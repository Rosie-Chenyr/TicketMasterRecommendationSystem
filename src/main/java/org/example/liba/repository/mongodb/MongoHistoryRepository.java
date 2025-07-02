package org.example.liba.repository.mongodb;

import org.example.liba.entity.History;
import org.example.liba.repository.HistoryRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("mongoHistoryRepository")
public interface MongoHistoryRepository extends MongoRepository<History, String>, HistoryRepository {
    List<History> findByUserId(String userId);

    @Transactional // Ensures consistency during delete operations in MongoDB as well
    void deleteByUserIdAndItem_ItemId(String userId, String itemId);

    boolean existsByUserIdAndItem_ItemId(String userId, String itemId);
}
