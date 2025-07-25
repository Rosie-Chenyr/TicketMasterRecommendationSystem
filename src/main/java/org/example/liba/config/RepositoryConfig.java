package org.example.liba.config;

import org.example.liba.entity.User;
import org.example.liba.repository.CategoryRepository;
import org.example.liba.repository.HistoryRepository;
import org.example.liba.repository.ItemRepository;
import org.example.liba.repository.UserRepository;
import org.example.liba.repository.mongodb.MongoCategoryRepository;
import org.example.liba.repository.mongodb.MongoHistoryRepository;
import org.example.liba.repository.mongodb.MongoItemRepository;
import org.example.liba.repository.mongodb.MongoUserRepository;
import org.example.liba.repository.mysql.MySQLCategoryRepository;
import org.example.liba.repository.mysql.MySQLHistoryRepository;
import org.example.liba.repository.mysql.MySQLItemRepository;
import org.example.liba.repository.mysql.MySQLUserRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class RepositoryConfig {

    @Value("${database.type}")
    private String dbType;

    @Value("${database.mysql}")
    private String mysql;

    private final ObjectProvider<MySQLItemRepository> mySQLItemRepositoryProvider;
    private final ObjectProvider<MongoItemRepository> mongoItemRepositoryProvider;

    private final ObjectProvider<MySQLHistoryRepository> mySQLHistoryRepositoryProvider;
    private final ObjectProvider<MongoHistoryRepository> mongoHistoryRepositoryProvider;

    private final ObjectProvider<MySQLCategoryRepository> mySQLCategoryRepositoryProvider;
    private final ObjectProvider<MongoCategoryRepository> mongoCategoryRepositoryProvider;

    private final ObjectProvider<MySQLUserRepository> mySQLUserRepositoryProvider;
    private final ObjectProvider<MongoUserRepository> mongoUserRepositoryProvider;

    public RepositoryConfig(
            ObjectProvider<MySQLItemRepository> mySQLItemRepositoryProvider,
            ObjectProvider<MongoItemRepository> mongoItemRepositoryProvider,
            ObjectProvider<MySQLHistoryRepository> mySQLHistoryRepositoryProvider,
            ObjectProvider<MongoHistoryRepository> mongoHistoryRepositoryProvider,
            ObjectProvider<MySQLCategoryRepository> mySQLCategoryRepositoryProvider,
            ObjectProvider<MongoCategoryRepository> mongoCategoryRepositoryProvider,
            ObjectProvider<MySQLUserRepository> mySQLUserRepositoryProvider,
            ObjectProvider<MongoUserRepository> mongoUserRepositoryProvider
    ) {
        this.mySQLItemRepositoryProvider = mySQLItemRepositoryProvider;
        this.mongoItemRepositoryProvider = mongoItemRepositoryProvider;
        this.mySQLHistoryRepositoryProvider = mySQLHistoryRepositoryProvider;
        this.mongoHistoryRepositoryProvider = mongoHistoryRepositoryProvider;
        this.mySQLCategoryRepositoryProvider = mySQLCategoryRepositoryProvider;
        this.mongoCategoryRepositoryProvider = mongoCategoryRepositoryProvider;
        this.mongoUserRepositoryProvider = mongoUserRepositoryProvider;
        this.mySQLUserRepositoryProvider = mySQLUserRepositoryProvider;
    }

    @Bean
    public ItemRepository itemRepository() {
        if (dbType.equalsIgnoreCase(mysql)) {
            return mySQLItemRepositoryProvider.getIfAvailable();
        } else {
            return mongoItemRepositoryProvider.getIfAvailable();
        }
    }

    @Bean
    public HistoryRepository historyRepository() {
        if (dbType.equalsIgnoreCase(mysql)) {
            return mySQLHistoryRepositoryProvider.getIfAvailable();
        } else {
            return mongoHistoryRepositoryProvider.getIfAvailable();
        }
    }

    @Bean
    public CategoryRepository categoryRepository() {
        if (dbType.equalsIgnoreCase(mysql)) {
            return mySQLCategoryRepositoryProvider.getIfAvailable();
        } else {
            return mongoCategoryRepositoryProvider.getIfAvailable();
        }
    }

    @Bean
    public UserRepository userRepository() {
        if (dbType.equalsIgnoreCase(mysql)) {
            return mySQLUserRepositoryProvider.getIfAvailable();
        } else {
            return mongoUserRepositoryProvider.getIfAvailable();
        }
    }

    @PostConstruct
    public void init() {
        System.out.println("🔧 Using database type: " + dbType);
    }
}
