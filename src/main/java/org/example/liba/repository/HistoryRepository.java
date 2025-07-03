package org.example.liba.repository;

import org.example.liba.entity.History;

import java.util.List;

public interface HistoryRepository {
    List<History> findByUserId(String userId);

    void deleteByUserIdAndItemId(String userId, String id);

    boolean existsByUserIdAndItemId(String userId, String itemId);

    History save(History history);
}
