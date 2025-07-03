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

    public void setFavoriteItems(String userId, List<String> ids){
        for(String id : ids){
            Optional<Item> existingItems = itemRepository.findById(id);
            if(!existingItems.isEmpty()){
                log.info("existing Items is NOT Empty");
                Item item = existingItems.get();
                History history = new History();
                history.setUserId(userId);
                history.setId(UUID.randomUUID().toString());
                history.setItem(item);
                if(!historyRepository.existsByUserIdAndItemId(userId, id)){
                    historyRepository.save(history);
                    log.info("history%s is saved ", history);
                }
            }
            log.info("existing Items is Empty");
        }
    }

    @Transactional
    public void unsetFavoriteItems(String userId, List<String> ids){
        for(String id : ids){
            historyRepository.deleteByUserIdAndItemId(userId, id);
        }
    }

    public Set<Item> getFavoriteItems(String userId) {
        List<History> histories = historyRepository.findByUserId(userId);
        return histories.stream()
                .map(History::getItem)
                .collect(Collectors.toSet());
    }
}
