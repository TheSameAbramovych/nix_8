package ua.com.alevel.hw_8_jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.alevel.hw_8_jpa.controller.dto.PageAndSizeData;
import ua.com.alevel.hw_8_jpa.controller.dto.SortData;
import ua.com.alevel.hw_8_jpa.dao.GroupDao;
import ua.com.alevel.hw_8_jpa.entity.Group;
import ua.com.alevel.hw_8_jpa.entity.Student;
import ua.com.alevel.hw_8_jpa.persistence.DataTableRequest;

import java.util.List;
import java.util.Set;

@Service
public class GroupService implements CrudService<Group, Long> {

    private final GroupDao groupDao;
    private final StudentService studentService;

    @Autowired
    public GroupService(GroupDao groupDao, StudentService studentService) {
        this.groupDao = groupDao;
        this.studentService = studentService;
    }

    public void save(Group group) {
        if (group.getHeadman() == null) {
            throw new RuntimeException("Can't find student ");
        }
        groupDao.create(group);
        group.getStudents().add(group.getHeadman());
        groupDao.update(group);
    }

    @Override
    public void update(Group update) {
        Group group = groupDao.findById(update.getId());
        Student headman = studentService.findById(update.getHeadman().getId());

        Set<Student> students = group.getStudents();
        students.addAll(update.getStudents());
        group.setStudents(students);
        group.setName(update.getName());
        group.getStudents().add(headman);
        group.setHeadman(headman);

        groupDao.update(group);
    }

    @Override
    public void delete(Long id) {
        groupDao.delete(id);
    }

    public Group findById(Long id) {
        return groupDao.findById(id);
    }

    public List<Group> findByStudentId(Long id) {
        return groupDao.findByStudent(id).getItems();
    }

    public void deleteStudent(Long id, Student student) {
        Group group = groupDao.findById(id);
        group.getStudents().remove(student);
        groupDao.update(group);
    }

    public void addStudent(Long id, Student student) {
        Group group = groupDao.findById(id);
        group.getStudents().add(student);
        groupDao.update(group);
    }

    public List<Group> findAll(PageAndSizeData pageAndSizeData, SortData sortData) {
        DataTableRequest dataTableRequest = new DataTableRequest();
        dataTableRequest.setSort(sortData.getSort());
        dataTableRequest.setOrder(sortData.getOrder().toUpperCase());
        dataTableRequest.setCurrentPage(pageAndSizeData.getPage());
        dataTableRequest.setPageSize(pageAndSizeData.getSize());

        return groupDao.findAll(dataTableRequest).getItems();
    }

    @Override
    public long count() {
        return groupDao.count();
    }
}
