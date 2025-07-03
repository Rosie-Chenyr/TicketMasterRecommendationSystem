package org.example.liba.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.annotation.Documented;
import java.time.LocalDateTime;
import java.util.Set;
@Entity
@Table(name="items")
@Document(collection = "items") // mongoDB annotation
@JsonSerialize
@JsonDeserialize
public class Item implements Serializable {
    @Id // JPA ID
    @JsonProperty("id")
    private String id; // Use String for the ID type to be compatible with MongoDB

    // Explicitly map the field to the item_id column in the table and make it unique
    @Column(name = "item_id", unique = true)
    @JsonProperty("item_id")
    private String itemId;
    private String name;
    private double rating;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "date_time")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateTime;

    private double price;
    private String address;

    @ManyToMany(mappedBy = "items")
    private Set<Category> categories;

    private String imageUrl;
    private String url;
    private double distance;

    private Item(ItemBuilder builder) {
        this.id = builder.id;
        this.itemId = builder.itemId;
        this.name = builder.name;
        this.rating = builder.rating;
        this.dateTime = builder.dateTime;
        this.price = builder.price;
        this.address = builder.address;
        this.categories = builder.categories;
        this.imageUrl = builder.imageUrl;
        this.url = builder.url;
        this.distance = builder.distance;
    }

    public Item(){}

    public void setCategories(Set<Category> savedCategories) {
    }

    public void setId(String id) {
    }

    public static class ItemBuilder{
        public String itemId;
        private String id;
        private String name;
        private double rating;
        private LocalDateTime dateTime;
        private double price;
        private String address;
        private Set<Category> categories;
        private String imageUrl;
        private String url;
        private double distance;

        public ItemBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public ItemBuilder setItemId(String itemId) {
            this.itemId = itemId;
            return this;
        }

        public ItemBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ItemBuilder setRating(double rating) {
            this.rating = rating;
            return this;
        }

        public ItemBuilder setPrice(double price) {
            this.price = price;
            return this;
        }

        public ItemBuilder setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public ItemBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public ItemBuilder setCategories(Set<Category> categories) {
            this.categories = categories;
            return this;
        }

        public ItemBuilder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public ItemBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public ItemBuilder setDistance(double distance) {
            this.distance = distance;
            return this;
        }

        public Item build() {
            return new Item(this);
        }

    }

    public String getId() {
        return id;
    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public double getPrice() {
        return price;
    }

    public String getAddress() {
        return address;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                ", dateTime='" + dateTime + '\'' +
                ", price='" + price + '\'' +
                ", address='" + address + '\'' +
                ", categories=" + categories +
                ", imageUrl='" + imageUrl + '\'' +
                ", url='" + url + '\'' +
                ", distance=" + distance +
                '}';
    }
}
