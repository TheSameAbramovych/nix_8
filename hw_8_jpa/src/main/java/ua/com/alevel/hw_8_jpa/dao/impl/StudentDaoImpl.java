package ua.com.alevel.hw_8_jpa.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.hw_8_jpa.dao.StudentDao;
import ua.com.alevel.hw_8_jpa.entity.Student;
import ua.com.alevel.hw_8_jpa.persistence.DataTableRequest;
import ua.com.alevel.hw_8_jpa.persistence.DataTableResponse;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StudentDaoImpl implements StudentDao {
    private final EntityManager entityManager;

    public StudentDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public long count() {
        Query query = entityManager
                .createQuery("select count(student.id) from Student as student ");
        return (Long) query.getSingleResult();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void update(Student student) {
        entityManager.merge(student);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void delete(Long id) {
        entityManager.createQuery("delete from Student as student " +
                        "where student.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existById(Long id) {
        Query query = entityManager
                .createQuery("select count(student.id) from Student student " +
                        "where student.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    @Transactional(readOnly = true)
    public Student findById(Long id) {
        return entityManager.find(Student.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public DataTableResponse<Student> findAll(DataTableRequest request) {
        int page = (request.getCurrentPage() - 1) * request.getPageSize();
        int size = request.getPageSize();
        String sort = request.getSort();
        String order = request.getOrder();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> cq = cb.createQuery(Student.class);
        Root<Student> root = cq.from(Student.class);
        if (order.equalsIgnoreCase("desc")) {
            cq.orderBy(cb.desc(root.get(sort)));
        } else {
            cq.orderBy(cb.asc(root.get(sort)));
        }
        List<Student> result = entityManager.createQuery(cq)
                .setFirstResult(page)
                .setMaxResults(size)
                .getResultList();

        DataTableResponse<Student> tableResponse = new DataTableResponse<>();
        tableResponse.setItems(result);
        return tableResponse;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void create(Student student) {
        entityManager.persist(student);
    }

    @Override
    public DataTableResponse<Student> findByGroup(Long groupId) {
        List<Student> result = entityManager
                .createQuery("""
                        SELECT s FROM Student as s 
                        JOIN s.groups as g WHERE g.id = :id
                        """)
                .setParameter("id", groupId).getResultList();

        DataTableResponse<Student> tableResponse = new DataTableResponse<>();
        tableResponse.setItems(result);
        return tableResponse;
    }


    private Student convertResultSetToStudent(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        int age = resultSet.getInt("age");
        String email = resultSet.getString("email");

        Student student = new Student();
        student.setId(id);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setAge(age);
        return student;
    }

    private static class StudentResultSet {

        private final Student student;

        private StudentResultSet(Student student) {
            this.student = student;
        }

        public Student getStudent() {
            return student;
        }

    }
}
