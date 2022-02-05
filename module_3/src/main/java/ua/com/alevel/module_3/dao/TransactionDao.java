package ua.com.alevel.module_3.dao;

import org.springframework.stereotype.Repository;
import ua.com.alevel.module_3.entity.Transaction;

@Repository
public class TransactionDao implements BaseDao<Transaction, Long> {

    @Override
    public void create(Transaction entity) {

    }

    @Override
    public void update(Transaction entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public boolean existById(Long id) {
        return false;
    }

    @Override
    public Transaction findById(Long id) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }
}
