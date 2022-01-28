package ua.com.alevel.hw_8_9_jpa_hibernate.dao;

import ua.com.alevel.hw_8_9_jpa_hibernate.entity.Student;
import ua.com.alevel.hw_8_9_jpa_hibernate.persistence.DataTableRequest;
import ua.com.alevel.hw_8_9_jpa_hibernate.persistence.DataTableResponse;

public interface StudentDao extends BaseDao<Student> {

    @Override
    void update(Student student);

    @Override
    void delete(Long id);

    @Override
    boolean existById(Long id);

    @Override
    Student findById(Long id);

    @Override
    DataTableResponse<Student> findAll(DataTableRequest request);

    @Override
    void create(Student student);

    DataTableResponse<Student> findByGroup(Long groupId);
}
