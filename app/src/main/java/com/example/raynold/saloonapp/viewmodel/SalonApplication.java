package com.example.raynold.saloonapp.viewmodel;

import android.app.Application;

import com.example.raynold.saloonapp.dependencyInjection.ApplicationComponent;

/**
 * Created by RAYNOLD on 9/19/2017.
 */

public class SalonApplication extends Application{

    private ApplicationComponent mApplicationComponemt;

    public ApplicationComponent getApplicationComponemt() {
        return mApplicationComponemt;
    }
}
