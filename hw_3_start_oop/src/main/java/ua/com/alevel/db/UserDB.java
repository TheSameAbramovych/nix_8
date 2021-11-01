package ua.com.alevel.db;

import ua.com.alevel.entity.User;

import java.util.Objects;
import java.util.UUID;

public class UserDB {

    private static UserDB instance;

    private int pointer;
    private User[] users;

    private UserDB() {
        users = new User[10];
        pointer = 0;
    }

    public static UserDB getInstance() {
        if (instance == null) {
            instance = new UserDB();
        }
        return instance;
    }

    public void create(User user) {
        if (pointer == users.length) {
            copyToNewArray();
        }
        user.setId(generateId());
        users[pointer] = user;
        pointer++;
    }

    public void update(User user) {
        User current = findById(user.getId());
        if (current != null) {
            current.setAge(user.getAge());
            current.setName(user.getName());
        }
    }

    public void delete(String id) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) {
                break;
            }
            if (Objects.equals(users[i].getId(), id)) {
                System.arraycopy(users, i + 1, users, i, pointer - i - 1);
                pointer--;
            }
        }
    }

    public User findById(String id) {
        int i;
        for (i = 0; i < users.length; i++) {
            if (users[i] == null) {
                break;
            }
            if (Objects.equals(users[i].getId(), id)) {
                return users[i];
            }
        }
        return null;
    }

    public User[] findAll() {
        User[] usersTmp = new User[pointer];
        System.arraycopy(users, 0, usersTmp, 0, pointer);
        return usersTmp;
    }

    private String generateId() {
        String id = UUID.randomUUID().toString();
        if (findById(id) != null) {
            return generateId();
        }
        return id;
    }

    private void copyToNewArray() {
        User[] usersTmp = users;
        users = new User[(users.length * 2 / 3) + 1 + users.length];
        System.arraycopy(usersTmp, 0, users, 0, pointer);
    }

}
