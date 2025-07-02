package org.example.liba.service;

import org.example.liba.entity.Category;
import org.example.liba.entity.Item;
import org.example.liba.repository.CategoryRepository;
import org.example.liba.repository.ItemRepository;
import org.example.liba.repository.mysql.MySQLCategoryRepository;
import org.example.liba.repository.mysql.MySQLItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Item saveItem(Item item) {
        // Check if the itemId is already in the database
        List<Item> existingItems = itemRepository.findByItemId(item.getItemId());
        if (!existingItems.isEmpty()) {
            // If item already exists, skip saving and just return the existing item
            System.out.println("Item already exists: " + item.getItemId());
            return existingItems.get(0); // return the first matched item from the database
        }

        // If item doesn't exist, save it
        System.out.println("Saving Item: ID=" + item.getId() + ", itemId=" + item.getItemId());
        return itemRepository.save(item);
    }

//    private void saveNewCategories(Set<Category> categories) {
//        if (categories != null) {
//            for (Category category : categories) {
//                // Check if Category already exists
//                if (!categoryRepository.existsById(category.getName())) {
//                    categoryRepository.save(category);
//                }
//            }
//        }
//    }
}
