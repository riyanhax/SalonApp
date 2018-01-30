package com.example.raynold.saloonapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.raynold.saloonapp.activity.MyAppointmentFragment;
import com.example.raynold.saloonapp.activity.ProfileFragment;

/**
 * Created by RAYNOLD on 1/27/2018.
 */

public class PagerViewAdapter extends FragmentPagerAdapter{


    public PagerViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;
            case 1:
                MyAppointmentFragment myAppointmentFragment = new MyAppointmentFragment();
                return myAppointmentFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
