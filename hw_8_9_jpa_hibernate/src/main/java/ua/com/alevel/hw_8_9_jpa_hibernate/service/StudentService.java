package ua.com.alevel.hw_8_9_jpa_hibernate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.alevel.hw_8_9_jpa_hibernate.controller.dto.PageAndSizeData;
import ua.com.alevel.hw_8_9_jpa_hibernate.controller.dto.SortData;
import ua.com.alevel.hw_8_9_jpa_hibernate.dao.StudentDao;
import ua.com.alevel.hw_8_9_jpa_hibernate.entity.Student;
import ua.com.alevel.hw_8_9_jpa_hibernate.persistence.DataTableRequest;

import java.util.List;

@Service
public class StudentService implements CrudService<Student, Long> {
    private final StudentDao studentDao;

    @Autowired
    public StudentService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public void save(Student student) {
        studentDao.create(student);
    }

    @Override
    public void update(Student student) {
        studentDao.update(student);
    }

    @Override
    public void delete(Long id) {
        studentDao.delete(id);
    }

    @Override
    public Student findById(Long id) {
        return studentDao.findById(id);
    }

    public List<Student> findByGroup(Long id) {
        return studentDao.findByGroup(id).getItems();
    }

    @Override
    public List<Student> findAll(PageAndSizeData pageAndSizeData, SortData sortData) {
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder().toUpperCase());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());
        dataTableRequest.setPageSize(pageAndSizeData.getSize());

        return studentDao.findAll(dataTableRequest).getItems();
    }

    @Override
    public long count() {
        return studentDao.count();
    }

}
