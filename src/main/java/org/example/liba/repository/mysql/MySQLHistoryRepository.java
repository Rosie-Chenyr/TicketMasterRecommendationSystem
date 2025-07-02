package org.example.liba.repository.mysql;

import org.example.liba.entity.History;
import org.example.liba.repository.HistoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("mySQLHistoryRepository")
public interface MySQLHistoryRepository extends JpaRepository<History, String>, HistoryRepository {
    List<History> findByUserId(String userId);
    void deleteByUserIdAndItem_Id(String userId, String id);
}

