package org.example.liba.db;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MongoDBTableCreation implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(MongoDBTableCreation.class); // 打log
    @Autowired
    private MongoClient mongoClient; // connect to MongoDB

    @Override
    public void run(String... args) throws Exception {
        MongoDatabase db = mongoClient.getDatabase("libaproject");

        /**
         // Remove old collections (for testing usage)
         db.getCollection("items").drop();
         db.getCollection("category").drop();
         db.getCollection("users").drop();
         db.getCollection("history").drop();
         */

        // insert fake user data if not exist
        MongoCollection<Document> usersCollection = db.getCollection("users");
        Document filter = new Document("user_id", "1111");
        Document update = new Document("$setOnInsert",
                new Document()
                        .append("user_id", "1111")
                        .append("password", "mypassword")
                        .append("first_name", "John")
                        .append("last_name", "Smith")
        );
        usersCollection.findOneAndUpdate(filter, update, new FindOneAndUpdateOptions().upsert(true));

        logger.info("MongoDB initialization is done.");

    }
}
