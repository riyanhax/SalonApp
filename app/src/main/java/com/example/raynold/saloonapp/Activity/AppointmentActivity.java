package com.example.raynold.saloonapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raynold.saloonapp.Adapter.AppointmentAdapter;
import com.example.raynold.saloonapp.Adapter.DayAdapter;
import com.example.raynold.saloonapp.Model.Appointment;
import com.example.raynold.saloonapp.Model.Day;
import com.example.raynold.saloonapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import es.dmoral.toasty.Toasty;

import static android.R.attr.background;
import static android.R.attr.duration;
import static android.R.attr.typeface;

public class AppointmentActivity extends AppCompatActivity implements AppointmentAdapter.AppointmentClickListener {

    private Toolbar mAppointmentToolbar;
    private RecyclerView mRecyclerView;
    private List<Appointment> mAppointmentList;
    private AppointmentAdapter mAppointmentAdapter;
    private RecyclerView.ItemDecoration mItemDecoration;
    private String currentDate;
    private DatabaseReference mAppointmentRef;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserRef;
    private ProgressDialog mProgressDialog;
    private String Username;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        mAppointmentList = new ArrayList<>();
        mAppointmentList.add(new Appointment("8:00AM", "9:00AM","Book"));
        mAppointmentList.add(new Appointment("9:00AM", "10:00AM", "Book"));
        mAppointmentList.add(new Appointment("10:00AM", "11:00AM" , "Book"));
        mAppointmentList.add(new Appointment("11:00AM", "12:00PM" ,"Book"));
        mAppointmentList.add(new Appointment("12:00PM", "1:00PM", "Book"));
        mAppointmentList.add(new Appointment("1:00PM", "2:00PM" , "Book"));
        mAppointmentList.add(new Appointment("2:00PM", "3:00PM" ,"Book"));
        mAppointmentList.add(new Appointment("3:00PM", "4:00PM", "Book"));
        mAppointmentList.add(new Appointment("4:00PM", "5:00PM" , "Book"));
        mAppointmentList.add(new Appointment("5:00PM", "6:00PM" ,"Book"));
        mAppointmentList.add(new Appointment("6:00PM", "7:00PM", "Book"));
        mAppointmentList.add(new Appointment("7:00PM", "8:00PM", "Book"));

        mAppointmentAdapter = new AppointmentAdapter(mAppointmentList, this);

        mAppointmentToolbar = (Toolbar) findViewById(R.id.appoint_toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.appoinment_recycler);
        mProgressDialog = new ProgressDialog(this);
        mAppointmentRef = FirebaseDatabase.getInstance().getReference().child("Appointments");
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();

        mItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(mItemDecoration);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAppointmentAdapter);

        setSupportActionBar(mAppointmentToolbar);
        getSupportActionBar().setTitle("Appointment");

        setSupportActionBar(mAppointmentToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //mAppointmentToolbar.setNavigationIcon(R.mipmap.ic_launcher);

        mProgressDialog.setTitle("Appointment Added");
        mProgressDialog.setMessage("please wait while we add your appiontment");
        mProgressDialog.setCanceledOnTouchOutside(false);


        /** end after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

/** start before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DATE, 0);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd MMM yyyy", Locale.ENGLISH);
                String date1 = dateFormat.format(date);

                currentDate = date1;

            }

            @Override
            public void onCalendarScroll(HorizontalCalendarView calendarView,
                                         int dx, int dy) {

            }

            @Override
            public boolean onDateLongClicked(Date date, int position) {
                return true;
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

    @Override
    public void onClickeListerner(final Appointment appointment) {
        mProgressDialog.show();
        final String userUid = mAuth.getCurrentUser().getUid();
        mUserRef.child(userUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Username = dataSnapshot.child("name").getValue().toString();
                userEmail = dataSnapshot.child("email").getValue().toString();

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("startTime", appointment.getStartTime());
                hashMap.put("endTime", appointment.getEndTime());
                hashMap.put("date", currentDate);
                hashMap.put("name", Username);
                hashMap.put("email", userEmail);

                mAppointmentRef.child(userUid).push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            startActivity(new Intent(AppointmentActivity.this, AccountActivity.class));
                            finish();
                            mProgressDialog.dismiss();

                            Toasty.info(AppointmentActivity.this, "Appointment added", Toast.LENGTH_LONG).show();

                        }

                        if (!task.isSuccessful()) {
                            mProgressDialog.dismiss();
                            Toasty.error(AppointmentActivity.this, "sorry something went wrong, try again").show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}