package org.example.liba.repository;

import org.example.liba.entity.History;

import java.util.List;

public interface HistoryRepository {
    List<History> findByUserId(String userId);

    void deleteByUserIdAndItem_ItemId(String userId, String itemId);

    boolean existsByUserIdAndItem_ItemId(String userId, String itemId);

    History save(History history);
}
