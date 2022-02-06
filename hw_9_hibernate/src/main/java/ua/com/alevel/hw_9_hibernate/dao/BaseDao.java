package ua.com.alevel.hw_9_hibernate.dao;

import ua.com.alevel.hw_9_hibernate.persistence.DataTableRequest;
import ua.com.alevel.hw_9_hibernate.persistence.DataTableResponse;

public interface BaseDao<ENTITY> {

    void create(ENTITY entity);

    void update(ENTITY entity);

    void delete(Long id);

    boolean existById(Long id);

    ENTITY findById(Long id);

    DataTableResponse<ENTITY> findAll(DataTableRequest request);

    long count();
}
