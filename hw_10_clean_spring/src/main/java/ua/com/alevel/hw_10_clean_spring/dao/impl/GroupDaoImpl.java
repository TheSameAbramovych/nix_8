package ua.com.alevel.hw_10_clean_spring.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.hw_10_clean_spring.dao.GroupDao;
import ua.com.alevel.hw_10_clean_spring.entity.Group;
import ua.com.alevel.hw_10_clean_spring.persistence.DataTableRequest;
import ua.com.alevel.hw_10_clean_spring.persistence.DataTableResponse;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class GroupDaoImpl implements GroupDao {
    private final EntityManagerFactory entityManagerFactory;

    public GroupDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public long count() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager
                .createQuery("select count(g.id) from Group as g");
        return (Long) query.getSingleResult();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void create(Group group) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(group);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void update(Group group) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(group);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void delete(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.createQuery("delete from Group as group " +
                            "where group.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager
                .createQuery("select count(group.id) from Group group " +
                        "where group.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    @Transactional(readOnly = true)
    public Group findById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return entityManager.find(Group.class, id);
    }

    @Override
    public DataTableResponse<Group> findAll(DataTableRequest request) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
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
        EntityManager entityManager = entityManagerFactory.createEntityManager();
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
    public DataTableResponse<Group> findByHeadman(Long headmanId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Group> result = entityManager
                .createQuery("""
                        SELECT g FROM Group as g 
                        JOIN g.headman as h WHERE h.id = :id
                        """)
                .setParameter("id", headmanId).getResultList();

        DataTableResponse<Group> tableResponse = new DataTableResponse<>();
        tableResponse.setItems(result);
        return tableResponse;
    }

}
