package com.example.raynold.saloonapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raynold.saloonapp.adapter.RegimenAdapter;
import com.example.raynold.saloonapp.model.HairStyle;
import com.example.raynold.saloonapp.R;
import com.example.raynold.saloonapp.model.Regimen;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RegimenAdapter.ListItemClickListener{

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
    private String userId;
    private List<Regimen> mRegimenList;
    private RegimenAdapter mRegimenAdapter;
    private RecyclerView mRegimenRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Views reference
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open, R.string.close);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mNavigationView = (ArcNavigationView) findViewById(R.id.nav_view);
        mRegimenRecycler = (RecyclerView) findViewById(R.id.regimen_recyclerview);

        addRegimen();

        Picasso picasso = Picasso.with(getApplicationContext());
        picasso.setIndicatorsEnabled(false);


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

        setSupportActionBar(mToolbar);

        //mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
        //getSupportActionBar().setIcon(android.R.color.transparent);

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

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

// Get details on the currently active default data network
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

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
            case R.id.menu_styles:
                startActivity(new Intent(MainActivity.this, StylesActivity.class));
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
    public void onListItemClick(Regimen regimen) {
    }

    public void addRegimen(){

        mRegimenList = new ArrayList<>();

        mRegimenList.add(new Regimen("Type 4a",
                "The hair strands of a type 4A are densely packed together giving the hair a thicker appearance but with fine strands. Although 4A hair is better at retaining moisture than the other type 4 hair textures it still requires moisture, and a good detangling technique.",
                "You can wear your hair loose, in twists, or put it up in a bun. You decide. But always make sure your ends are neatly tucked away most of the time, and protect your hair at night with a satin or silk bonnet or pillowcase. If you decide to add extensions, avoid using uneven volumes that may weigh your natural hair down, and ensure that you keep it hydrated. ",
                "Type 4A hair requires frequent moisturizing and as a part of your hair care routine, Use water or aloe vera juice to open up your cuticles, then apply a lightweight leave-in conditioner to ensure that your curls remain hydrated and happy at least three times a week. It is also a good idea to use a light oil like avocado oil or grape seed oil for sealing to help reduce frizz.",
                "Wash every two weeks with a good shampoo of choice. A good detangler and a soft brush will come in handy during your pre poo phase to ensure your curls are separated and to remove any shed hair before you start the process of shampooing your hair. Doing a mud wash every six weeks is also advised to keep your curls shiny, bouncy and your scalp completely build up free.",
                "Always use a deep penetrating conditioner immediately after your shampoo wash. Leave on for five to ten minutes for adequate absorption before rinsing it out. Use a richly moisturizing deep conditioner bi weekly, and a strengthening hot oil treatment using a mix of oils like palm kernel oil, neem oil, and sesame oils every six weeks will also be wonderful for your pampering sessions.",
                R.drawable.a
        ));

        mRegimenList.add(new Regimen("Type 4b",
                "Type 4b has less curl definition and does shrink up a lot. If you have 4B hair, you may notice that your hair strands will closely resemble the letter “z’ and have a cotton-like feeling. Type 4B hair can also range in thickness and requires lots of conditioning and hydration as part of your daily natural hair care routine.",
                "You can wear your hair loose, in twists, or put it up in a bun. You decide. But keeping your hair stretched at all times is important to minimize shrinkage. Using a few drops of an essential oil like lemongrass or rosemary to massage your edges and nape at night also helps with blood circulation and aids hair growth. If you decide to add extensions, avoid using uneven volumes that may weigh your natural hair down, and ensure that you keep it hydrated. Using wigs are also a great option to give your hair a break from constant manipulation.",
                "Type 4B hair requires frequent moisturizing and as a part of your hair care routine, Spray your hair lightly with a mist bottle, then apply a lightweight leave-in conditioner or rich butter to ensure that your curls remain hydrated and happy at least three times a week, seal with a natural oil of choice only if necessary.",
                "Washing your hair with a moisturizing conditioner to restore hydration in between wash days isn’t a bad idea. Using a deep cleansing shampoo or doing a pre poo with a light oil helps to ensure that your hair doesn’t shrink up while washing it.",
                "If your hair feels weak or lacks elasticity, use a protein conditioner to strengthen the hair follicles and prevent breakage. We will advise doing that when required by observing how your hair reacts. Ayurvedic powders like henna, brahmi, amla and bhrinraj are also great alternatives for keeping your protein levels up. Moisturizing deep conditioners can be used once a week or when needed to repair any damage to provide maximum moisture for your natural tresses.",
                R.drawable.b
        ));

        mRegimenList.add(new Regimen("Type 4c",
                "Type 4c hair is extremely fragile and easily susceptible to breakage if not properly handled. This texture has very little to no curl definition, tangles easily, and can be resistant to moisture. Regular conditioning is required in order to prevent the hair from becoming too dry, brittle, and prone to breakage.",
                "Wearing protective styles such as braids, locs, and tucked hairstyles will prevent the hair from immature shedding and unnecessary tangling. Ensuring that your hair is adequately detangled and hydrated before a protective style is also vital.",
                "Type 4C hair loves creamy butters, moisturizing products, and natural oils and butters to hydrate the hair and retain moisture. Whichever methods you choose, Moisturize at least four times a week with emphasis on night care to increase absorption and circulation.",
                "When cleansing 4c hair, it is best to focus on the scalp and avoid agitation to prevent the hair from becoming tangled or knotted. Use a creamy shampoo with lots of slip to aid in detangling the hair. Use a wide-tooth comb while in the shower to remove tangles starting from the ends of the hair and working upwards.",
                "Type 4C hair strands are so tightly compacted, this texture is more prone to breakage than any other hair texture. Infusing rich herbs like burdock root, dandelion root, nettle root, horsetail, and moringa into your weekly conditioning routine helps to strengthen, and moisturize the hair.",
                R.drawable.c
        ));

        mRegimenAdapter = new RegimenAdapter(mRegimenList, MainActivity.this);
        mRegimenRecycler.setHasFixedSize(true);
        mRegimenRecycler.addItemDecoration(new GridSpacingItemDecoration(2,dpToPx(5),true));
        mRegimenRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRegimenRecycler.setAdapter(mRegimenAdapter);

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
