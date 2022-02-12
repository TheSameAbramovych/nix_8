package ua.com.alevel.module_3.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ua.com.alevel.module_3.entity.User;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDao implements BaseDao<User, Long> {
    private final SessionFactory sessionFactory;

    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void create(User entity) {
        sessionFactory.getCurrentSession().save(entity);
    }

    @Override
    @Transactional
    public void update(User entity) {
        sessionFactory.getCurrentSession().update(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from User as entity " +
                        "where entity.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    @Transactional
    public User findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(User.class, id);
    }

    @Override
    @Transactional
    public long count() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session
                .createQuery("select count(entity.id) from User as entity ");
        return (Long) query.getSingleResult();
    }

    @Override
    @Transactional
    public List<User> findAll(LimitedRequest request) {
        Session session = sessionFactory.getCurrentSession();
        int page = (request.getCurrentPage() - 1) * request.getPageSize();
        int size = request.getPageSize();
        String sort = request.getSort();
        String order = request.getOrder();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        if (order.equalsIgnoreCase("desc")) {
            cq.orderBy(cb.desc(root.get(sort)));
        } else {
            cq.orderBy(cb.asc(root.get(sort)));
        }

        return session.createQuery(cq)
                .setFirstResult(page)
                .setMaxResults(size)
                .getResultList();
    }
}
