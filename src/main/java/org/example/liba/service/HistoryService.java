package org.example.liba.service;

import lombok.extern.slf4j.Slf4j;
import org.example.liba.entity.History;
import org.example.liba.entity.Item;
import org.example.liba.repository.HistoryRepository;
import org.example.liba.repository.ItemRepository;
import org.example.liba.repository.mysql.MySQLHistoryRepository;
import org.example.liba.repository.mysql.MySQLItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HistoryService {
    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private ItemRepository itemRepository;

    public void setFavoriteItems(String userId, List<String> itemIds){
        for(String itemId : itemIds){
            List<Item> existingItems = itemRepository.findByItemId(itemId);
            if(!existingItems.isEmpty()){
                log.info("existintItems is NOT Empty");
                Item item = existingItems.get(0);
                History history = new History();
                history.setUserId(userId);
                history.setId(UUID.randomUUID().toString());
                history.setItem(item);
                historyRepository.save(history);
                log.info("history%s is saved ", history);
            }
            log.info("existintItems is Empty");
        }
    }

    @Transactional
    public void unsetFavoriteItems(String userId, List<String> itemIds){
        for(String itemId : itemIds){
            historyRepository.deleteByUserIdAndItem_ItemId(userId, itemId);
        }
    }

    public Set<Item> getFavoriteItems(String userId) {
        List<History> histories = historyRepository.findByUserId(userId);
        return histories.stream()
                .map(History::getItem)
                .collect(Collectors.toSet());
    }
}
