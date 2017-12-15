package com.example.raynold.saloonapp.activity;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.raynold.saloonapp.R;

public class RegimenDetailActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageView mImageView;
    private TextView mDetailView;
    private TextView mCleanseView;
    private TextView mConditionView;
    private TextView mMoistureView;
    private TextView mStyleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regimen_detail);

        mToolbar = (Toolbar) findViewById(R.id.regimen_detail_toolbar);
        mImageView = (ImageView) findViewById(R.id.regimen_imageview);
        mDetailView = (TextView) findViewById(R.id.regimen_hair_detail);
        mCleanseView = (TextView) findViewById(R.id.cleanse_textview);
        mConditionView = (TextView) findViewById(R.id.conditon_textview);
        mMoistureView = (TextView) findViewById(R.id.moisture_textview);
        mStyleView = (TextView) findViewById(R.id.style_textview);

        Intent getIntent = getIntent();
        String hairName = getIntent.getStringExtra("name");
        String detail = getIntent.getStringExtra("detail");
        String condition = getIntent.getStringExtra("condition");
        String cleanse = getIntent.getStringExtra("cleanse");
        String moisture = getIntent.getStringExtra("moisture");
        String style = getIntent.getStringExtra("style");
        int imageRes = getIntent.getIntExtra("pic", 0);

        mDetailView.setText(detail);
        mCleanseView.setText(cleanse);
        mConditionView.setText(condition);
        mStyleView.setText(style);
        mMoistureView.setText(moisture);
        mImageView.setImageResource(imageRes);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(hairName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
