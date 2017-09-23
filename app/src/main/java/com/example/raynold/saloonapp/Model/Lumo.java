package com.example.raynold.saloonapp.Model;

import android.app.Application;

import com.example.raynold.saloonapp.dependencyInjection.ApplicationComponent;
import com.example.raynold.saloonapp.dependencyInjection.ApplicationModule;
import com.example.raynold.saloonapp.dependencyInjection.DaggerApplicationComponent;
import com.example.raynold.saloonapp.dependencyInjection.RoomModule;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by RAYNOLD on 9/5/2017.
 */

public class Lumo extends Application{
    private ApplicationComponent mApplicationComponemt;

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);

        mApplicationComponemt = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .roomModule(new RoomModule(this))
                .build();
    }
    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponemt;
    }
}
