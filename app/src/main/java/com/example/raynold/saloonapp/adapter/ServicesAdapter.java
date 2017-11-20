package com.example.raynold.saloonapp.adapter;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.raynold.saloonapp.model.Services;
import com.example.raynold.saloonapp.R;
import com.example.raynold.saloonapp.viewmodel.NewShopItemViewModel;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by RAYNOLD on 8/24/2017.
 */

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServicesViewHolder> {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    List<Services> mServices;
    private ListItemClickListener mOnclickListener;
    private NewShopItemViewModel newListItemViewModel;

    public ServicesAdapter(List<Services> services, ListItemClickListener listener) {
        mServices = services;
        mOnclickListener = listener;
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
        holder.mDetails.setText(services.getDetails());

    }

    @Override
    public int getItemCount() {
        return mServices.size();
    }

    public interface ListItemClickListener {
        void onListItemClick(Services services);
    }

    public class ServicesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mTitle;
        TextView mPrice;
        ImageView mServiceImage;
        TextView mDetails;

        public ServicesViewHolder(View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.services_title);
            mPrice = (TextView) itemView.findViewById(R.id.services_price);
            mServiceImage = (ImageView) itemView.findViewById(R.id.services_pic);
            mDetails = (TextView) itemView.findViewById(R.id.services_details);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int clickedPosition = getAdapterPosition();
            mOnclickListener.onListItemClick(mServices.get(clickedPosition));
        }
    }
}
