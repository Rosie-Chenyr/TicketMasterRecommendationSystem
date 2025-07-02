package org.example.liba.controller;

import org.example.liba.dto.ItemResponse;
import org.example.liba.entity.Item;
import org.example.liba.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/history")
public class ItemHistoryController {
    @Autowired
    private HistoryService historyService;
    @PostMapping
    public ResponseEntity<String> setFavoriteItems(@RequestBody String body){
        try{
            Map<String, Object> input = ControllerHelper.readJsonFromBody(body);
            String userId = (String) input.get("user_id");
            List<String> itemIds = (List<String>) input.get("favorite");
            historyService.setFavoriteItems(userId, itemIds);
            return ControllerHelper.createSuccessResponse();
        } catch (IOException e){
            return ResponseEntity.badRequest().body("Error setting favorite items");
        }
    }

    @DeleteMapping
    public ResponseEntity<String> unsetFavoriteItems(@RequestBody String body){
        try{
            Map<String, Object> input = ControllerHelper.readJsonFromBody(body);
            String userId = (String) input.get("user_id");
            List<String> itemIds = (List<String>) input.get("favorite");
            historyService.unsetFavoriteItems(userId, itemIds);
            return ControllerHelper.createSuccessResponse();
        } catch (IOException e){
            return ResponseEntity.badRequest().body("Error unsetting favorite items");
        }
    }

    @GetMapping
    public ResponseEntity<String> getFavoriteItems(@RequestParam String userId){
        Set<Item> favoriteItems = historyService.getFavoriteItems(userId);

        try{
            List<ItemResponse> itemResponses = favoriteItems.stream()
                    .map(item -> new ItemResponse(item, true))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(ControllerHelper.convertToJson(itemResponses));
        } catch (IOException e){
            return ResponseEntity.badRequest().body("Error fetching favorite items");
        }
    }
}
