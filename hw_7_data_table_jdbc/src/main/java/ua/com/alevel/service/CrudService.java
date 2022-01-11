package ua.com.alevel.service;

import ua.com.alevel.controller.dto.PageAndSizeData;
import ua.com.alevel.controller.dto.SortData;

import java.util.List;

public interface CrudService<E, I> {
    void save(E e);

    void update(E e);

    void delete(I id);

    E findById(I id);

    List<E> findAll(PageAndSizeData pageAndSizeData, SortData sortData);

    long count();
}
