package ua.com.alevel.hw_8_jpa.dao;

import ua.com.alevel.hw_8_jpa.entity.Student;
import ua.com.alevel.hw_8_jpa.persistence.DataTableResponse;

public interface StudentDao extends BaseDao<Student> {
    DataTableResponse<Student> findByGroup(Long groupId);
}
