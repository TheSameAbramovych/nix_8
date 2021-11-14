package ua.com.hw4.dao;


import ua.com.hw4.db.StudentDB;
import ua.com.hw4.entity.Student;

public class StudentDao {

    public void create(Student student) {
        StudentDB.getInstance().create(student);
    }

    public void update(Student student) {
        StudentDB.getInstance().update(student);
    }

    public void delete(String id) {
        StudentDB.getInstance().delete(id);
    }

    public Student findById(String id) {
        return StudentDB.getInstance().findById(id);
    }

    public Student[] findAll() {
        return StudentDB.getInstance().findAll();
    }
}


