package com.example.raynold.saloonapp.activity;

import android.arch.persistence.room.Database;
import android.content.Intent;
import android.support.v4.view.ViewPager;
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

import com.example.raynold.saloonapp.adapter.PagerViewAdapter;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AccountActivity extends AppCompatActivity {


    @BindView(R.id.profile_label)
    TextView mProfileLabel;
    @BindView(R.id.appointment_label)
    TextView mAppoinmentLabel;
    @BindView(R.id.mainPager)
    ViewPager mViewPager;

    private PagerViewAdapter mPagerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);

        mPagerViewAdapter = new PagerViewAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerViewAdapter);

        //all users appoinment recyclerview setup

        mProfileLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
                mProfileLabel.setTextColor(getResources().getColor(R.color.textTabLight));
                mAppoinmentLabel.setTextColor(getResources().getColor(R.color.textTabBright));
            }
        });

        mAppoinmentLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
                mAppoinmentLabel.setTextColor(getResources().getColor(R.color.textTabLight));
                mProfileLabel.setTextColor(getResources().getColor(R.color.textTabBright));
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                changeTabs(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void changeTabs(int position) {

        switch (position) {
            case 0:
                mProfileLabel.setTextColor(getResources().getColor(R.color.textTabLight));
                mProfileLabel.setTextSize(22);

                mAppoinmentLabel.setTextColor(getResources().getColor(R.color.colorPrimary));
                mAppoinmentLabel.setTextSize(16);
                break;

            case 1:

                mAppoinmentLabel.setTextColor(getResources().getColor(R.color.textTabLight));
                mAppoinmentLabel.setTextSize(22);

                mProfileLabel.setTextColor(getResources().getColor(R.color.colorPrimary));
                mProfileLabel.setTextSize(16);
                break;

            default:
                break;
        }
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
