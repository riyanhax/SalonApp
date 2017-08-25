package com.example.raynold.saloonapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class ServicesActivity extends AppCompatActivity {

    private Toolbar mServicesToolbar;
    private ServicesAdapter mServicesAdapter;
    private RecyclerView mServicesRecycler;
    private List<Services> mServicesList = new ArrayList<>();
    private RecyclerView.ItemDecoration mItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);


        mServicesList.add(new Services("Color", "Starting at N5000", R.drawable.natural_graduated_bob));
        mServicesList.add(new Services("Color", "Starting at N3000", R.drawable.pixiew_with_long_bangs));
        mServicesList.add(new Services("Color", "Starting at N5000", R.drawable.natural_graduated_bob));
        mServicesList.add(new Services("Color", "Starting at N3000", R.drawable.pixiew_with_long_bangs));

        mServicesAdapter = new ServicesAdapter(mServicesList);

        mServicesToolbar = (Toolbar) findViewById(R.id.services_toolbar);
        mServicesRecycler = (RecyclerView) findViewById(R.id.services_recyclerview);
        mServicesRecycler.setLayoutManager(new LinearLayoutManager(this));
        mItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mServicesRecycler.setHasFixedSize(true);
        mServicesRecycler.setAdapter(mServicesAdapter);
        setSupportActionBar(mServicesToolbar);
        getSupportActionBar().setTitle("Services");

        setSupportActionBar(mServicesToolbar);
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
