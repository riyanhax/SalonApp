package com.example.raynold.saloonapp.activity;

import android.content.Intent;
import android.media.Image;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.raynold.saloonapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegimenDetailActivity extends AppCompatActivity {

    @BindView(R.id.regimen_imageview)
    ImageView mImageView;
    @BindView(R.id.regimen_hair_detail)
    TextView mDetailView;
    @BindView(R.id.cleanse_textview)
    TextView mCleanseView;
    @BindView(R.id.conditon_textview)
    TextView mConditionView;
    @BindView(R.id.moisture_textview)
    TextView mMoistureView;
    @BindView(R.id.style_textview)
    TextView mStyleView;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regimen_detail);
        ButterKnife.bind(this);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandableAppbar);
        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        Intent getIntent = getIntent();
        String hairName = getIntent.getStringExtra("name");
        String detail = getIntent.getStringExtra("detail");
        String condition = getIntent.getStringExtra("condition");
        String cleanse = getIntent.getStringExtra("cleanse");
        String moisture = getIntent.getStringExtra("moisture");
        String style = getIntent.getStringExtra("style");
        int imageRes = getIntent.getIntExtra("pic", 0);

        mCollapsingToolbarLayout.setTitle(hairName);

        mDetailView.setText(detail);
        mCleanseView.setText(cleanse);
        mConditionView.setText(condition);
        mStyleView.setText(style);
        mMoistureView.setText(moisture);
        mImageView.setImageResource(imageRes);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
