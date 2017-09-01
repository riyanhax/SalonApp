package com.example.raynold.saloonapp.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
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
import android.widget.Toast;

import com.example.raynold.saloonapp.Model.HairStyle;
import com.example.raynold.saloonapp.Adapter.HairStyleAdapter;
import com.example.raynold.saloonapp.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, HairStyleAdapter.ListItemClickListener {

    private RecyclerView mHairRecyclerview;
    private LinearLayoutManager mLinearLayoutManager;
    private HairStyleAdapter mStyleAdapter;
    private List<HairStyle> mHairStyleList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private Toolbar mToolbar;
    private RecyclerView.ItemDecoration mItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open, R.string.close);
        mHairRecyclerview = (RecyclerView) findViewById(R.id.style_recyclerview);
        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);

        setNavigationViewListener();
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
                startActivity(new Intent(MainActivity.this, JobActivity.class));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
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
