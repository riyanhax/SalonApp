package com.example.raynold.saloonapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.raynold.saloonapp.R;

public class JobActivity extends AppCompatActivity {

    private Toolbar mJobToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);

        mJobToolbar = (Toolbar) findViewById(R.id.job_toolbar);
        setSupportActionBar(mJobToolbar);
        getSupportActionBar().setTitle("Job");


        setSupportActionBar(mJobToolbar);
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
