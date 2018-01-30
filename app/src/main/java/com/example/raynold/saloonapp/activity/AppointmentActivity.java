package com.example.raynold.saloonapp.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.raynold.saloonapp.R;
import com.example.raynold.saloonapp.Remote.APIService;
import com.example.raynold.saloonapp.adapter.AppointmentAdapter;
import com.example.raynold.saloonapp.model.Appointment;
import com.example.raynold.saloonapp.model.MyResponse;
import com.example.raynold.saloonapp.model.Notification;
import com.example.raynold.saloonapp.model.Sender;
import com.example.raynold.saloonapp.model.Token;
import com.example.raynold.saloonapp.util.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentActivity extends AppCompatActivity implements AppointmentAdapter.AppointmentClickListener {

    private final String ADMINREF = "Admin_Appointment";
    private final String MYAPPOINMENT = "My_appoinmtment";

    String newKey;
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
    private DatabaseReference mAdminRef;
    private DatabaseReference mNotification;
    private String adminUid = "cgr2P4gMnjaX5OtcUGagEiTEI482";
    private long numberOfChild;
    private DatabaseReference mMyAppoinmentRef;
    private FirebaseFirestore mFirebaseFirestore;
    private FirebaseFirestore mUserAppointment;

    private APIService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        mService = Utils.getFCMService();

        mAppointmentList = new ArrayList<>();
        mAppointmentList.add(new Appointment("8:00AM", "11:00AM","Book"));
        mAppointmentList.add(new Appointment("11:00AM", "2:00PM" ,"Book"));
        mAppointmentList.add(new Appointment("2:00PM", "5:00PM" ,"Book"));
        mAppointmentList.add(new Appointment("5:00PM", "8:00PM" ,"Book"));

        mAppointmentAdapter = new AppointmentAdapter(mAppointmentList, this);

        mAppointmentToolbar = (Toolbar) findViewById(R.id.appoint_toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.appoinment_recycler);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
        mUserAppointment = FirebaseFirestore.getInstance();

        mProgressDialog = new ProgressDialog(this);
        mAppointmentRef = FirebaseDatabase.getInstance().getReference().child("Appointments");
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mNotification = FirebaseDatabase.getInstance().getReference().child("Notifications");
        mMyAppoinmentRef = FirebaseDatabase.getInstance().getReference().child(MYAPPOINMENT);
        mAdminRef = FirebaseDatabase.getInstance().getReference().child(ADMINREF);
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null) {
            Intent loginIntent = new Intent(AppointmentActivity.this, LogInActivity.class);
            startActivity(loginIntent);
            finish();
        }

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

                currentDate = dateFormat.format(date);

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
                String phoneNum = dataSnapshot.child("phone_number").getValue().toString();

                final HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("startTime", appointment.getStartTime());
                hashMap.put("endTime", appointment.getEndTime());
                hashMap.put("date", currentDate);
                hashMap.put("name", Username);
                hashMap.put("email", userEmail);
                hashMap.put("user_uid", userUid);
                hashMap.put("phoneNumber", phoneNum);

                final HashMap<String, String> userHashMap = new HashMap<>();
                hashMap.put("startTime", appointment.getStartTime());
                hashMap.put("endTime", appointment.getEndTime());
                hashMap.put("date", currentDate);

                final HashMap<String, String> notificationMap = new HashMap<>();
                notificationMap.put("name", Username);
                notificationMap.put("email", userEmail);
                notificationMap.put("user_uid", userUid);
                notificationMap.put("admin_uid", adminUid);

                String currentdateNoSpace = currentDate.replaceAll("\\s","");
                newKey = currentdateNoSpace+appointment.getStartTime()+appointment.getEndTime();

                mAppointmentRef.child(newKey).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {
                        numberOfChild = dataSnapshot.getChildrenCount();

                        if (numberOfChild >= 4) {
                            mProgressDialog.cancel();
                            AlertDialog.Builder builder;

                            builder = new AlertDialog.Builder(AppointmentActivity.this);

                            builder.setTitle("Slot fully booked")
                                    .setMessage("Select a different time or date")
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // do nothing
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        } else {

                            mAppointmentRef.child(newKey).child(userUid).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    mUserAppointment.collection("my_appoinment").add(userHashMap).addOnSuccessListener(AppointmentActivity.this, new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {

                                            mFirebaseFirestore.collection(ADMINREF).add(hashMap).addOnSuccessListener(AppointmentActivity.this,new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {

                                                    mProgressDialog.cancel();
                                                    Toasty.info(AppointmentActivity.this, "Appointment added ", Toast.LENGTH_LONG).show();
                                                    sendNotification();
                                                }
                                            });

                                        }
                                    });
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
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void sendNotification() {
        Token token = new Token(FirebaseInstanceId.getInstance().getToken(), true);
        FirebaseFirestore tokenRef = FirebaseFirestore.getInstance();


        tokenRef.collection("Token").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {



                    Token token1 = documentChange.getDocument().toObject(Token.class);

                    if (token1.isServerToken()) {
                        Notification notification = new Notification("Lumo Naturals", "You have a new Appointment");
                        Sender sender = new Sender(token1.getToken(), notification);
                        mService.sendNotification(sender)
                                .enqueue(new Callback<MyResponse>() {
                                    @Override
                                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                        if (response.body().success == 1) {

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<MyResponse> call, Throwable t) {

                                    }
                                });


                        Log.i("Appointment", " "+token1.isServerToken());
                    }


                }
            }
        });
    }
}