package org.example.liba.repository.mysql;

import org.example.liba.entity.User;
import org.example.liba.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MySQLUserRepository extends JpaRepository<User, String>, UserRepository {
    User findByUserId(String userId);
    boolean existsByUserId(String userId);
}
