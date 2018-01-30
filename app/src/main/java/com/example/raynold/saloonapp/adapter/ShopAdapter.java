package com.example.raynold.saloonapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.raynold.saloonapp.R;
import com.example.raynold.saloonapp.detail.WishListDetailActivity;
import com.example.raynold.saloonapp.model.Shop;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RAYNOLD on 1/24/2018.
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder>{

    List<Shop> mShopList;
    Context mContext;

    public ShopAdapter(List<Shop> shopList, Context context) {
        mShopList = shopList;
        mContext = context;
    }

    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForItem = R.layout.shop_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToImmediately = false;
        View view = inflater.inflate(layoutIdForItem, parent, shouldAttachToImmediately);
        ShopViewHolder shopViewHolder = new ShopViewHolder(view);

        return shopViewHolder;
    }

    @Override
    public void onBindViewHolder(ShopViewHolder holder, int position) {

        final Shop shop = mShopList.get(position);
        holder.bind(shop);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String productName = shop.getName();
                String productLocation = shop.getLocation();
                String productPrice = shop.getPrice();
                String productImage = shop.getImage();
                String productDetails = shop.getDetail();

                Intent detailIntent = new Intent(mContext, WishListDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("itemId", productName);
                bundle.putString("name", productName);
                bundle.putString("location", productLocation);
                bundle.putString("price", productPrice);
                bundle.putString("image", productImage);
                bundle.putString("detail", productDetails);
                detailIntent.putExtras(bundle);
                mContext.startActivity(detailIntent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return mShopList.size();
    }

    public static class ShopViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.shop_title)
        TextView mShopTitle;
        @BindView(R.id.shop_price)
        TextView mShopPrice;
        @BindView(R.id.shop_image)
        ImageView mShop_image;
        @BindView(R.id.shop_store)
        TextView mShopStore;
        @BindView(R.id.wishlist_btn)
        ImageButton mWishListBtn;
        @BindView(R.id.shop_pic_progress)
        ProgressBar mProgressBar;

        public ShopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Shop shop){
            mShopTitle.setText(shop.getName());
            mShopStore.setText(shop.getLocation());
            mShopPrice.setText(shop.getPrice());
            Picasso.with(itemView.getContext()).load(shop.getImage()).resize(220, 200).into(mShop_image, new Callback() {
                @Override
                public void onSuccess() {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    Picasso.with(itemView.getContext()).load(shop.getImage()).resize(220, 200).into(mShop_image);
                }

                @Override
                public void onError() {
                    mProgressBar.setVisibility(View.VISIBLE);
                    Picasso.with(itemView.getContext()).load(shop.getImage()).into(mShop_image);
                }
            });
        }

    }
}
