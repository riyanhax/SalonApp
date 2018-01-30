package com.example.raynold.saloonapp.activity;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.raynold.saloonapp.R;
import com.example.raynold.saloonapp.ShopFragment;
import com.example.raynold.saloonapp.util.BaseActivity;

public class ShopActivity extends BaseActivity{

    private static final String SHOP_FRAG = "SHOP_FRAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        FragmentManager fragmentManager = getSupportFragmentManager();
        ShopFragment shopFragment = new ShopFragment();


            //shopFragment = ShopFragment.newInstance();
            fragmentManager.beginTransaction().add(R.id.root_shop_activity, shopFragment)
                    .commit();

            //addFragmentToActivity(fragmentManager,shopFragment,R.id.root_shop_activity, SHOP_FRAG);


    }

}
