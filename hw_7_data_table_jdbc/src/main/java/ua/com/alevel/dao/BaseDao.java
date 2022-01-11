package ua.com.alevel.dao;

import ua.com.alevel.persistence.DataTableRequest;
import ua.com.alevel.persistence.DataTableResponse;

public interface BaseDao<ENTITY> {

    void create(ENTITY entity);

    void update(ENTITY entity);

    void delete(Long id);

    boolean existById(Long id);

    ENTITY findById(Long id);

    DataTableResponse<ENTITY> findAll(DataTableRequest request);

    long count();
}
