package ua.com.alevel.dao;

import ua.com.alevel.persistence.DataTableRequest;
import ua.com.alevel.persistence.DataTableResponse;
import ua.com.alevel.persistence.entity.Group;

public interface GroupDao extends BaseDao<Group> {
    @Override
    void create(Group group);

    @Override
    void update(Group group);

    @Override
    void delete(Long id);

    @Override
    boolean existById(Long id);

    @Override
    Group findById(Long id);

    @Override
    DataTableResponse<Group> findAll(DataTableRequest request);

    DataTableResponse<Group> findByStudent(Long studentId);

    void deleteStudent(Long id, Long studentId);

    void addStudent(Long id, Long studentId);

    void addHeadman(Long headmanId);
}
