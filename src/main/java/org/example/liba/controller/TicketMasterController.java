package org.example.liba.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.liba.entity.Item;
import org.example.liba.service.ItemService;
import org.example.liba.service.TicketMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;



import java.util.List;

import static org.example.liba.service.TicketMasterService.DEFAULT_RADIUS;

@RestController
@Slf4j
public class TicketMasterController {
    private final TicketMasterService ticketMasterService;
    private final ItemService itemService;


    @Autowired
    public TicketMasterController(TicketMasterService ticketMasterService, ItemService itemService) {
        this.ticketMasterService = ticketMasterService;
        this.itemService = itemService;
    }

    @GetMapping("/search")
    public ResponseEntity<?>  searchEvents(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(required = false) String keyword) {
        log.info("TicketMasterController entered with parameters lat%s, lon%s, keyword%s ", lat, lon, keyword);
        List<Item> result = ticketMasterService.searchByLatLon(lat, lon, keyword, DEFAULT_RADIUS);
        log.info("Get result%s ", result.get(0));
        if (!result.isEmpty()) {
            log.info("Result is empty ");
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(result);
        }
    }
}
