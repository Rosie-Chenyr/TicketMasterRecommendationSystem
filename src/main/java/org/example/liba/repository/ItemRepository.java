package org.example.liba.repository;

import org.example.liba.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, String>{
    List<Item> findByItemId(String itemId);
}