package com.example.raynold.saloonapp.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.example.raynold.saloonapp.R;
import com.example.raynold.saloonapp.model.HairStyle;
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

public class StylesActivity extends AppCompatActivity {

    static ProgressBar mProgressBar;
    private RecyclerView mStylesRecyclerView;
    private DatabaseReference mHairStyleRef;
    private FloatingActionButton mAddNewHairStyle;
    private DatabaseReference mUserRef;
    private DataSnapshot mDataSnapshot;
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_styles);

        //database query
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mUserRef.keepSynced(true);
        mHairStyleRef = FirebaseDatabase.getInstance().getReference().child("HairStyles");
        mHairStyleRef.keepSynced(true);

        Picasso picasso = Picasso.with(getApplicationContext());
        picasso.setIndicatorsEnabled(false);


        mAuth =FirebaseAuth.getInstance();
        mToolbar = (Toolbar) findViewById(R.id.styles_toolbar);
        mAddNewHairStyle = (FloatingActionButton) findViewById(R.id.fb_add_new_style);
        mProgressBar = (ProgressBar) findViewById(R.id.styles_progress);
        mStylesRecyclerView = (RecyclerView) findViewById(R.id.styles_recyclerView);
        mStylesRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(5), true));
        mStylesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mStylesRecyclerView.setHasFixedSize(true);
        mStylesRecyclerView.setItemViewCacheSize(8);
        mStylesRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        mStylesRecyclerView.setDrawingCacheEnabled(true);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Styles");

        mAddNewHairStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddStyleActivity.class));

            }
        });

        if (mAuth.getCurrentUser() == null) {
            mAddNewHairStyle.setVisibility(View.INVISIBLE);
        }


        getUserData();

    }

    public void getUserData() {

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            String userUid = mAuth.getCurrentUser().getUid();
            mUserRef.child(userUid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    try {
                        if (dataSnapshot.hasChild("admin")) {
                            mAddNewHairStyle.setVisibility(View.VISIBLE);
                        } else {
                            mAddNewHairStyle.setVisibility(View.INVISIBLE);
                        }

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<HairStyle, HairStyleViewHolder>(HairStyle.class, R.layout.hair_list_item, HairStyleViewHolder.class, mHairStyleRef) {
                    @Override
                    protected void populateViewHolder(final HairStyleViewHolder viewHolder, final HairStyle model, int position) {

                        viewHolder.setImage(model.getPicUrl());

                        mProgressBar.setVisibility(View.INVISIBLE);

                        mStylesRecyclerView.setVisibility(View.VISIBLE);


                        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Bundle bundle = new Bundle();
                                Intent styleIntent = new Intent(StylesActivity.this, StyleDetailActivity.class);
                                bundle.putString("image", model.getPicUrl());
                                bundle.putString("title", model.getTitle());
                                bundle.putString("description", model.getDescription());
                                styleIntent.putExtras(bundle);
                                startActivity(styleIntent);

                            }
                        });

                    }

                    @Override
                    protected void onCancelled(DatabaseError error) {
                        super.onCancelled(error);
                        mProgressBar.setVisibility(View.INVISIBLE);
                    }
                };
        mStylesRecyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public static class HairStyleViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public ProgressBar mProgressBar;
        View mView;

        public HairStyleViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            mImageView = (ImageView) itemView.findViewById(R.id.style_pic);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.hair_progress);
        }

        public void setImage(final String imageUrl) {
            mImageView.setVisibility(View.VISIBLE);
            Picasso.with(itemView.getContext()).load(imageUrl).resize(220, 150).into(mImageView, new Callback() {
                @Override
                public void onSuccess() {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    Picasso.with(itemView.getContext()).load(imageUrl).resize(220, 150).into(mImageView);
                }

                @Override
                public void onError() {
                    Picasso.with(itemView.getContext()).load(imageUrl).into(mImageView);
                    mProgressBar.setVisibility(View.VISIBLE);

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
