package org.example.liba.repository;

import org.example.liba.entity.User;

public interface UserRepository {
    User findByUserId(String userId);
    User save(User user);
    boolean existsByUserId(String userId);
}
