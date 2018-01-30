package com.example.raynold.saloonapp.detail;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.raynold.saloonapp.R;
import com.example.raynold.saloonapp.util.BaseActivity;

public class WishListDetailActivity extends BaseActivity {

    private static final String DETAIL_FRAG = "DETAIL_FRAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wsh_list_detail);

        Intent i = getIntent();

        FragmentManager fragmentManager = getSupportFragmentManager();
        DetailFragment detailFragment = (DetailFragment) fragmentManager.findFragmentByTag(DETAIL_FRAG);

        if (detailFragment == null) {
            String id = i.getStringExtra("itemdId");
            String name = i.getStringExtra("name");
            String image = i.getStringExtra("image");
            String location = i.getStringExtra("location");
            String price = i.getStringExtra("price");
            int saved = i.getIntExtra("saved", 0);
            String details = i.getStringExtra("detail");

            detailFragment = DetailFragment.newInstance(id, name, image,location,details, price,saved);

            addFragmentToActivity(fragmentManager,detailFragment,R.id.root_fragment_detail, DETAIL_FRAG);
        }
    }

}
