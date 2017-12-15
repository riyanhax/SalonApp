package com.example.raynold.saloonapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.raynold.saloonapp.R;
import com.example.raynold.saloonapp.activity.MainActivity;
import com.example.raynold.saloonapp.activity.RegimenDetailActivity;
import com.example.raynold.saloonapp.model.Regimen;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class RegimenAdapter extends RecyclerView.Adapter<RegimenAdapter.RegimenViewHolder> {

    private List<Regimen> mRegimen;

    private ListItemClickListener listItemClickListener;

    public RegimenAdapter(List<Regimen> regimen, ListItemClickListener listItemClickListener) {
        this.listItemClickListener = listItemClickListener;
        mRegimen = regimen;
    }

    @Override
    public RegimenViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForItem = R.layout.regimen_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToImmediately = false;
        View view = inflater.inflate(layoutIdForItem, parent, shouldAttachToImmediately);
        RegimenViewHolder appointmentAdapterViewHolder = new RegimenViewHolder(view);

        return appointmentAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(final RegimenViewHolder holder, int position) {

        final Regimen regimen = mRegimen.get(position);

        holder.mImageView.setImageResource(regimen.getImageRes());
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("name", regimen.getHairName());
                bundle.putString("detail", regimen.getDetail());
                bundle.putString("cleanse", regimen.getCleanse());
                bundle.putString("condition", regimen.getCondition());
                bundle.putString("moisture", regimen.getMoisturize());
                bundle.putString("style", regimen.getStyle());
                bundle.putInt("pic", regimen.getImageRes());
                Intent regimenDetailIntent = new Intent(holder.mImageView.getContext(), RegimenDetailActivity.class);
                regimenDetailIntent.putExtras(bundle);
                holder.mImageView.getContext().startActivity(regimenDetailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRegimen.size();
    }

    public interface ListItemClickListener {
        void onListItemClick(Regimen regimen);
    }

    public class RegimenViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{

        ImageView mImageView;

        public RegimenViewHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.regimen_image);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            listItemClickListener.onListItemClick(mRegimen.get(clickedPosition));
        }
    }

}
