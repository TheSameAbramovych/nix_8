package ua.com.alevel.alevel.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.com.alevel.alevel.dao.StudentDao;
import ua.com.alevel.alevel.entity.Student;

public class StudentService {
    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");
    private final StudentDao studentDao;

    public StudentService() {
        studentDao = new StudentDao();
    }

    public StudentService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public void create(Student student) {
        studentDao.create(student);
        LOGGER_INFO.info("Create student: " + student);

    }

    public void update(Student student) {
        studentDao.update(student);
        LOGGER_INFO.info("Update student: " + student.getId());
    }

    public void delete(String id) {
        studentDao.delete(id);
        LOGGER_INFO.info("Delete student: " + id);
    }

    public Student findById(String id) {
        return studentDao.findById(id);
    }

    public Student[] findAll() {
        return studentDao.findAll();
    }
}
