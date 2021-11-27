package ua.com.alevel.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.com.alevel.dao.StudentDao;
import ua.com.alevel.entity.Student;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class StudentServiceTest {

    public static final Random RANDOM = new Random();

    @Mock
    private StudentDao studentDao;

    @InjectMocks
    private StudentService studentService;

    @Test
    public void create_ok() {
        Student student = createStudent();

        studentService.create(student);

        verify(studentDao).create(student);
    }

    @Test
    public void update_ok() {
        Student studentOld = createStudent();
        Student studentNew = createStudent();

        when(studentDao.findById(studentNew.getId())).thenReturn(studentOld);

        studentService.update(studentNew);

        verify(studentDao).update(studentNew);
    }

    @Test
    public void findAll_ok() {
        Student[] students = {createStudent(), createStudent()};

        when(studentDao.findAll()).thenReturn(students);
        Student[] studentAll = studentService.findAll();

        assertArrayEquals(students, studentAll);
        verify(studentDao).findAll();
    }

    @Test
    public void findById_ok() {
        Student student = createStudent();

        studentService.findById(student.getId());

        verify(studentDao).findById(student.getId());
    }

    @Test
    public void delete_ok() {
        Student student = createStudent();

        studentService.delete(student.getId());

        verify(studentDao).delete(student.getId());
    }

    private Student createStudent() {
        Student student = new Student();
        student.setId(UUID.randomUUID().toString());
        student.setName(UUID.randomUUID().toString());
        student.setAge(RANDOM.nextInt());
        student.setEmail(UUID.randomUUID().toString());
        return student;
    }
}
