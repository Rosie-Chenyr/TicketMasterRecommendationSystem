package org.example.liba.repository.mongodb;

import org.example.liba.entity.Item;
import org.example.liba.repository.ItemRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("mongoItemRepository")
public interface MongoItemRepository extends MongoRepository<Item, String>, ItemRepository {
    List<Item> findByItemId(String itemId);
}
