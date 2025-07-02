package org.example.liba.repository.mysql;

import org.example.liba.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.liba.repository.CategoryRepository;

import java.util.Optional;


@Repository("mySQLCategoryRepository")
public interface MySQLCategoryRepository extends JpaRepository<Category, String>, CategoryRepository{
    Optional<Category> findByName(String name);
}
