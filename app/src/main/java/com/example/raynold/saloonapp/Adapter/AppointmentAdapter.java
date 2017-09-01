package com.example.raynold.saloonapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.raynold.saloonapp.Model.Appointment;
import com.example.raynold.saloonapp.R;

import java.util.List;

/**
 * Created by RAYNOLD on 8/29/2017.
 */

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {

    List<Appointment> mAppointmentList;

    public AppointmentAdapter(List<Appointment> appointmentList) {
        mAppointmentList = appointmentList;
    }

    @Override
    public AppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForItem = R.layout.appointment_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToImmediately = false;
        View view = inflater.inflate(layoutIdForItem, parent, shouldAttachToImmediately);
        AppointmentViewHolder appointmentAdapterViewHolder = new AppointmentViewHolder(view);

        return appointmentAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(AppointmentViewHolder holder, int position) {

        Appointment appointment = mAppointmentList.get(position);
        holder.mStartTime.setText(appointment.getStartTime());
        holder.mEndTime.setText(appointment.getEndTime());
        holder.mBook.setText(appointment.getBook());
    }

    @Override
    public int getItemCount() {
        return mAppointmentList.size();
    }


    public class AppointmentViewHolder extends RecyclerView.ViewHolder {

        TextView mStartTime;
        TextView mEndTime;
        Button mBook;

        public AppointmentViewHolder(View itemView) {
            super(itemView);

            mEndTime = (TextView) itemView.findViewById(R.id.end_time);
            mStartTime = (TextView) itemView.findViewById(R.id.start_time);
            mBook = (Button) itemView.findViewById(R.id.book_btn);
        }
    }
}
