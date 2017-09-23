package com.example.raynold.saloonapp.dependencyInjection;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Room;

import com.example.raynold.saloonapp.data.SavedItemDatabase;
import com.example.raynold.saloonapp.data.SavedItemRepository;
import com.example.raynold.saloonapp.data.ShopDao;
import com.example.raynold.saloonapp.viewmodel.CustomViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by RAYNOLD on 9/20/2017.
 */

@Module
public class RoomModule {

    private final SavedItemDatabase database;

    public RoomModule(Application application) {
        this.database = Room.databaseBuilder(
                application,
                SavedItemDatabase.class,
                "WishList.db"
        ).build();
    }

    @Provides
    @Singleton
    SavedItemRepository provideListItemRepository(ShopDao shopItemDao){
        return new SavedItemRepository(shopItemDao);
    }

    @Provides
    @Singleton
    ShopDao provideListItemDao(SavedItemDatabase database){
        return database.mShopDao();
    }

    @Provides
    @Singleton
    SavedItemDatabase provideListItemDatabase(Application application){
        return database;
    }

    @Provides
    @Singleton
    ViewModelProvider.Factory provideViewModelFactory(SavedItemRepository repository){
        return new CustomViewModelFactory(repository);
    }
}
