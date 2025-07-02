package org.example.liba.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "history")
@Document(collection = "history")
public class History implements Serializable {
    @Id
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
    private Item item;

    //Getter and Setters

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public Item getItem() {
        return item;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
