package com.example.raynold.saloonapp.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.raynold.saloonapp.R;

public class CallUsActivity extends AppCompatActivity {

    private Toolbar mCallUsToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_us);

        mCallUsToolbar = (Toolbar) findViewById(R.id.call_us_toolbar);
        setSupportActionBar(mCallUsToolbar);
        getSupportActionBar().setTitle("Call Us");

        setSupportActionBar(mCallUsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:08160195225"));
        startActivity(callIntent);
        finish();

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
