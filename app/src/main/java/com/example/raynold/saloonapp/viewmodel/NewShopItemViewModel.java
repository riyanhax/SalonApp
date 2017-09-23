package com.example.raynold.saloonapp.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.example.raynold.saloonapp.Model.Shop;
import com.example.raynold.saloonapp.data.SavedItemRepository;
import com.example.raynold.saloonapp.data.WishListModel;

/**
 * Created by RAYNOLD on 9/20/2017.
 */

public class NewShopItemViewModel extends ViewModel {

    private SavedItemRepository repository;

    NewShopItemViewModel(SavedItemRepository repository) {
        this.repository = repository;
    }

    /**
     * Attach our LiveData to the Database
     */
    public void addNewItemToDatabase(WishListModel listItem){
        new AddItemTask().execute(listItem);
    }

    private class AddItemTask extends AsyncTask<WishListModel, Void, Void> {

        @Override
        protected Void doInBackground(WishListModel... item) {
            repository.createNewListItem(item[0]);
            return null;
        }
    }
}
