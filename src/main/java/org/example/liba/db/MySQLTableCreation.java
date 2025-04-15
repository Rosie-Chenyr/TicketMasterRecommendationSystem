package org.example.liba.db;// package

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class MySQLTableCreation implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(MySQLTableCreation.class);

    @Value("${spring.datasource.url}")
    private String url;

    @Override
    public void run(String... args) throws Exception {
        try (Connection conn = DriverManager.getConnection(url);
             Statement statement = conn.createStatement()) {
            // Connect to MySQL
            System.out.println("Connecting to " + url);

            // Create new tables
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS items ("
                    + "id VARCHAR(255) NOT NULL UNIQUE PRIMARY KEY,"
                    + "item_id VARCHAR(255) NOT NULL UNIQUE," // UNIQUE constraint here
                    + "name VARCHAR(255),"
                    + "rating FLOAT,"
                    + "address VARCHAR(255),"
                    + "date_time DATETIME,"
                    + "price FLOAT,"
                    + "image_url VARCHAR(255),"
                    + "url VARCHAR(255),"
                    + "distance FLOAT"
                    + ");");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS categories (\n" +
                    "    name VARCHAR(255) NOT NULL PRIMARY KEY\n" +
                    ");");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS categories_items (" +
                    "    category_name VARCHAR(255) NOT NULL," +
                    "    item_id VARCHAR(255) NOT NULL," +
                    "    PRIMARY KEY (category_name, item_id)," +
                    "    FOREIGN KEY (item_id) REFERENCES items(item_id)," +
                    "    FOREIGN KEY (category_name) REFERENCES categories(name)" +
                    ");");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users ("
                    + "id VARCHAR(255) NOT NULL UNIQUE PRIMARY KEY,"
                    + "user_id VARCHAR(255) NOT NULL UNIQUE,"
                    + "password VARCHAR(255) NOT NULL,"
                    + "first_name VARCHAR(255),"
                    + "last_name VARCHAR(255)"
                    + ");");

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS history ("
                    + "id VARCHAR(255) NOT NULL UNIQUE PRIMARY KEY,"
                    + "user_id VARCHAR(255) NOT NULL,"
                    + "item_id VARCHAR(255) NOT NULL,"
                    + "last_favor_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                    + "FOREIGN KEY (user_id) REFERENCES users(user_id),"
                    + "FOREIGN KEY (item_id) REFERENCES items(item_id)"
                    + ");");

            // Insert a fake user
            statement.executeUpdate("INSERT IGNORE INTO users (user_id, password, first_name, last_name)" +
                    " VALUES ('1111', 'mypassword', 'John', 'Smith')");

            logger.info("MySQL initialization is done.");
        } catch (SQLException e) {
            logger.error("SQL Exception occurred while creating mysql table: ", e);
        }
    }
}
