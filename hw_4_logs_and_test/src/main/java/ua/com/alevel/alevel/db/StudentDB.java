package ua.com.alevel.alevel.db;

import ua.com.alevel.alevel.entity.Student;

public class StudentDB extends DB<Student> {

    private static StudentDB instance;

    private StudentDB() {
    }

    public static StudentDB getInstance() {
        if (instance == null) {
            instance = new StudentDB();
        }
        return instance;
    }

    @Override
    protected Student[] createEntitiesArray(int size) {
        return new Student[size];
    }

}
