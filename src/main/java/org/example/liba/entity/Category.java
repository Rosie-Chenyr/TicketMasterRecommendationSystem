package org.example.liba.entity;// package

//import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="categories")
//@Document(collection = "categories") // MongoDB annotation
public class Category {

    @Id
    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = CascadeType.ALL) // Consider CascadeType.ALL or appropriate cascade options
    @JoinTable(
            name = "categories_items", // Use the actual join table name
            joinColumns = @JoinColumn(name = "category_name"), // Column in the join table that references this entity
            inverseJoinColumns = @JoinColumn(name = "item_id") // Column in the join table that references the other entity
    )
    private Set<Item> items;

    // Constructors, Getters, and Setters
    public Category() {}

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}