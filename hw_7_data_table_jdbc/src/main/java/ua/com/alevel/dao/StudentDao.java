package ua.com.alevel.dao;

import ua.com.alevel.persistence.DataTableRequest;
import ua.com.alevel.persistence.DataTableResponse;
import ua.com.alevel.persistence.entity.Student;

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
