package com.example.raynold.saloonapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raynold.saloonapp.Model.HairStyle;
import com.example.raynold.saloonapp.Model.Shop;
import com.example.raynold.saloonapp.R;
import com.example.raynold.saloonapp.data.WishListModel;
import com.example.raynold.saloonapp.detail.WishListDetailActivity;
import com.example.raynold.saloonapp.saved.WishList;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
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

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView mHairRecyclerview;
    private LinearLayoutManager mLinearLayoutManager;
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
    private FloatingActionButton mAddNewHairStyle;

    private DatabaseReference mHairStyleRef;
    private DataSnapshot mDataSnapshot;
    private String userId;
    private ProgressBar mProgressBar;

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
        mAddNewHairStyle = (FloatingActionButton) findViewById(R.id.fb_add_new_style);
        mProgressBar = (ProgressBar) findViewById(R.id.hair_progress);

        Picasso picasso = Picasso.with(getApplicationContext());
        picasso.setIndicatorsEnabled(false);


        //Firebase Connections

        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mUserRef.keepSynced(true);
        mHairStyleRef = FirebaseDatabase.getInstance().getReference().child("HairStyles");
        mHairStyleRef.keepSynced(true);


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
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("ILumo");

        mAddNewHairStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddStyleActivity.class));

            }
        });

        try{
            mUserRef.child(userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mDataSnapshot = dataSnapshot;

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        try {
            if (mDataSnapshot.hasChild("admin")) {
                mAddNewHairStyle.setVisibility(View.VISIBLE);
            } else {
                mAddNewHairStyle.setVisibility(View.INVISIBLE);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);

    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<HairStyle, HairStyleViewHolder>(HairStyle.class,R.layout.hair_list_item,HairStyleViewHolder.class,mHairStyleRef) {
                    @Override
                    protected void populateViewHolder(final HairStyleViewHolder viewHolder, final HairStyle model, int position) {

                        viewHolder.setImage(model.getPicUrl());

                        try {
                            if (mDataSnapshot.hasChild("admin")) {
                                mAddNewHairStyle.setVisibility(View.VISIBLE);
                            } else {
                                mAddNewHairStyle.setVisibility(View.INVISIBLE);
                            }

                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }

                        mProgressBar.setVisibility(View.INVISIBLE);
                        mHairRecyclerview.setVisibility(View.VISIBLE);


                        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Toast.makeText(getApplicationContext(), "You clicked ", Toast.LENGTH_SHORT).show();
                                Bundle bundle = new Bundle();
                                Intent styleIntent = new Intent(MainActivity.this, StyleDetailActivity.class);
                                bundle.putString("image", model.getPicUrl());
                                bundle.putString("title", model.getTitle());
                                bundle.putString("description", model.getDescription());
                                styleIntent.putExtras(bundle);
                                startActivity(styleIntent);

                            }
                        });

                    }
                };
        mHairRecyclerview.setAdapter(firebaseRecyclerAdapter);



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

    public static class HairStyleViewHolder extends RecyclerView.ViewHolder{

        public TextView mTitle;
        public TextView mDescription;
        public TextView mPrice;
        public ImageView mImageView;

        public HairStyleViewHolder(View itemView) {
            super(itemView);
            //mTitle = (TextView) itemView.findViewById(R.id.hair_title);
            //mDescription = (TextView) itemView.findViewById(R.id.hair_description);
            //mPrice = (TextView) itemView.findViewById(R.id.price);
            mImageView = (ImageView) itemView.findViewById(R.id.style_pic);
        }

        public void setImage(final String imageUrl) {

            Picasso.with(itemView.getContext()).load(imageUrl).placeholder(R.drawable.no_image_placeholder).into(mImageView, new Callback() {
                @Override
                public void onSuccess() {
                    Picasso.with(itemView.getContext()).load(imageUrl).placeholder(R.drawable.no_image_placeholder).into(mImageView);
                }

                @Override
                public void onError() {
                    Picasso.with(itemView.getContext()).load(imageUrl).placeholder(R.drawable.no_image_placeholder).into(mImageView);
                }
            });
        }

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
