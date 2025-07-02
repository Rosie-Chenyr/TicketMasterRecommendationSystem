package org.example.liba.repository.mongodb;

import org.example.liba.entity.Category;
import org.example.liba.repository.CategoryRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("mongoCategoryRepository")
public interface MongoCategoryRepository extends MongoRepository<Category, String>, CategoryRepository {
    Optional<Category> findByName(String name);
}
