package com.example.raynold.saloonapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.raynold.saloonapp.Model.Shop;
import com.example.raynold.saloonapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by RAYNOLD on 8/29/2017.
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewHolder>{

    List<Shop> mShopList;

    public ShopAdapter(List<Shop> shopList) {
        mShopList = shopList;
    }

    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForItem = R.layout.shop_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToImmediately = false;
        View view = inflater.inflate(layoutIdForItem, parent, shouldAttachToImmediately);
        ShopViewHolder shopAdapterViewHolder = new ShopViewHolder(view);

        return shopAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(ShopViewHolder holder, int position) {

        Shop shop = mShopList.get(position);
        holder.mShopTitle.setText(shop.getPName());
        holder.mShopPrice.setText("N"+ shop.getPrice());
        holder.mShop_image.setImageResource(shop.getPImage());
        holder.mShopStore.setText(shop.getMLocation());
    }

    @Override
    public int getItemCount() {
        return mShopList.size();
    }

    public class ShopViewHolder extends RecyclerView.ViewHolder {

        TextView mShopTitle;
        TextView mShopPrice;
        ImageView mShop_image;
        TextView mShopStore;

        public ShopViewHolder(View itemView) {
            super(itemView);

            mShop_image = (ImageView) itemView.findViewById(R.id.shop_image);
            mShopPrice = (TextView) itemView.findViewById(R.id.shop_price);
            mShopTitle = (TextView) itemView.findViewById(R.id.shop_title);
            mShopStore = (TextView) itemView.findViewById(R.id.shop_store);
        }
    }
}
