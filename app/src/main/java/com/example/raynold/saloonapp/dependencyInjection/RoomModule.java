package com.example.raynold.saloonapp.dependencyInjection;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

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

    static final Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };
    private final SavedItemDatabase database;

    public RoomModule(Application application) {
        this.database = Room.databaseBuilder(
                application,
                SavedItemDatabase.class,
                "WishList.db"
        ).fallbackToDestructiveMigration().build();
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
