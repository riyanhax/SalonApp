package com.example.raynold.saloonapp;

import android.content.ContentValues;
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
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mHairRecyclerview;
    private LinearLayoutManager mLinearLayoutManager;
    private HairStyleAdapter mStyleAdapter;
    private List<HairStyle> mHairStyleList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private Toolbar mToolbar;
    private RecyclerView.ItemDecoration mItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open, R.string.close);
        mHairRecyclerview = (RecyclerView) findViewById(R.id.style_recyclerview);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

        mHairRecyclerview.addItemDecoration(mItemDecoration);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mHairRecyclerview.setLayoutManager(mLinearLayoutManager);
        mHairRecyclerview.setHasFixedSize(true);

        mHairStyleList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {

            mHairStyleList.add(new HairStyle("Brazillian Wig", "Cut hair, Faded black", "5000", R.drawable.natural_graduated_bob));
            mHairStyleList.add(new HairStyle("Brazillian Wig", "Cut hair, Faded black", "2000", R.drawable.pixiew_with_long_bangs));
        }


        mStyleAdapter = new HairStyleAdapter(mHairStyleList);
        mHairRecyclerview.setAdapter(mStyleAdapter);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);

        setNavigationViewListener();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mActionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_job:
                startActivity(new Intent(MainActivity.this, JobActivity.class));
                break;

            case R.id.menu_appointment:
                    startActivity(new Intent(MainActivity.this, AppointmentActivity.class));
                break;

            case R.id.menu_call_us:
                startActivity(new Intent(MainActivity.this, CallUsActivity.class));
                break;
            case R.id.menu_services:
                startActivity(new Intent(MainActivity.this, ServicesActivity.class));
                break;
            case R.id.menu_location:
                startActivity(new Intent(MainActivity.this, LocationActivity.class));
                break;
            case R.id.menu_shop:
                startActivity(new Intent(MainActivity.this, ShopActivity.class));
                break;
        }
        //mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
}
