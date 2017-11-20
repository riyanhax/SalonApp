package com.example.raynold.saloonapp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by RAYNOLD on 8/29/2017.
 */
@Entity
public class Shop {

    @PrimaryKey
    private String name;
    private String image;
    private String price;
    private String location;
    private String detail;

    public Shop(){}

    public Shop(String name, String image, String price, String location, String detail) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.location = location;
        this.detail = detail;
    }

    public String getDetails() {
        return detail;
    }

    public void setDetails(String details) {
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
