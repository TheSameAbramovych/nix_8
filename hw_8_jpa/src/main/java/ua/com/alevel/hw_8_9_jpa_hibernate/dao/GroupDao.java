package ua.com.alevel.hw_8_9_jpa_hibernate.dao;

import ua.com.alevel.hw_8_9_jpa_hibernate.entity.Group;
import ua.com.alevel.hw_8_9_jpa_hibernate.persistence.DataTableRequest;
import ua.com.alevel.hw_8_9_jpa_hibernate.persistence.DataTableResponse;

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

    void addHeadman(Long headmanId);
}
