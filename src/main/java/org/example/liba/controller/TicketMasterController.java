package org.example.liba.controller;

import org.example.liba.service.TicketMasterService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TicketMasterController {
    @Autowired
    private final TicketMasterService ticketMasterService;

    public TicketMasterController(TicketMasterService ticketMasterService) {
        this.ticketMasterService = ticketMasterService;
    }

    @GetMapping("/search")
    public JSONArray searchEvents(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(required = false) String keyword) {
        try{
            return ticketMasterService.search(lat, lon, keyword);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new JSONArray()).getBody();
        }
    }
}
