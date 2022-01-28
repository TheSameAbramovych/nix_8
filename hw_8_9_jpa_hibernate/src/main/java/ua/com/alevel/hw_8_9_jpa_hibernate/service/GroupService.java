package ua.com.alevel.hw_8_9_jpa_hibernate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.alevel.hw_8_9_jpa_hibernate.controller.dto.PageAndSizeData;
import ua.com.alevel.hw_8_9_jpa_hibernate.controller.dto.SortData;
import ua.com.alevel.hw_8_9_jpa_hibernate.dao.GroupDao;
import ua.com.alevel.hw_8_9_jpa_hibernate.entity.Group;
import ua.com.alevel.hw_8_9_jpa_hibernate.persistence.DataTableRequest;

import java.util.List;

@Service
public class GroupService implements CrudService<Group, Long> {

    private final GroupDao groupDao;

    @Autowired
    public GroupService(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public void save(Group group) {
        groupDao.create(group);
    }

    @Override
    public void update(Group group) {
        groupDao.update(group);
    }

    @Override
    public void delete(Long id) {
        groupDao.delete(id);
    }

    public void delete(Group group) {
        groupDao.delete(group.getId());
    }

    public Group findById(Long id) {
        return groupDao.findById(id);
    }

    public List<Group> findByStudentId(Long id) {
        return groupDao.findByStudent(id).getItems();
    }

    public void deleteStudent(Long id, Long studentId) {
        groupDao.deleteStudent(id, studentId);
    }

    public void addStudent(Long id, Long studentId) {
        groupDao.addStudent(id, studentId);
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
