package ua.com.alevel.module_3.dao;

import org.springframework.stereotype.Repository;
import ua.com.alevel.module_3.entity.Wallet;

@Repository
public class WalletDao implements BaseDao<Wallet, String> {
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
    public boolean existById(String id) {
        return false;
    }

    @Override
    public Wallet findById(String id) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }
}
