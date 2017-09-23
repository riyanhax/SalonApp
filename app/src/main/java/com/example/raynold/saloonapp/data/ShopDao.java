package com.example.raynold.saloonapp.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.raynold.saloonapp.Model.Shop;

import java.util.List;

/**
 * Created by RAYNOLD on 9/19/2017.
 */

@Dao
public interface ShopDao {

    @Query("SELECT * FROM WishListModel")
    LiveData<List<WishListModel>> getShopSavedItems();

    @Query("SELECT * FROM WishListModel WHERE itemId = :itemId")
    LiveData<WishListModel> getShopItemById(String itemId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertShopSavedItems(WishListModel wishListModel);

    @Delete
    void deleteShopItem(WishListModel wishListModel);

}
