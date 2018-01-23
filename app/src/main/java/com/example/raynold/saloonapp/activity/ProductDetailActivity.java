package com.example.raynold.saloonapp.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.media.Image;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.raynold.saloonapp.model.Lumo;
import com.example.raynold.saloonapp.R;
import com.example.raynold.saloonapp.data.WishListModel;
import com.example.raynold.saloonapp.viewmodel.NewShopItemViewModel;
import com.example.raynold.saloonapp.viewmodel.ShopItemViewModel;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class ProductDetailActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    NewShopItemViewModel listItemCollectionViewModel;
    ShopItemViewModel mShopItemViewModel;

    private Toolbar mToolbar;
    private TextView mProductName;
    private TextView mProductPrice;
    private TextView mProductInfo;
    private ImageView mProductImage;
    private TextView mProductLocation;
    private ImageButton mWishlist;
    private int wish = 0;
    private String productName;



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

        mShopItemViewModel = ViewModelProviders.of(this,viewModelFactory)
                .get(ShopItemViewModel.class);

        mToolbar = (Toolbar) findViewById(R.id.product_layout);
        mProductName = (TextView) findViewById(R.id.product_name);
        mProductPrice = (TextView) findViewById(R.id.product_price);
        mProductImage = (ImageView) findViewById(R.id.product_image);
        mProductLocation = (TextView) findViewById(R.id.product_location);
        mProductInfo = (TextView) findViewById(R.id.product_info);
        mWishlist = (ImageButton) findViewById(R.id.wishlist_detail_btn);

        setSupportActionBar(mToolbar);

        productName = getIntent().getStringExtra("name");
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

        mShopItemViewModel.getListItemById(productName).observeForever(new Observer<WishListModel>() {
            @Override
            public void onChanged(@Nullable WishListModel wishListModel) {

//                if (wishListModel.getSaved() == 0) {
//                    mWishlist.setImageResource(R.drawable.heart_button);
//                } else {
//                    mWishlist.setImageResource(R.drawable.like);
//                }

                //Toasty.info(ProductDetailActivity.this,"testing observer " + wishListModel.getSaved(), Toast.LENGTH_LONG).show();
            }
        });


        mWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    //mWishlist.setImageResource(R.drawable.like);
                    WishListModel wishListModel = new WishListModel(productName,productName,productImage,productPrice,location,details,1);
                     listItemCollectionViewModel.addNewItemToDatabase(wishListModel);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
