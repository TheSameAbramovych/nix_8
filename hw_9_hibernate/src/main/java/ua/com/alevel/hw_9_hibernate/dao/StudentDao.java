package ua.com.alevel.hw_9_hibernate.dao;

import ua.com.alevel.hw_9_hibernate.entity.Student;
import ua.com.alevel.hw_9_hibernate.persistence.DataTableResponse;

public interface StudentDao extends BaseDao<Student> {
    DataTableResponse<Student> findByGroup(Long groupId);
}
