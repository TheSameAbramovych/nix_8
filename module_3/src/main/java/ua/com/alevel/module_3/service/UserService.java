package ua.com.alevel.module_3.service;

import ua.com.alevel.module_3.controller.dto.PageAndSizeData;
import ua.com.alevel.module_3.controller.dto.SortData;
import ua.com.alevel.module_3.entity.User;

import java.util.List;

public interface UserService {
    void create(User entity);

    void update(User entity);

    void changeStatus(Long id);

    User findById(Long id);

    List<User> findAll(PageAndSizeData pageAndSizeData, SortData sortData);

    long count();
}
