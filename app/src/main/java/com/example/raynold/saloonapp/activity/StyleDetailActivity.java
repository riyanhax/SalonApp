package com.example.raynold.saloonapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.raynold.saloonapp.R;
import com.squareup.picasso.Picasso;

public class StyleDetailActivity extends AppCompatActivity {

    private TextView mTitle;
    private TextView mDescription;
    private ImageView mStyleImage;
    private Toolbar mStyleAppbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_detail);

        mStyleAppbar = (Toolbar) findViewById(R.id.style_appbar);
        mTitle = (TextView) findViewById(R.id.tv_style_title);
        mDescription = (TextView) findViewById(R.id.tv_style_description);
        mStyleImage = (ImageView) findViewById(R.id.style_image);
        setSupportActionBar(mStyleAppbar);

        Intent intent = getIntent();
        mTitle.setText(intent.getStringExtra("title"));
        mDescription.setText(intent.getStringExtra("description"));

        getSupportActionBar().setTitle(intent.getStringExtra("title"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Picasso.with(StyleDetailActivity.this).load(intent.getStringExtra("image")).into(mStyleImage);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
