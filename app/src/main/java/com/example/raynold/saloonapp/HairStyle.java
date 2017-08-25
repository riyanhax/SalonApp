package com.example.raynold.saloonapp;

/**
 * Created by RAYNOLD on 8/22/2017.
 */

public class HairStyle {

    private String mTitle;
    private String mDescription;
    private String mPrice;
    private int mPicUrl;

    public HairStyle(String title, String description, String price, int picUrl) {
        mTitle = title;
        mDescription = description;
        mPrice = price;
        mPicUrl = picUrl;
    }


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public int getPicUrl() {
        return mPicUrl;
    }

    public void setPicUrl(int picUrl) {
        mPicUrl = picUrl;
    }
}
