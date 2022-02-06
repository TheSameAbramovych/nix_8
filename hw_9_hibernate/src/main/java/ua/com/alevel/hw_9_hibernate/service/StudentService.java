package ua.com.alevel.hw_9_hibernate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.alevel.hw_9_hibernate.controller.dto.PageAndSizeData;
import ua.com.alevel.hw_9_hibernate.controller.dto.SortData;
import ua.com.alevel.hw_9_hibernate.dao.GroupDao;
import ua.com.alevel.hw_9_hibernate.dao.StudentDao;
import ua.com.alevel.hw_9_hibernate.entity.Group;
import ua.com.alevel.hw_9_hibernate.entity.Student;
import ua.com.alevel.hw_9_hibernate.persistence.DataTableRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService implements CrudService<Student, Long> {
    private final StudentDao studentDao;
    private final GroupDao groupDao;

    @Autowired
    public StudentService(StudentDao studentDao, GroupDao groupDao) {
        this.studentDao = studentDao;
        this.groupDao = groupDao;
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
        List<Group> groups = groupDao.findByHeadman(id).getItems();
        if (!groups.isEmpty()) {
            throw new RuntimeException("Student is headman in group: " + groups.stream().map(Group::getName).collect(Collectors.joining()));
        }
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
