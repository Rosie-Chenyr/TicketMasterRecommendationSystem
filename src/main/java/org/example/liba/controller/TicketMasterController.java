package org.example.liba.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.liba.entity.Item;
import org.example.liba.repository.ItemRepository;
import org.example.liba.service.ItemService;
import org.example.liba.service.TicketMasterService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TicketMasterController {
    @Autowired
    private final TicketMasterService ticketMasterService;
    @Autowired
    private final ItemService itemService;

    public TicketMasterController(TicketMasterService ticketMasterService, ItemService itemService) {
        this.ticketMasterService = ticketMasterService;
        this.itemService = itemService;
    }

    @GetMapping("/search")
    public ResponseEntity<?>  searchEvents(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(required = false) String keyword) {
        try{
            List<Item> res = ticketMasterService.search(lat, lon, keyword);
            for(Item item:res){
                itemService.saveItem(item);
            }
            return ResponseEntity.ok(res);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\": \"Unable to process the request\"}");
        }
    }
}
