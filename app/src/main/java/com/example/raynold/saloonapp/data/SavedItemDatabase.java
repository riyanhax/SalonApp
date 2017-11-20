package com.example.raynold.saloonapp.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by RAYNOLD on 9/19/2017.
 */

@Database(entities = {WishListModel.class},version = 2)
public abstract class SavedItemDatabase extends RoomDatabase {

    public abstract ShopDao mShopDao();
}
