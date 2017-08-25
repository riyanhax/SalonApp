package com.example.raynold.saloonapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class AppointmentActivity extends AppCompatActivity {

    private Toolbar mAppointmentToolbar;
    //private DrawerLayout mDrawerLayout;
    //private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open, R.string.close);

        mAppointmentToolbar = (Toolbar) findViewById(R.id.appoint_toolbar);
        setSupportActionBar(mAppointmentToolbar);
        getSupportActionBar().setTitle("Appointment");

//        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
//        mActionBarDrawerToggle.syncState();

        setSupportActionBar(mAppointmentToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //mAppointmentToolbar.setNavigationIcon(R.mipmap.ic_launcher);

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
