package com.example.raynold.saloonapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.raynold.saloonapp.activity.AddProductActivity;
import com.example.raynold.saloonapp.adapter.ShopAdapter;
import com.example.raynold.saloonapp.model.Shop;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment {

    String userId;
    @BindView(R.id.shop_frag_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.shop_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.shop_frag_layout)
    RelativeLayout mRelativeLayout;
    private RecyclerView.ItemDecoration mItemDecoration;
    private DatabaseReference mShopRef;
    private FloatingActionButton mAddProduct;
    private DatabaseReference mUserRef;
    private FirebaseAuth mAuth;
    private LinearLayoutManager mLinearLayoutManager;
    private int wish = 0;
    private FirebaseFirestore mFirebaseFirestore;
    private ShopAdapter mShopAdapter;
    private List<Shop> mShopList;


    public ShopFragment() {
        // Required empty public constructor
    }

    public static ShopFragment newInstance() {

        return new ShopFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mShopList.clear();
        //mRecyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShopFragment shopFragment = new ShopFragment();
        shopFragment.setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shop, container, false);
        ButterKnife.bind(this, v);

        mShopList = new ArrayList<>();
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(mItemDecoration);

        mFirebaseFirestore = FirebaseFirestore.getInstance();
//        mShopRef = FirebaseDatabase.getInstance().getReference().child("Shop");
//        mShopRef.keepSynced(true);

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Shop");
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setHasOptionsMenu(true);

        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mUserRef.keepSynced(true);
        mAuth = FirebaseAuth.getInstance();

        try {
            userId = mAuth.getCurrentUser().getUid();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        mAddProduct = (FloatingActionButton) v.findViewById(R.id.fb_add);



        Picasso picasso = Picasso.with(getContext());
        picasso.setIndicatorsEnabled(false);

        mAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddProductActivity.class));
            }
        });

        getUserData();
        getShopData();

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRelativeLayout.removeAllViews();
    }

    public void getShopData() {

        mShopList.clear();
        mFirebaseFirestore.collection("Shop").addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (e != null) {
                    Log.i("ShopFrag", e.getMessage());
                }

                for (DocumentChange documentChange : documentSnapshots.getDocumentChanges()) {
                    if (documentChange.getType() == DocumentChange.Type.ADDED) {
                        Shop shop = documentChange.getDocument().toObject(Shop.class);

                        mShopList.add(shop);
                        mShopAdapter = new ShopAdapter(mShopList, getContext());
                        mRecyclerView.setAdapter(mShopAdapter);
                    }
                }
            }
        });
    }



    @Override
    public void onStart() {
        super.onStart();
        mShopList.clear();
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
                            mAddProduct.setVisibility(View.VISIBLE);
                        } else {
                            mAddProduct.setVisibility(View.INVISIBLE);
                        }

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            mAddProduct.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == android.R.id.home){
            getActivity().finish();
        }
        return true;
    }


    @Override
    public void onStop() {
        super.onStop();
    }

}
