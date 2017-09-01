package com.example.raynold.saloonapp.Model;

/**
 * Created by RAYNOLD on 8/24/2017.
 */

public class Services {

    private String mTitle;
    private String mPrice;
    private int mImageResource;

    public Services(String title, String price, int imageResource) {
        mTitle = title;
        mPrice = price;
        mImageResource = imageResource;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public void setImageResource(int imageResource) {
        mImageResource = imageResource;
    }
}
