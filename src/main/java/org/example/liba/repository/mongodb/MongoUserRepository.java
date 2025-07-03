package org.example.liba.repository.mongodb;

import org.example.liba.entity.Category;
import org.example.liba.entity.User;

import org.example.liba.repository.UserRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoUserRepository extends MongoRepository<User, String>, UserRepository {
    User findByUserId(String userId);
    boolean existsByUserId(String userId);
}
