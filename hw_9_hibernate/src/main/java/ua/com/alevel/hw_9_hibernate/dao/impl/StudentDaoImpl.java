package ua.com.alevel.hw_9_hibernate.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ua.com.alevel.hw_9_hibernate.dao.StudentDao;
import ua.com.alevel.hw_9_hibernate.entity.Student;
import ua.com.alevel.hw_9_hibernate.persistence.DataTableRequest;
import ua.com.alevel.hw_9_hibernate.persistence.DataTableResponse;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class StudentDaoImpl implements StudentDao {
    private final SessionFactory sessionFactory;

    public StudentDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public long count() {
        Session session = getSession();
        Query query = session
                .createQuery("select count(student.id) from Student as student ");
        long result = (Long) query.getSingleResult();
        closeSession(session);
        return result;
    }

    @Override
    public void update(Student student) {
        Session session = getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.update(student);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            closeSession(session);
        }
    }

    @Override
    public void delete(Long id) {
        Session session = getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.createQuery("delete from Student as student " +
                            "where student.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            closeSession(session);
        }
    }

    @Override
    public boolean existById(Long id) {
        Session session = getSession();
        Query query = session
                .createQuery("select count(student.id) from Student student " +
                        "where student.id = :id")
                .setParameter("id", id);
        closeSession(session);
        boolean exist = (Long) query.getSingleResult() == 1;
        return exist;
    }

    @Override
    public Student findById(Long id) {
        Session session = getSession();
        Student searchStudent = session.find(Student.class, id);
        closeSession(session);
        return searchStudent;
    }

    @Override
    public DataTableResponse<Student> findAll(DataTableRequest request) {
        Session session = getSession();
        int page = (request.getCurrentPage() - 1) * request.getPageSize();
        int size = request.getPageSize();
        String sort = request.getSort();
        String order = request.getOrder();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Student> cq = cb.createQuery(Student.class);
        Root<Student> root = cq.from(Student.class);
        if (order.equalsIgnoreCase("desc")) {
            cq.orderBy(cb.desc(root.get(sort)));
        } else {
            cq.orderBy(cb.asc(root.get(sort)));
        }
        List<Student> result = session.createQuery(cq)
                .setFirstResult(page)
                .setMaxResults(size)
                .getResultList();

        DataTableResponse<Student> tableResponse = new DataTableResponse<>();
        tableResponse.setItems(result);
        closeSession(session);
        return tableResponse;
    }

    @Override
    public void create(Student student) {
        Session session = getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.save(student);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            closeSession(session);
        }
    }

    @Override
    public DataTableResponse<Student> findByGroup(Long groupId) {
        Session session = getSession();
        List<Student> result = session
                .createQuery("""
                        SELECT s FROM Student as s 
                        JOIN s.groups as g WHERE g.id = :id
                        """)
                .setParameter("id", groupId).getResultList();

        DataTableResponse<Student> tableResponse = new DataTableResponse<>();
        tableResponse.setItems(result);
        closeSession(session);
        return tableResponse;
    }

    private Session getSession() {
        try {
            return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            return sessionFactory.openSession();
        }
    }

    private void closeSession(Session session) {
        try {
            session.close();
        } catch (Exception e) {
            System.out.println("e = " + e.getMessage());
        }
    }
}
