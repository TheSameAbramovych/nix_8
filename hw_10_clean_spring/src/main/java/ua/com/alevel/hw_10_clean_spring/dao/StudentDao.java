package ua.com.alevel.hw_10_clean_spring.dao;

import ua.com.alevel.hw_10_clean_spring.entity.Student;
import ua.com.alevel.hw_10_clean_spring.persistence.DataTableResponse;

public interface StudentDao extends BaseDao<Student> {
    DataTableResponse<Student> findByGroup(Long groupId);
}
