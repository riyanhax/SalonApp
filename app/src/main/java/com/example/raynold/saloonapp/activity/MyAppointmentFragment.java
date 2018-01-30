package com.example.raynold.saloonapp.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.raynold.saloonapp.R;
import com.example.raynold.saloonapp.model.AccountAppointment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAppointmentFragment extends Fragment {

    private final String MYAPPOINMENT = "My_appoinmtment";


    @BindView(R.id.account_appoint_recycler)
    RecyclerView mAppoinmtRecycler;
    FirebaseRecyclerAdapter<AccountAppointment, AccountViewHolder> firebaseRecyclerAdapter;
    DatabaseReference mAccountRef;
    FirebaseAuth mAuth;
    String appointmentKey;
    private DividerItemDecoration mItemDecoration;


    public MyAppointmentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_appointment, container, false);
        ButterKnife.bind(this, view);

        mAuth = FirebaseAuth.getInstance();

        mAppoinmtRecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mAppoinmtRecycler.setLayoutManager(linearLayoutManager);

        mAccountRef = FirebaseDatabase.getInstance().getReference().child(MYAPPOINMENT)
                .child(mAuth.getCurrentUser().getUid());

        swipeEffect();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<AccountAppointment, AccountViewHolder>(
                        AccountAppointment.class,
                        R.layout.account_list_item,
                        AccountViewHolder.class,
                        mAccountRef

                ) {
                    @Override
                    protected void populateViewHolder(AccountViewHolder viewHolder, AccountAppointment model, int position) {

                        appointmentKey = getRef(position).getKey();


                        viewHolder.setText(model.getStartTime(), model.getEndTime());
                        viewHolder.setDate(model.getDate());

                    }
                };

        mAppoinmtRecycler.setAdapter(firebaseRecyclerAdapter);
    }

    public void swipeEffect() {

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();

                firebaseRecyclerAdapter.notifyItemRemoved(position);
                mAccountRef.child(appointmentKey).removeValue();
                //mAdminRef.child(appointmentKey).removeValue();

            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mAppoinmtRecycler);
    }

    public static class AccountViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.appointment_time)
        TextView mAppointmentTime;
        @BindView(R.id.appointment_date)
        TextView mAppointmentDate;

        View mView;

        public AccountViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            ButterKnife.bind(this, mView);
        }

        public void setText(String start, String end) {

            mAppointmentTime.setText(start + " - " + end);
        }

        public void setDate(String date) {
            mAppointmentDate = (TextView) mView.findViewById(R.id.appointment_date);
            mAppointmentDate.setText(date);
        }

    }
}
