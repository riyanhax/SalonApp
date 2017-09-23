package com.example.raynold.saloonapp.data;

import android.arch.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by RAYNOLD on 9/19/2017.
 */

public class SavedItemRepository {

    private final ShopDao mShopDao;

    @Inject
    public SavedItemRepository(ShopDao shop) {
        mShopDao = shop;
    }

    public LiveData<List<WishListModel>> getListOfData(){
        return mShopDao.getShopSavedItems();
    }

    public LiveData<WishListModel> getListItem(String itemId){
        return mShopDao.getShopItemById(itemId);
    }

    public Long createNewListItem(WishListModel listItem){
        return mShopDao.insertShopSavedItems(listItem);
    }

    public void deleteListItem(WishListModel listItem){
        mShopDao.deleteShopItem(listItem);
    }
}
