package com.example.raynold.saloonapp.model;

/**
 * Created by RAYNOLD on 8/22/2017.
 */

public class HairStyle {

    private String title;
    private String description;
        private String picUrl;

    public HairStyle(){}

    public HairStyle(String title, String description, String picUrl) {
        this.title = title;
        this.description = description;
        this.picUrl = picUrl;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return this.picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
