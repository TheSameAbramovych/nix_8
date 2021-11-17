package ua.com.alevel.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.dao.StudentDao;
import ua.com.alevel.entity.Student;

public class StudentService {

    private final StudentDao studentDao = new StudentDao();
    private final Logger loggerInfo = LoggerFactory.getLogger("info");

    public void create(Student student) {
        studentDao.create(student);
        loggerInfo.info("Create student: " + student);
    }

    public void update(Student student) {
        studentDao.update(student);
        loggerInfo.info("Update student: " + student.getId());
    }

    public void delete(String id) {
        studentDao.delete(id);
        loggerInfo.info("Delete student: " + id);
    }

    public Student findById(String id) {
        return studentDao.findById(id);
    }

    public Student[] findAll() {
        return studentDao.findAll();
    }
}
