package com.example.raynold.saloonapp.Model;

/**
 * Created by RAYNOLD on 8/29/2017.
 */

public class Shop {

    private String mPName;
    private int mPImage;
    private int mPrice;
    private String mLocation;

    public Shop(String PName, int PImage, int price, String mpLocation) {
        mPName = PName;
        mPImage = PImage;
        mPrice = price;
        this.mLocation = mpLocation;
    }

    public String getPName() {
        return mPName;
    }

    public void setPName(String PName) {
        mPName = PName;
    }

    public int getPImage() {
        return mPImage;
    }

    public void setPImage(int PImage) {
        mPImage = PImage;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int price) {
        mPrice = price;
    }

    public String getMLocation() {
        return mLocation;
    }

    public void setMLocation(String mpLocation) {
        this.mLocation = mpLocation;
    }
}
