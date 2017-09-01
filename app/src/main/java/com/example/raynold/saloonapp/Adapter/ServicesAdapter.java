package com.example.raynold.saloonapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raynold.saloonapp.Model.Services;
import com.example.raynold.saloonapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by RAYNOLD on 8/24/2017.
 */

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServicesViewHolder> {

    List<Services> mServices;

    public ServicesAdapter(List<Services> services) {
        mServices = services;
    }


    @Override
    public void onBindViewHolder(ServicesViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public ServicesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForItem = R.layout.services_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToImmediately = false;
        View view = inflater.inflate(layoutIdForItem, parent, shouldAttachToImmediately);
       ServicesViewHolder developerAdapterViewHolder = new ServicesViewHolder(view);

        return developerAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(ServicesViewHolder holder, int position) {


        Services services = mServices.get(position);
        holder.mTitle.setText(services.getTitle());
        holder.mPrice.setText(services.getPrice());
        holder.mServiceImage.setImageResource(services.getImageResource());

    }

    @Override
    public int getItemCount() {
        return mServices.size();
    }

    public class ServicesViewHolder extends RecyclerView.ViewHolder {

        TextView mTitle;
        TextView mPrice;
        CircleImageView mServiceImage;

        public ServicesViewHolder(View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.services_title);
            mPrice = (TextView) itemView.findViewById(R.id.services_price);
            mServiceImage = (CircleImageView) itemView.findViewById(R.id.services_pic);
        }

    }
}
