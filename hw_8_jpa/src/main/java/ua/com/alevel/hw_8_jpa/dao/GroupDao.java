package ua.com.alevel.hw_8_jpa.dao;

import ua.com.alevel.hw_8_jpa.entity.Group;
import ua.com.alevel.hw_8_jpa.persistence.DataTableResponse;

public interface GroupDao extends BaseDao<Group> {
    DataTableResponse<Group> findByStudent(Long studentId);

    DataTableResponse<Group> findByHeadman(Long headmanId);
}