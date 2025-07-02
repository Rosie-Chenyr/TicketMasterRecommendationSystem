package org.example.liba.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ConditionalOnProperty(name = "database.type", havingValue = "mysql")
@EnableJpaRepositories(basePackages = "org.example.liba.repository.mysql")
public class JpaConfig {
}
