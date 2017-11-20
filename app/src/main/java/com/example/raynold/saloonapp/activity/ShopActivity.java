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
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        mToolbar = (Toolbar) findViewById(R.id.shop_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Shop");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        ShopFragment shopFragment = (ShopFragment) fragmentManager.findFragmentByTag(SHOP_FRAG);

        if (shopFragment == null) {
            shopFragment = ShopFragment.newInstance();

            addFragmentToActivity(fragmentManager,shopFragment,R.id.root_shop_activity, SHOP_FRAG);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
