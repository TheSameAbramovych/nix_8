package ua.com.alevel.module_3.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ua.com.alevel.module_3.entity.Wallet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class WalletDao implements BaseDao<Wallet, String> {
    private final SessionFactory sessionFactory;

    public WalletDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void create(Wallet entity) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.persist(entity);
    }

    @Override
    @Transactional
    public void update(Wallet entity) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.update(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
    }

    @Override
    @Transactional
    public Wallet findById(String id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.get(Wallet.class, id);
    }

    @Override
    public List<Wallet> findAll(LimitedRequest request) {
        return null;
    }

    @Override
    @Transactional
    public long count() {
        return 0;
    }

    @Transactional
    public Wallet findByNumber(String number) {
        Session currentSession = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = currentSession.getCriteriaBuilder();
        CriteriaQuery<Wallet> criteriaQuery = criteriaBuilder.createQuery(Wallet.class);
        Root<Wallet> root = criteriaQuery.from(Wallet.class);

        criteriaQuery.where(criteriaBuilder.equal(root.get("number"), number));

        return currentSession.createQuery(criteriaQuery).uniqueResult();
    }
}
