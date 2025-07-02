package org.example.liba.repository;

import org.example.liba.entity.Category;

import java.util.Optional;

//mongodb + sql extends this interface，之后修改代码无需更改命名
public interface CategoryRepository {
    Optional<Category> findByName(String name);

    Category save(Category category);
}
