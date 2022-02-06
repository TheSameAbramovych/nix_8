package ua.com.alevel.hw_9_hibernate.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ua.com.alevel.hw_9_hibernate.dao.GroupDao;
import ua.com.alevel.hw_9_hibernate.entity.Group;
import ua.com.alevel.hw_9_hibernate.persistence.DataTableRequest;
import ua.com.alevel.hw_9_hibernate.persistence.DataTableResponse;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class GroupDaoImpl implements GroupDao {
    private final SessionFactory sessionFactory;

    public GroupDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public long count() {
        Session session = getSession();
        Query query = getSession()
                .createQuery("select count(g.id) from Group as g");
        closeSession(session);
        long result = (Long) query.getSingleResult();
        return result;
    }

    @Override
    public void create(Group group) {
        Session session = getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.save(group);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            closeSession(session);
        }
    }

    @Override
    public void update(Group group) {
        Session session = getSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.update(group);
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
            session.createQuery("delete from Group as group " +
                            "where group.id = :id")
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
                .createQuery("select count(group.id) from Group group " +
                        "where group.id = :id")
                .setParameter("id", id);
        boolean exist = (Long) query.getSingleResult() == 1;
        closeSession(session);
        return exist;
    }

    @Override
    public Group findById(Long id) {
        Session session = getSession();
        Group searchGroup = session.find(Group.class, id);
        closeSession(session);
        return searchGroup;
    }

    @Override
    public DataTableResponse<Group> findAll(DataTableRequest request) {
        Session session = getSession();
        int page = (request.getCurrentPage() - 1) * request.getPageSize();
        int size = request.getPageSize();
        String sort = request.getSort();
        String order = request.getOrder();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Group> cq = cb.createQuery(Group.class);
        Root<Group> root = cq.from(Group.class);
        if (order.equalsIgnoreCase("desc")) {
            cq.orderBy(cb.desc(root.get(sort)));
        } else {
            cq.orderBy(cb.asc(root.get(sort)));
        }
        List<Group> result = session.createQuery(cq)
                .setFirstResult(page)
                .setMaxResults(size)
                .getResultList();

        DataTableResponse<Group> tableResponse = new DataTableResponse<>();
        tableResponse.setItems(result);
        closeSession(session);
        return tableResponse;
    }

    @Override
    public DataTableResponse<Group> findByStudent(Long studentId) {
        Session session = getSession();
        List<Group> result = session
                .createQuery("""
                        SELECT g FROM Group as g 
                        JOIN g.students as s WHERE s.id = :id
                        """)
                .setParameter("id", studentId).getResultList();

        DataTableResponse<Group> tableResponse = new DataTableResponse<>();
        tableResponse.setItems(result);
        closeSession(session);
        return tableResponse;
    }

    @Override
    public DataTableResponse<Group> findByHeadman(Long headmanId) {
        Session session = getSession();
        List<Group> result = session
                .createQuery("""
                        SELECT g FROM Group as g 
                        JOIN g.headman as h WHERE h.id = :id
                        """)
                .setParameter("id", headmanId).getResultList();

        DataTableResponse<Group> tableResponse = new DataTableResponse<>();
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
