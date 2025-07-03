package org.example.liba.repository;

import org.example.liba.entity.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    //List<Item> findById(String id);

    Item save(Item item);

    Optional<Item> findById(String id);
}
