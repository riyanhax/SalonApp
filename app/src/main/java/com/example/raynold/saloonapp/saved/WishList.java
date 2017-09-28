package com.example.raynold.saloonapp.saved;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.raynold.saloonapp.R;
import com.example.raynold.saloonapp.Util.BaseActivity;
import com.example.raynold.saloonapp.saved.WishListFragment;

public class WishList extends BaseActivity {

    private static final String LIST_FRAG = "LIST_FRAG";
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);

        mToolbar = (Toolbar) findViewById(R.id.saved_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("WishList");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        WishListFragment wishListFragment = (WishListFragment) fragmentManager.findFragmentByTag(LIST_FRAG);

        if (wishListFragment == null) {
            wishListFragment = WishListFragment.newInstance();

            addFragmentToActivity(fragmentManager,wishListFragment,R.id.root_activity_list, LIST_FRAG);
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
