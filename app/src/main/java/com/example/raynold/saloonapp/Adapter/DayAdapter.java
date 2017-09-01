package com.example.raynold.saloonapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raynold.saloonapp.Model.Appointment;
import com.example.raynold.saloonapp.Model.Day;
import com.example.raynold.saloonapp.R;

import java.util.List;

/**
 * Created by RAYNOLD on 8/31/2017.
 */

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {

    List<Day> mDayList;

    public DayAdapter(List<Day> appointmentList) {
        mDayList = appointmentList;
    }

    @Override
    public DayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForItem = R.layout.day_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToImmediately = false;
        View view = inflater.inflate(layoutIdForItem, parent, shouldAttachToImmediately);
        DayViewHolder dayAdapterViewHolder = new DayViewHolder(view);

        return dayAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(DayViewHolder holder, int position) {

        Day appointment = mDayList.get(position);
        holder.mDayTextView.setText("" + appointment.getDay());
    }

    @Override
    public int getItemCount() {
        return mDayList.size();
    }

    public class DayViewHolder extends RecyclerView.ViewHolder {

        TextView mDayTextView;

        public DayViewHolder(View itemView) {
            super(itemView);

            mDayTextView = (TextView) itemView.findViewById(R.id.tv_day);
        }
    }
}
