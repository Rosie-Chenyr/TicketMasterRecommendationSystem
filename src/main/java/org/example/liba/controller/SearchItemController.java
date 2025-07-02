package org.example.liba.controller;

import org.example.liba.entity.Item;
import org.example.liba.service.ItemService;
import org.example.liba.service.TicketMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

@RestController
public class SearchItemController {
    @Autowired
    private TicketMasterService ticketMasterService;

    @Autowired
    private ItemService itemService;

//    @GetMapping("/search")
//    public ResponseEntity<?> searchItems(@RequestParam(required = false) String city),
//                                        @RequestParam(required = false) Integer postalCode,
//                                        @RequestParam(required = false) Double lat,
//                                        @RequestParam(required = false) Double lon,
//                                        @RequestParam(required = false) String term) {
//        try{
//            List<Item> items;
//            if(city!=null){
//                items = ticketMasterService.searchByCity(city, term);
//            }
//            else if(postalCode != null){
//                items = ticketMasterService.searchByZipcode(postalCode, term);
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"error\":\"Unable to process the request\"}");
//        }
//    }
}
