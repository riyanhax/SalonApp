package com.example.raynold.saloonapp.data;

import android.arch.lifecycle.LiveData;

import com.example.raynold.saloonapp.Model.Shop;

import java.util.List;

/**
 * Created by RAYNOLD on 9/19/2017.
 */

public class SavedItemRepository {

    private final ShopDao mShopDao;

    public SavedItemRepository(ShopDao shop) {
        mShopDao = shop;
    }

    public LiveData<Shop> getShopItem(String itemId) {
        return mShopDao.getShopItemById(itemId);
    }

    public void deleteShopItem(Shop itemId) {
        mShopDao.deleteShopItem(itemId);
    }

    public void insertShopItem(Shop itemId) {
        mShopDao.insertShopSavedItems(itemId);
    }
}
