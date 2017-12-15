package com.example.raynold.saloonapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.raynold.saloonapp.R;

public class ServicesDetailActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sevices_detail);
        Intent intent = getIntent();
        String detail = intent.getStringExtra("detail");
        String title = intent.getStringExtra("title");

        //Toast.makeText(ServicesDetailActivity.this, "data: " + detail + " " + title, Toast.LENGTH_SHORT).show();

        mToolbar = (Toolbar) findViewById(R.id.services_detail_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(title);
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
