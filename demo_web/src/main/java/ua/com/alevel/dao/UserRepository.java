package ua.com.alevel.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.alevel.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);
}
