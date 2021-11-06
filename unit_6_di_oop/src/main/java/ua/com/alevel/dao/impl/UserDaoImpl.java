package ua.com.alevel.dao.impl;

import com.nixsolutions.annotations.Service;
import ua.com.alevel.dao.UserDao;
import ua.com.alevel.db.UserDB;
import ua.com.alevel.db.impl.UserInMemoryDB;
import ua.com.alevel.entity.User;

import java.util.Collection;

@Service
public class UserDaoImpl implements UserDao {

    //    private final UserDB db = ObjectFactory.getInstance().getCurrentObject(UserDB.class);
    private final UserDB db = new UserInMemoryDB();

    @Override
    public void create(User entity) {
        db.create(entity);
    }

    @Override
    public void update(User entity) {
        db.update(entity);
    }

    @Override
    public void delete(String id) {
        db.delete(id);
    }

    @Override
    public User findById(String id) {
        return db.findById(id);
    }

    @Override
    public Collection<User> findAll() {
        return db.findAll();
    }

    @Override
    public boolean existByEmail(String email) {
        return db.existByEmail(email);
    }
}
