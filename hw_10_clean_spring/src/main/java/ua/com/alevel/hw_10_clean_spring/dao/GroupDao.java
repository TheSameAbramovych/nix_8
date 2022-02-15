package ua.com.alevel.hw_10_clean_spring.dao;

import ua.com.alevel.hw_10_clean_spring.entity.Group;
import ua.com.alevel.hw_10_clean_spring.persistence.DataTableResponse;

public interface GroupDao extends BaseDao<Group> {

    DataTableResponse<Group> findByStudent(Long studentId);

    DataTableResponse<Group> findByHeadman(Long headmanId);
}