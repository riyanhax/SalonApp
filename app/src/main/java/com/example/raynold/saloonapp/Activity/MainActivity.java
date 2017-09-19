package com.example.raynold.saloonapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raynold.saloonapp.Model.HairStyle;
import com.example.raynold.saloonapp.Adapter.HairStyleAdapter;
import com.example.raynold.saloonapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rom4ek.arcnavigationview.ArcNavigationView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, HairStyleAdapter.ListItemClickListener {

    private RecyclerView mHairRecyclerview;
    private LinearLayoutManager mLinearLayoutManager;
    private HairStyleAdapter mStyleAdapter;
    private List<HairStyle> mHairStyleList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private Toolbar mToolbar;
    private RecyclerView.ItemDecoration mItemDecoration;
    private Button mGotoLogin;
    private ArcNavigationView mNavigationView;
    private FirebaseAuth mAuth;
    private LinearLayout mLinearLayout;
    private LinearLayout mLoggedOutLayout;
    private CircleImageView mCircleImageView;
    private DatabaseReference mUserRef;
    private TextView mHeaderUsername;
    private TextView mHeaderEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Views reference
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open, R.string.close);
        mHairRecyclerview = (RecyclerView) findViewById(R.id.style_recyclerview);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mNavigationView = (ArcNavigationView) findViewById(R.id.nav_view);


        //Firebase Connections

        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mUserRef.keepSynced(true);

        //Nav header ref
        View header = mNavigationView.getHeaderView(0);
        mGotoLogin = (Button) header.findViewById(R.id.goto_login_activity);
        mLinearLayout = (LinearLayout) header.findViewById(R.id.logged_in_layout);
        mLoggedOutLayout = (LinearLayout) header.findViewById(R.id.logged_out_layout);
        mCircleImageView = (CircleImageView) header.findViewById(R.id.thumb_image);
        mHeaderEmail = (TextView) header.findViewById(R.id.header_email);
        mHeaderUsername = (TextView) header.findViewById(R.id.header_username);


                mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

        mHairRecyclerview.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(5), true));
        mLinearLayoutManager = new LinearLayoutManager(this);
        mHairRecyclerview.setLayoutManager(new GridLayoutManager(this, 2));
        mHairRecyclerview.setHasFixedSize(true);

        mHairStyleList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {

            mHairStyleList.add(new HairStyle("Brazillian Wig", "Cut hair, Faded black", "5000", R.drawable.natural_graduated_bob));
            mHairStyleList.add(new HairStyle("Brazillian Wig", "Cut hair, Faded black", "2000", R.drawable.pixiew_with_long_bangs));
        }



        mStyleAdapter = new HairStyleAdapter(mHairStyleList,this);
        mHairRecyclerview.setAdapter(mStyleAdapter);

        setSupportActionBar(mToolbar);

        //mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
        getSupportActionBar().setIcon(android.R.color.transparent);

        setNavigationViewListener();

        mGotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LogInActivity.class));
            }
        });

        getUserData();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);

    }


    @Override
    public void onStart() {
        super.onStart();


        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

// Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            FirebaseUser currentUser = mAuth.getCurrentUser();

            if (currentUser != null) {

                mLinearLayout.setVisibility(View.VISIBLE);
                mLoggedOutLayout.setVisibility(View.INVISIBLE);
            }

        } else {

            Toasty.error(this, "Check your network conection", Toast.LENGTH_LONG).show();
        }

    }

    public void getUserData() {

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            String userUid = mAuth.getCurrentUser().getUid();
            mUserRef.child(userUid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    mHeaderUsername.setText(dataSnapshot.child("name").getValue().toString());
                    mHeaderEmail.setText(dataSnapshot.child("email").getValue().toString());

                    final String defaultImage = dataSnapshot.child("thumb_image").getValue().toString();
                    if (defaultImage.equals("default")) {
                        Picasso.with(MainActivity.this).load(R.mipmap.ic_default_image).into(mCircleImageView);
                    } else {

                        Picasso.with(MainActivity.this).load(defaultImage).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.mipmap.ic_default_image).into(mCircleImageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                Picasso.with(MainActivity.this).load(defaultImage).placeholder(R.mipmap.ic_default_image).into(mCircleImageView);
                            }

                            @Override
                            public void onError() {
                                Picasso.with(MainActivity.this).load(defaultImage).placeholder(R.mipmap.ic_default_image).into(mCircleImageView);
                            }
                        });
                    }


                    if (mAuth.getCurrentUser() != null) {

                        mLinearLayout.setClickable(true);
                        mLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(MainActivity.this, AccountActivity.class));
                            }
                        });
                    } else {
                        mLinearLayout.setClickable(false);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            mLoggedOutLayout.setVisibility(View.VISIBLE);
            mLinearLayout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mActionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_job:
                startActivity(new Intent(MainActivity.this, WishList.class));
                break;

            case R.id.menu_appointment:
                    startActivity(new Intent(MainActivity.this, AppointmentActivity.class));
                break;

            case R.id.menu_call_us:
                startActivity(new Intent(MainActivity.this, CallUsActivity.class));
                break;
            case R.id.menu_services:
                startActivity(new Intent(MainActivity.this, ServicesActivity.class));
                break;
            case R.id.menu_location:
                startActivity(new Intent(MainActivity.this, LocationActivity.class));
                break;
            case R.id.menu_shop:
                startActivity(new Intent(MainActivity.this, ShopActivity.class));
                break;
        }
        //mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onListItemClick(HairStyle clickedItem) {
        Toast.makeText(this, "You clicked ", Toast.LENGTH_SHORT).show();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }


    }

}
