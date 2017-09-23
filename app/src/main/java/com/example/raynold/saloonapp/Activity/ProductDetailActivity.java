package com.example.raynold.saloonapp.Activity;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.raynold.saloonapp.Model.Lumo;
import com.example.raynold.saloonapp.R;
import com.example.raynold.saloonapp.data.WishListModel;
import com.example.raynold.saloonapp.viewmodel.NewShopItemViewModel;
import com.example.raynold.saloonapp.viewmodel.SavedItemCollectionViewModel;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class ProductDetailActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    NewShopItemViewModel listItemCollectionViewModel;
    private Toolbar mToolbar;
    private TextView mProductName;
    private TextView mProductPrice;
    private TextView mProductInfo;
    private ImageView mProductImage;
    private TextView mProductLocation;
    private ImageButton mWishlist;
    private int wish = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        ((Lumo) this.getApplication())
                .getApplicationComponent()
                .inject(this);

        //Set up and subscribe (observe) to the ViewModel
        listItemCollectionViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(NewShopItemViewModel.class);

        mToolbar = (Toolbar) findViewById(R.id.product_layout);
        mProductName = (TextView) findViewById(R.id.product_name);
        mProductPrice = (TextView) findViewById(R.id.product_price);
        mProductImage = (ImageView) findViewById(R.id.product_image);
        mProductLocation = (TextView) findViewById(R.id.product_location);
        mProductInfo = (TextView) findViewById(R.id.product_info);
        mWishlist = (ImageButton) findViewById(R.id.wishlist_detail_btn);
        setSupportActionBar(mToolbar);

        final String productName = getIntent().getStringExtra("name");
        final String productPrice = getIntent().getStringExtra("price" );
        final String productImage = getIntent().getStringExtra("image");
        final String location = getIntent().getStringExtra("location");
        final String details = getIntent().getStringExtra("detail");
        Picasso picasso = Picasso.with(this);
        picasso.setIndicatorsEnabled(false);

        getSupportActionBar().setTitle(productName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mProductName.setText(productName);
        mProductPrice.setText(String.valueOf("N " +productPrice));

        Picasso.with(this).load(productImage).into(mProductImage);
        mProductLocation.setText(location);
        mProductInfo.setText(details);



        mWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wish = 1;
                    //mWishlist.setImageResource(R.drawable.like);
                    WishListModel wishListModel = new WishListModel(productName,productName,productImage,productPrice,location,details);
                     listItemCollectionViewModel.addNewItemToDatabase(wishListModel);
            }
        });

                    if (wish == 0) {
                        mWishlist.setImageResource(R.drawable.heart_button);
                    } else if (wish ==1 ) {

                        mWishlist.setImageResource(R.drawable.like);
                    }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
