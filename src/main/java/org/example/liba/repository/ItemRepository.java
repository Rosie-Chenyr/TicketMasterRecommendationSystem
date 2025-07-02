package org.example.liba.repository;

import org.example.liba.entity.Item;

import java.util.List;

public interface ItemRepository {
    List<Item> findByItemId(String itemId);

    Item save(Item item);
}
