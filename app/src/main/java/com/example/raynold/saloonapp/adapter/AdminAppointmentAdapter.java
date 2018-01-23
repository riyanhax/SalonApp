//package com.example.raynold.saloonapp.adapter;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.example.raynold.saloonapp.model.AdminAppointment;
//import com.example.raynold.saloonapp.R;
//
//import java.util.List;
//
///**
// * Created by RAYNOLD on 9/28/2017.
// */
//
//public class AdminAppointmentAdapter extends RecyclerView.Adapter<AdminAppointmentAdapter.AdminAppointmentViewHolder>{
//
//    List<AdminAppointment> mAppointmentList;
//    private AdminAppointmentAdapter.AppointmentClickListener mAppointmentClickListener;
//
//    public AdminAppointmentAdapter(List<AdminAppointment> appointmentList) {
//        mAppointmentList = appointmentList;
//    }
//
//    @Override
//    public AdminAppointmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Context context = parent.getContext();
//        int layoutIdForItem = R.layout.admin_appointment_list;
//        LayoutInflater inflater = LayoutInflater.from(context);
//        boolean shouldAttachToImmediately = false;
//        View view = inflater.inflate(layoutIdForItem, parent, shouldAttachToImmediately);
//        AdminAppointmentViewHolder appointmentAdapterViewHolder = new AdminAppointmentViewHolder(view);
//
//        return appointmentAdapterViewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(AdminAppointmentViewHolder holder, int position) {
//
//        AdminAppointment appointment = mAppointmentList.get(position);
//        try {
//            holder.mTime.setText(appointment.getStartTime() + " - " + appointment.getEndTime());
//            holder.mName.setText(appointment.getName());
//            holder.mdate.setText(appointment.getDate());
//            holder.mPhoneNNumber.setText(appointment.getPhoneNumber());
//        }catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mAppointmentList.size();
//    }
//
//    public interface AppointmentClickListener {
//
//        void onClickeListerner(AdminAppointment appointment);
//    }
//
//
//}
