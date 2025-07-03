package org.example.liba.repository.mysql;

import org.example.liba.entity.Item;
import org.example.liba.repository.ItemRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("mySQLItemRepository")
public interface MySQLItemRepository extends JpaRepository<Item, String>, ItemRepository {
    //Optional<Item> findById(String itemId);
}