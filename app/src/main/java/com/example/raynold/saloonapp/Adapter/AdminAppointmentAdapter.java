package com.example.raynold.saloonapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.raynold.saloonapp.Model.AdminAppointment;
import com.example.raynold.saloonapp.Model.Appointment;
import com.example.raynold.saloonapp.R;

import java.util.List;

/**
 * Created by RAYNOLD on 9/28/2017.
 */

public class AdminAppointmentAdapter extends RecyclerView.Adapter<AdminAppointmentAdapter.AdminAppointmentViewHolder>{

    List<AdminAppointment> mAppointmentList;
    private AdminAppointmentAdapter.AppointmentClickListener mAppointmentClickListener;

    public AdminAppointmentAdapter(List<AdminAppointment> appointmentList) {
        mAppointmentList = appointmentList;
    }

    @Override
    public AdminAppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForItem = R.layout.admin_appointment_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToImmediately = false;
        View view = inflater.inflate(layoutIdForItem, parent, shouldAttachToImmediately);
        AdminAppointmentViewHolder appointmentAdapterViewHolder = new AdminAppointmentViewHolder(view);

        return appointmentAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(AdminAppointmentViewHolder holder, int position) {

        AdminAppointment appointment = mAppointmentList.get(position);
        holder.mTime.setText(appointment.getStartTime() + " - " + appointment.getEndTime());
        holder.mName.setText(appointment.getName());
        holder.mdate.setText(appointment.getDate());

    }

    @Override
    public int getItemCount() {
        return mAppointmentList.size();
    }

    public interface AppointmentClickListener {

        void onClickeListerner(AdminAppointment appointment);
    }

    public class AdminAppointmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mName;
        TextView mTime;
        TextView mdate;


        public AdminAppointmentViewHolder(View itemView) {
            super(itemView);

            mTime = (TextView) itemView.findViewById(R.id.tv_appointment_time);
            mName = (TextView) itemView.findViewById(R.id.tv_appointment_name);
            mdate = (TextView) itemView.findViewById(R.id.tv_appointment_date);


        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mAppointmentClickListener.onClickeListerner(mAppointmentList.get(clickedPosition));
        }
    }
}
