package org.example.liba.controller;

import org.example.liba.entity.Category;
import org.example.liba.entity.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class ItemController {
    @GetMapping("/item")
    public ResponseEntity<Item> getItem(){
        Item item = new Item.ItemBuilder()
                .setItemId("123")
                .setName("Sample Item")
                .setRating(4.5)
                .setDistance(1.5)
                //.setCategories(Set.of("Category1", "Category2"))
                .setUrl("http://example.com/item")
                .setAddress("123 Sample St, Sample City")
                .setImageUrl("http://example.com/image.jpg")
                .build();
//        item.setId(Long.valueOf("123"));

        return ResponseEntity.ok(item);
    }
}


