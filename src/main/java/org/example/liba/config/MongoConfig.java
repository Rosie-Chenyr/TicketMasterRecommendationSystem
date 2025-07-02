package org.example.liba.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ConditionalOnProperty(name = "database.type", havingValue = "mongo")
@EnableMongoRepositories(basePackages = "org.example.liba.repository.mongodb")
public class MongoConfig {
}