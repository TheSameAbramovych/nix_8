package ua.com.alevel.module_3.dao;

import org.springframework.stereotype.Repository;
import ua.com.alevel.module_3.entity.Wallet;

@Repository
public class UserDao implements BaseDao<Wallet, Long> {
    @Override
    public void create(Wallet entity) {

    }

    @Override
    public void update(Wallet entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public boolean existById(Long id) {
        return false;
    }

    @Override
    public Wallet findById(Long id) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }
}
