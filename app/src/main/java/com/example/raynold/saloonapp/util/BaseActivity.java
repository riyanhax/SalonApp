package com.example.raynold.saloonapp.util;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by RAYNOLD on 9/19/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public static void addFragmentToActivity(android.support.v4.app.FragmentManager fragmentManager,
                                             android.support.v4.app.Fragment fragment,
                                             int frameId,
                                             String tag){
        android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId,fragment, tag);
        transaction.commit();
    }
}


