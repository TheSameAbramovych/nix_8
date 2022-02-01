package ua.com.alevel.hw_8_9_jpa_hibernate.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.hw_8_9_jpa_hibernate.dao.GroupDao;
import ua.com.alevel.hw_8_9_jpa_hibernate.entity.Group;
import ua.com.alevel.hw_8_9_jpa_hibernate.persistence.DataTableRequest;
import ua.com.alevel.hw_8_9_jpa_hibernate.persistence.DataTableResponse;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class GroupDaoImpl implements GroupDao {
    private final EntityManager entityManager;

    public GroupDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public long count() {
        Query query = entityManager
                .createQuery("select count(g.id) from Group as g");
        return (Long) query.getSingleResult();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void create(Group group) {
        entityManager.persist(group);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void update(Group group) {
        entityManager.merge(group);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void delete(Long id) {
        entityManager.createQuery("delete from Group as group " +
                        "where group.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existById(Long id) {
        Query query = entityManager
                .createQuery("select count(group.id) from Group group " +
                        "where group.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    @Transactional(readOnly = true)
    public Group findById(Long id) {
        return entityManager.find(Group.class, id);
    }

    @Override
    public DataTableResponse<Group> findAll(DataTableRequest request) {
        int page = (request.getCurrentPage() - 1) * request.getPageSize();
        int size = request.getPageSize();
        String sort = request.getSort();
        String order = request.getOrder();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Group> cq = cb.createQuery(Group.class);
        Root<Group> root = cq.from(Group.class);
        if (order.equalsIgnoreCase("desc")) {
            cq.orderBy(cb.desc(root.get(sort)));
        } else {
            cq.orderBy(cb.asc(root.get(sort)));
        }
        List<Group> result = entityManager.createQuery(cq)
                .setFirstResult(page)
                .setMaxResults(size)
                .getResultList();

        DataTableResponse<Group> tableResponse = new DataTableResponse<>();
        tableResponse.setItems(result);
        return tableResponse;
    }

    @Override
    public DataTableResponse<Group> findByStudent(Long studentId) {
        List<Group> result = entityManager
                .createQuery("""
                        SELECT g FROM Group as g 
                        JOIN g.students as s WHERE s.id = :id
                        """)
                .setParameter("id", studentId).getResultList();

        DataTableResponse<Group> tableResponse = new DataTableResponse<>();
        tableResponse.setItems(result);
        return tableResponse;
    }

    @Override
    public void addHeadman(Long headmanId) {

    }

//    private Group convertResultSetToGroup(ResultSet resultSet) throws SQLException {
//        long id = resultSet.getLong("id");
//        String name = resultSet.getString("name");
//        Student headmanId =
//                resultSet.getLong("headman");
//        Group group = new Group();
//        group.setId(id);
//        group.setName(name);
//        group.setHeadman(headmanId);
//        return group;
//    }
}
