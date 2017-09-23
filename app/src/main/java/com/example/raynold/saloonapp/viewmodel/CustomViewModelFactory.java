package com.example.raynold.saloonapp.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.raynold.saloonapp.data.SavedItemRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by RAYNOLD on 9/20/2017.
 */

@Singleton
public class CustomViewModelFactory implements ViewModelProvider.Factory{

    private final SavedItemRepository repository;

    @Inject
    public CustomViewModelFactory(SavedItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SavedItemCollectionViewModel.class))
            return (T) new SavedItemCollectionViewModel(repository);

        else if (modelClass.isAssignableFrom(ShopItemViewModel.class))
            return (T) new ShopItemViewModel(repository);

        else if (modelClass.isAssignableFrom(NewShopItemViewModel.class))
            return (T) new NewShopItemViewModel(repository);

        else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }
}
