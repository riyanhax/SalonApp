package com.example.raynold.saloonapp.Activity;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raynold.saloonapp.Adapter.HairStyleAdapter;
import com.example.raynold.saloonapp.Model.HairStyle;
import com.example.raynold.saloonapp.Model.Lumo;
import com.example.raynold.saloonapp.R;
import com.example.raynold.saloonapp.Model.Shop;
import com.example.raynold.saloonapp.data.WishListModel;
import com.example.raynold.saloonapp.viewmodel.NewShopItemViewModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import es.dmoral.toasty.Toasty;

public class ShopActivity extends AppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    NewShopItemViewModel listItemCollectionViewModel;
    private Toolbar mShopToolbar;
    private List<Shop> mShopList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.ItemDecoration mItemDecoration;
    private DatabaseReference mShopRef;
    private FloatingActionButton mAddProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        ((Lumo) this.getApplication())
                .getApplicationComponent()
                .inject(this);

        //Set up and subscribe (observe) to the ViewModel
        listItemCollectionViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(NewShopItemViewModel.class);


        mShopToolbar = (Toolbar) findViewById(R.id.shop_toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.shop_recycler);
        mItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        mShopRef = FirebaseDatabase.getInstance().getReference().child("Shop");
        mAddProduct = (FloatingActionButton) findViewById(R.id.fb_add);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(mItemDecoration);

        Picasso picasso = Picasso.with(this);
        picasso.setIndicatorsEnabled(false);

        setSupportActionBar(mShopToolbar);
        getSupportActionBar().setTitle("Shop");

        setSupportActionBar(mShopToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShopActivity.this, AddProductActivity.class));
                //Toasty.info(ShopActivity.this, "Clicked on the Float action button", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Shop, ShopViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Shop, ShopViewHolder>(Shop.class,R.layout.shop_list_item,ShopViewHolder.class,mShopRef) {
            @Override
            protected void populateViewHolder(final ShopViewHolder viewHolder, final Shop model, int position) {

                viewHolder.setPrice(model.getPrice());
                viewHolder.setTitle(model.getName());
                viewHolder.setStore(model.getLocation());
                viewHolder.setImage(model.getImage());

                viewHolder.mWishListBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewHolder.mWishListBtn.setImageResource(R.drawable.like);
                        WishListModel wishListModel =  new WishListModel(model.getName(),model.getName(),model.getImage(),model.getPrice(),model.getLocation(), model.getDetails());
                        listItemCollectionViewModel.addNewItemToDatabase(wishListModel);
                        Toasty.info(ShopActivity.this, "saved to database", Toast.LENGTH_LONG).show();
                    }
                });


                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String productName = model.getName();
                        String productLocation = model.getLocation();
                        String productPrice = model.getPrice();
                        String productImage = model.getImage();
                        String productDetails = model.getDetails();

                        Intent detailIntent = new Intent(ShopActivity.this, ProductDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("name", productName);
                        bundle.putString("location", productLocation);
                        bundle.putString("price", productPrice);
                        bundle.putString("image", productImage);
                        bundle.putString("detail", productDetails);
                        detailIntent.putExtras(bundle);
                        startActivity(detailIntent);

                        startActivity(detailIntent);
                    }
                });

            }
        };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public static class ShopViewHolder extends RecyclerView.ViewHolder {

        TextView mShopTitle;
        TextView mShopPrice;
        ImageView mShop_image;
        TextView mShopStore;
        ImageButton mWishListBtn;

        public ShopViewHolder(View itemView) {
            super(itemView);

            mShop_image = (ImageView) itemView.findViewById(R.id.shop_image);
            mShopPrice = (TextView) itemView.findViewById(R.id.shop_price);
            mShopTitle = (TextView) itemView.findViewById(R.id.shop_title);
            mShopStore = (TextView) itemView.findViewById(R.id.shop_store);
            mWishListBtn = (ImageButton) itemView.findViewById(R.id.wishlist_btn);


        }

        public void setPrice(String price) {
            mShopPrice.setText("N "+ price);

        }
        public void setTitle(String title) {
            mShopTitle.setText(title);

        }
        public void setStore(String store) {
            mShopStore.setText(store);

        }
        public void setImage(final String image) {
            Picasso.with(itemView.getContext()).load(image).placeholder(R.drawable.no_image_placeholder).into(mShop_image, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                    Picasso.with(itemView.getContext()).load(R.drawable.no_image_placeholder).into(mShop_image);
                }
            });
        }
    }
}
