package ua.com.alevel.db;

import ua.com.alevel.entity.Group;

public class GroupDB extends DB<Group> {

    private static GroupDB instance;

    private GroupDB() {
    }

    public static GroupDB getInstance() {
        if (instance == null) {
            instance = new GroupDB();
        }
        return instance;
    }

    @Override
    protected Group[] createEntitiesArray(int size) {
        return new Group[size];
    }

}


