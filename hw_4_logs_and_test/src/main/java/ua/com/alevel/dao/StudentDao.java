package ua.com.alevel.dao;


import ua.com.alevel.db.StudentDB;
import ua.com.alevel.entity.Student;

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


