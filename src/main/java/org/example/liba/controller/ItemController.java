package org.example.liba.controller;

import org.example.liba.model.Item;
import org.example.liba.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;
    @GetMapping
    public String listItems(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "items/list";
    }
    @PostMapping
    public String saveItem(@ModelAttribute Item item){
        itemRepository.save(item);
        return "redirect:/items";
    }

    @PostMapping("/{id}")
    public String updateItem(@PathVariable Long id, @ModelAttribute Item item){
        item.setId(id);
        itemRepository.save(item);
        return "redirect:/items";
    }

    @PostMapping("/{id}/delete")
    public String deleteitem(@PathVariable Long id){
        itemRepository.deleteById(id);
        return "redirect:/items";
    }
}
