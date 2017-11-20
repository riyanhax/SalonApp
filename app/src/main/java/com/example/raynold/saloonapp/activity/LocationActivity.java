package com.example.raynold.saloonapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.raynold.saloonapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.raynold.saloonapp.R.id.map;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    static final LatLng GWARIMPA = new LatLng(9.1079, 7.4102);
    private Toolbar mLocationToolbar;
    //static final LatLng KIEL = new LatLng(53.551, 9.993);
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        mLocationToolbar = (Toolbar) findViewById(R.id.location_toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        setSupportActionBar(mLocationToolbar);
        getSupportActionBar().setTitle("Location");

        setSupportActionBar(mLocationToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Gwarinpa, Abuja,55 and move the camera.
        LatLng gwarinpa = new LatLng(9.1077, 7.4102);
        mMap.addMarker(new MarkerOptions().position(gwarinpa).title("Lumo Naturals"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(gwarinpa));

//        //Move the camera instantly to hamburg with a zoom of 15.
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(GWARIMPA, 20));

        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(21), 2000, null);
        mMap.getCameraPosition();

    }
}
