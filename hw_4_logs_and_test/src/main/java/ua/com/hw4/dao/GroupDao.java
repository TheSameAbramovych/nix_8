package ua.com.hw4.dao;


import ua.com.hw4.db.GroupDB;
import ua.com.hw4.entity.Group;


public class GroupDao {

    public void create(Group group) {
        GroupDB.getInstance().create(group);
    }

    public void update(Group group) {
        GroupDB.getInstance().update(group);
    }

    public void delete(String id) {
        GroupDB.getInstance().delete(id);
    }

    public Group findById(String id) {
        return GroupDB.getInstance().findById(id);
    }

    public Group[] findAll() {
        return GroupDB.getInstance().findAll();
    }
}


