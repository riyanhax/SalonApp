package com.example.raynold.saloonapp.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by RAYNOLD on 9/20/2017.
 */
@Entity
public class WishListModel {

    @PrimaryKey
    private String itemId;
    private String name;
    private String image;
    private String price;
    private String location;
    private String detail;

    public WishListModel(String itemId,String name, String image, String price, String location, String detail) {
        this.itemId = itemId;
        this.name = name;
        this.image = image;
        this.price = price;
        this.location = location;
        this.detail = detail;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
