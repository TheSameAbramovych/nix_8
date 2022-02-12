package ua.com.alevel.module_3.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ua.com.alevel.module_3.entity.Transaction;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

@Repository
public class TransactionDao implements BaseDao<Transaction, Long> {
    private final SessionFactory sessionFactory;

    public TransactionDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(Transaction entity) {
        sessionFactory.getCurrentSession().save(entity);
    }

    @Override
    public void update(Transaction entity) {
        sessionFactory.getCurrentSession().update(entity);
    }

    @Override
    public void delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from Transaction as entity " +
                        "where entity.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public Transaction findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Transaction.class, id);
    }

    @Override
    public List<Transaction> findAll(LimitedRequest request) {
        Session session = sessionFactory.getCurrentSession();
        int page = (request.getCurrentPage() - 1) * request.getPageSize();
        int size = request.getPageSize();
        String sort = request.getSort();
        String order = request.getOrder();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Transaction> cq = cb.createQuery(Transaction.class);
        Root<Transaction> root = cq.from(Transaction.class);
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

    public List<Transaction> findByPeriod(List<String> wallets, Date from, Date to) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Transaction> cq = cb.createQuery(Transaction.class);
        Root<Transaction> root = cq.from(Transaction.class);

        cq.where(cb.and(
                cb.in(root.get("wallet").get("number")).value(wallets),
                cb.between(root.get("processedDate"), from, to)
        ));

        return session.createQuery(cq).getResultList();
    }

    @Override
    public long count() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session
                .createQuery("select count(entity.id) from Transaction as entity ");
        return (Long) query.getSingleResult();
    }
}
