package com.example.raynold.saloonapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.raynold.saloonapp.R;
import com.example.raynold.saloonapp.Model.Shop;
import com.example.raynold.saloonapp.Adapter.ShopAdapter;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity {

    private Toolbar mShopToolbar;
    private ShopAdapter mShopAdapter;
    private List<Shop> mShopList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.ItemDecoration mItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        mShopList.add(new Shop("Brazillian Hair", R.drawable.amla_oil, 2000, "Luminos salon"));
        mShopList.add(new Shop("Brazillian Hair", R.drawable.natural_graduated_bob, 2000, "Luminos salon"));
        mShopList.add(new Shop("Brazillian Hair", R.drawable.amla_oil, 2000, "Luminos salon"));
        mShopList.add(new Shop("Brazillian Hair", R.drawable.natural_graduated_bob, 2000, "Luminos salon"));
        mShopList.add(new Shop("Brazillian Hair", R.drawable.natural_graduated_bob, 2000, "Luminos salon"));

        mShopAdapter = new ShopAdapter(mShopList);

        mShopToolbar = (Toolbar) findViewById(R.id.shop_toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.shop_recycler);
        mItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(mItemDecoration);

        mRecyclerView.setAdapter(mShopAdapter);

        setSupportActionBar(mShopToolbar);
        getSupportActionBar().setTitle("Shop");

        setSupportActionBar(mShopToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
