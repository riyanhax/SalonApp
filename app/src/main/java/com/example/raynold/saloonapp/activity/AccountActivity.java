package com.example.raynold.saloonapp.activity;

import android.arch.persistence.room.Database;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.raynold.saloonapp.adapter.AdminAppointmentAdapter;
import com.example.raynold.saloonapp.model.AccountAppointment;
import com.example.raynold.saloonapp.model.AdminAppointment;
import com.example.raynold.saloonapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountActivity extends AppCompatActivity {

    private final String ADMIN_REF = "Admin_Appointment";
    FirebaseRecyclerAdapter<AccountAppointment, AccountViewHolder> firebaseRecyclerAdapter;
    String userUid;
    private Toolbar mAccountToolbar;
    private Button mLogOut;
    private DatabaseReference mUserRef;
    private FirebaseAuth mAuth;
    private CircleImageView mAccountImage;
    private TextView mAccountName;
    private DatabaseReference mAccountRef;
    private RecyclerView mAppoinmtRecycler;
    private DividerItemDecoration mItemDecoration;
    private String appointmentKey;
    private DatabaseReference mAdminRef;
    private AdminAppointmentAdapter mAdminAppointmentAdapter;
    private RecyclerView mAdminRecycler;
    private List<AdminAppointment> mAdminAppointments = new ArrayList<>();
    private DatabaseReference mNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mAccountToolbar = (Toolbar) findViewById(R.id.account_appbar);
        mLogOut = (Button) findViewById(R.id.log_out_btn);
        mAccountImage = (CircleImageView) findViewById(R.id.account_image);
        mAccountName = (TextView) findViewById(R.id.account_username);

        //all users appoinment recyclerview setup
        mAppoinmtRecycler = (RecyclerView) findViewById(R.id.account_appoint_recycler);
        mAppoinmtRecycler.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mAppoinmtRecycler.setLayoutManager(linearLayoutManager);
        mItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mAppoinmtRecycler.addItemDecoration(mItemDecoration);

        //admin appointment recyclerview setup
        mAdminRecycler = (RecyclerView) findViewById(R.id.admin_appoint_recycler);
        mAdminRecycler.setHasFixedSize(true);
        LinearLayoutManager adminLayout = new LinearLayoutManager(this);
        adminLayout.setReverseLayout(true);
        adminLayout.setStackFromEnd(true);
        mAdminRecycler.setLayoutManager(adminLayout);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mAdminRecycler.addItemDecoration(dividerItemDecoration);


        mAuth = FirebaseAuth.getInstance();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mAccountRef = FirebaseDatabase.getInstance().getReference().child("Appointments")
                .child(mAuth.getCurrentUser().getUid());
        mAdminRef = FirebaseDatabase.getInstance().getReference().child(ADMIN_REF);
        mNotification = FirebaseDatabase.getInstance().getReference().child("Notifications");

        mUserRef.keepSynced(true);

        setSupportActionBar(mAccountToolbar);
        getSupportActionBar().setTitle("My Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AccountActivity.this,MainActivity.class ));
                finish();
            }
        });

        getUserDetails();

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
                mAdminRef.child(appointmentKey).removeValue();
                mNotification.child(userUid).child(appointmentKey).removeValue();


            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mAppoinmtRecycler);

        mAdminRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String appointmentKey = snapshot.getKey();

                    mAdminRef.child(appointmentKey).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            mAdminAppointments.add(dataSnapshot.getValue(AdminAppointment.class));
                            mAdminAppointmentAdapter = new AdminAppointmentAdapter(mAdminAppointments);
                            mAdminRecycler.setAdapter(mAdminAppointmentAdapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        getUserDetails();
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

    private void getUserDetails() {
        userUid = mAuth.getCurrentUser().getUid();
        mUserRef.child(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild("admin")) {

                    mAdminRecycler.setVisibility(View.VISIBLE);
                    mAppoinmtRecycler.setVisibility(View.INVISIBLE);
                } else {
                    mAdminRecycler.setVisibility(View.INVISIBLE);
                    mAppoinmtRecycler.setVisibility(View.VISIBLE);

                }

                final String image = dataSnapshot.child("thumb_image").getValue().toString();
                String username = dataSnapshot.child("name").getValue().toString();

                mAccountName.setText(username);
                Picasso.with(AccountActivity.this).load(image).placeholder(R.mipmap.ic_default_image)
                        .networkPolicy(NetworkPolicy.OFFLINE).into(mAccountImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        Picasso.with(AccountActivity.this).load(image).placeholder(R.mipmap.ic_default_image)
                                .networkPolicy(NetworkPolicy.OFFLINE).into(mAccountImage);
                    }

                    @Override
                    public void onError() {
                        Picasso.with(AccountActivity.this).load(image).placeholder(R.mipmap.ic_default_image)
                                .into(mAccountImage);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class AccountViewHolder extends RecyclerView.ViewHolder {

        TextView mAppointmentTime;
        TextView mAppointmentDate;
        View mView;

        public AccountViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setText(String start, String end) {

            mAppointmentTime = (TextView) mView.findViewById(R.id.appointment_time);
            mAppointmentTime.setText(start + " - " + end);
        }

        public void setDate(String date) {
            mAppointmentDate = (TextView) mView.findViewById(R.id.appointment_date);
            mAppointmentDate.setText(date);
        }


    }
}
