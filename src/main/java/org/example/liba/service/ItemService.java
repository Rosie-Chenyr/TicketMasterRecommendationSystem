package org.example.liba.service;

import org.example.liba.entity.Item;
import org.example.liba.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void saveItem(Item item){
        List<Item> res = itemRepository.findByItemId(item.getItemId());
        if(res.isEmpty()){
            itemRepository.save(item);
        }
    }
}
