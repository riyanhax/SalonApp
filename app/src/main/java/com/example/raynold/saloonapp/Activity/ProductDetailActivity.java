package com.example.raynold.saloonapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.raynold.saloonapp.R;
import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextView mProductName;
    private TextView mProductPrice;
    private TextView mProductInfo;
    private ImageView mProductImage;
    private TextView mProductLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        mToolbar = (Toolbar) findViewById(R.id.product_layout);
        mProductName = (TextView) findViewById(R.id.product_name);
        mProductPrice = (TextView) findViewById(R.id.product_price);
        mProductImage = (ImageView) findViewById(R.id.product_image);
        mProductLocation = (TextView) findViewById(R.id.product_location);
        mProductInfo = (TextView) findViewById(R.id.product_info);
        setSupportActionBar(mToolbar);

        String productName = getIntent().getStringExtra("name");
        String productPrice = getIntent().getStringExtra("price" );
        String productImage = getIntent().getStringExtra("image");
        String location = getIntent().getStringExtra("location");
        String details = getIntent().getStringExtra("detail");
        Picasso picasso = Picasso.with(this);
        picasso.setIndicatorsEnabled(false);

        getSupportActionBar().setTitle(productName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProductName.setText(productName);
        mProductPrice.setText(String.valueOf("N " +productPrice));

        Picasso.with(this).load(productImage).into(mProductImage);
        mProductLocation.setText(location);
        mProductInfo.setText(details);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }
}
