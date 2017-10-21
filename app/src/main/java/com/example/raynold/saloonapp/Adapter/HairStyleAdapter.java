//package com.example.raynold.saloonapp.Adapter;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.raynold.saloonapp.Model.HairStyle;
//import com.example.raynold.saloonapp.R;
//
//import java.util.List;
//
///**
// * Created by RAYNOLD on 8/22/2017.
// */
//
//public class HairStyleAdapter extends RecyclerView.Adapter<HairStyleAdapter.HairStyleViewHolder> {
//
//    private ListItemClickListener mOnclickListener;
//
//    private List<HairStyle> mHairStyles;
//
//    public HairStyleAdapter(List<HairStyle> hairStyles, ListItemClickListener itemClickListener) {
//        this.mHairStyles = hairStyles;
//        mOnclickListener = itemClickListener;
//    }
//
//    public interface ListItemClickListener {
//        void onListItemClick(HairStyle clickedItem);
//    }
//
//
//    @Override
//    public void onBindViewHolder(HairStyleViewHolder holder, int position, List<Object> payloads) {
//        super.onBindViewHolder(holder, position, payloads);
//    }
//
//    @Override
//    public HairStyleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        Context context = parent.getContext();
//        int layoutIdForItem = R.layout.hair_list_item;
//        LayoutInflater inflater = LayoutInflater.from(context);
//        boolean shouldAttachToImmediately = false;
//        View view = inflater.inflate(layoutIdForItem, parent, shouldAttachToImmediately);
//        HairStyleViewHolder developerAdapterViewHolder = new HairStyleViewHolder(view);
//
//        return developerAdapterViewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(HairStyleViewHolder holder, int position) {
//
//        HairStyle hairStyle = mHairStyles.get(position);
//        //holder.mTitle.setText(hairStyle.getTitle());
//        //holder.mDescription.setText(hairStyle.getDescription());
//        //holder.mPrice.setText(hairStyle.getPrice());
//        holder.mImageView.setImageResource(hairStyle.getPicUrl());
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mHairStyles.size();
//    }
//
//
//
//}
