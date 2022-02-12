package ua.com.alevel.module_3.dao;

import java.util.List;

public interface BaseDao<E, I> {

    void create(E entity);

    void update(E entity);

    void delete(Long id);

    E findById(I id);

    List<E> findAll(LimitedRequest request);

    long count();
}
