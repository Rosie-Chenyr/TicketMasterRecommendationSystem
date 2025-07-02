package org.example.liba.dto;

import org.example.liba.entity.Item;
import org.example.liba.service.ItemService;

public class ItemResponse {
    private Item item;
    private boolean favorite;

    public ItemResponse(Item item, boolean favorite) {
        this.item = item;
        this.favorite = favorite;
    }

    public Item getItem() {
        return item;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
