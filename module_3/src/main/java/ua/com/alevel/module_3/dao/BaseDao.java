package ua.com.alevel.module_3.dao;

public interface BaseDao<E, I> {

    void create(E entity);

    void update(E entity);

    void delete(Long id);

    boolean existById(I id);

    E findById(I id);

//    List<E> findAll(DataTableRequest request);

    long count();
}
