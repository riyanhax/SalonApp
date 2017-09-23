package com.example.raynold.saloonapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.raynold.saloonapp.Model.Shop;
import com.example.raynold.saloonapp.data.SavedItemRepository;
import com.example.raynold.saloonapp.data.WishListModel;

/**
 * Created by RAYNOLD on 9/20/2017.
 */

public class ShopItemViewModel extends ViewModel {

    private SavedItemRepository repository;

    ShopItemViewModel(SavedItemRepository repository) {
        this.repository = repository;
    }

    public LiveData<WishListModel> getListItemById(String itemId){
        return repository.getListItem(itemId);
    }
}
