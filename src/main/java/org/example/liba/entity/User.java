package org.example.liba.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
@Document(collection = "users")
public class User implements Serializable {
    @Id
    @MongoId
    @JsonProperty("id")
    private String id;

    @Column(name = "user_id")// Explicitly map the field to the item_id column in the table
    @Field("user_id")// Explicitly map userId to the MongoDB user_id field.
    @Indexed(unique = true) // Ensure itemId is unique, if necessary
    private String userId;

    @Column(name = "first_name")
    @Field("first_name")
    private String firstName;

    @Column(name = "last_name")
    @Field("last_name")
    private String lastName;

    private String password;

    public User(){}

    public User(String userId, String password, String firstName, String lastName){
        this.userId = userId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
