package com.example.raynold.saloonapp.Activity;

import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.raynold.saloonapp.Adapter.AppointmentAdapter;
import com.example.raynold.saloonapp.Adapter.DayAdapter;
import com.example.raynold.saloonapp.Model.Appointment;
import com.example.raynold.saloonapp.Model.Day;
import com.example.raynold.saloonapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AppointmentActivity extends AppCompatActivity{

    private Toolbar mAppointmentToolbar;
    private RecyclerView mRecyclerView;
    private List<Appointment> mAppointmentList;
    private AppointmentAdapter mAppointmentAdapter;
    private RecyclerView.ItemDecoration mItemDecoration;
    private TextView mDateTextView;
    private TextView mDayTextView;
    private int day;
    private RecyclerView mDayRecyclerView;
    private List<Day> mDayList = new ArrayList<>();
    private DayAdapter mDayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        mAppointmentList = new ArrayList<>();
        mAppointmentList.add(new Appointment("8:40PM", "10:40PM", "Cancel" ));
        mAppointmentList.add(new Appointment("10:40AM", "12:40AM","Book"));
        mAppointmentList.add(new Appointment("12:40PM", "2:40PM", "Book"));
        mAppointmentList.add(new Appointment("2:40PM", "4:40PM" , "Cancel"));
        mAppointmentList.add(new Appointment("6:40PM", "8:40PM" ,"Book"));
        mAppointmentList.add(new Appointment("8:40PM", "10:40PM", "Cancel"));

        mAppointmentAdapter = new AppointmentAdapter(mAppointmentList);



        mAppointmentToolbar = (Toolbar) findViewById(R.id.appoint_toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.appoinment_recycler);
        mDayRecyclerView = (RecyclerView) findViewById(R.id.day_recyclerview);

        mDateTextView = (TextView) findViewById(R.id.date_tv);
        //mDayTextView = (TextView) findViewById(R.id.day_tv);
        mItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(mItemDecoration);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setAdapter(mAppointmentAdapter);

        mDayRecyclerView.setHasFixedSize(true);
        mDayRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));



        setSupportActionBar(mAppointmentToolbar);
        getSupportActionBar().setTitle("Appointment");

        setSupportActionBar(mAppointmentToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //mAppointmentToolbar.setNavigationIcon(R.mipmap.ic_launcher);

        Date date = new Date();
        //SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z", Locale.ENGLISH);

            //Date newDate = dateFormat.parse(date.toString());
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM ,yyyy", Locale.ENGLISH);
            String year = dateFormat.format(date);
            mDateTextView.setText(year);

            SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.ENGLISH);
            String dayString = dayFormat.format(date);

            day = Integer.parseInt(dayString);
            int addDay = day + 1;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.DAY_OF_YEAR, +1);
        Date newDate = calendar.getTime();

        String day1 = dayFormat.format(newDate);
        calendar.add(Calendar.DAY_OF_YEAR, +2);

        Date newDate1 = calendar.getTime();
        String date1 = String.valueOf(newDate1);

            //Log.i("AppointmentActivity", " "+ year + " " + Nextdate);
            mDayList.add(new Day(Integer.parseInt(day1)));
            //mDayList.add(new Day(Integer.parseInt(date1)));

        mDayAdapter = new DayAdapter(mDayList);
        mDayRecyclerView.setAdapter(mDayAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
