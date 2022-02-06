package ua.com.alevel.hw_9_hibernate.dao;

import ua.com.alevel.hw_9_hibernate.entity.Group;
import ua.com.alevel.hw_9_hibernate.persistence.DataTableResponse;

public interface GroupDao extends BaseDao<Group> {
    DataTableResponse<Group> findByStudent(Long studentId);

    DataTableResponse<Group> findByHeadman(Long headmanId);
}