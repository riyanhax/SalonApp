package com.example.raynold.saloonapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.example.raynold.saloonapp.Model.Shop;
import com.example.raynold.saloonapp.data.SavedItemRepository;
import com.example.raynold.saloonapp.data.WishListModel;

import java.util.List;

/**
 * Created by RAYNOLD on 9/20/2017.
 */

public class SavedItemCollectionViewModel extends ViewModel {

    private SavedItemRepository repository;

    SavedItemCollectionViewModel(SavedItemRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<WishListModel>> getListItems() {
        return repository.getListOfData();
    }

    public void deleteListItem(WishListModel listItem) {
        DeleteItemTask deleteItemTask = new DeleteItemTask();
        deleteItemTask.execute(listItem);
    }

    private class DeleteItemTask extends AsyncTask<WishListModel, Void, Void> {

        @Override
        protected Void doInBackground(WishListModel... item) {
            repository.deleteListItem(item[0]);
            return null;
        }
    }

}
