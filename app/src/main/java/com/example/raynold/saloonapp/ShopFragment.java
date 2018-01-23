package com.example.raynold.saloonapp;


import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raynold.saloonapp.activity.AddProductActivity;
import com.example.raynold.saloonapp.model.Lumo;
import com.example.raynold.saloonapp.model.Shop;
import com.example.raynold.saloonapp.data.WishListModel;
import com.example.raynold.saloonapp.detail.WishListDetailActivity;
import com.example.raynold.saloonapp.viewmodel.NewShopItemViewModel;
import com.example.raynold.saloonapp.viewmodel.ShopItemViewModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import es.dmoral.toasty.Toasty;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends LifecycleFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    NewShopItemViewModel listItemCollectionViewModel;
    ShopItemViewModel mShopItemViewModel;
    FirebaseRecyclerAdapter<Shop, ShopViewHolder> firebaseRecyclerAdapter;
    String userId;
    private List<Shop> mShopList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.ItemDecoration mItemDecoration;
    private DatabaseReference mShopRef;
    private FloatingActionButton mAddProduct;
    private DatabaseReference mUserRef;
    private FirebaseAuth mAuth;
    private RecyclerView.AdapterDataObserver mDataObserver;
    private ProgressBar mProgressBar;
    private DataSnapshot mDataSnapshot;
    private LinearLayoutManager mLinearLayoutManager;
    private Parcelable listState;
    private int wish = 0;
    private String productName;
    private ShopViewHolder mShopViewHolder;
    private LifecycleOwner mLifecycleOwner = this;

    public ShopFragment() {
        // Required empty public constructor
    }

    public static ShopFragment newInstance() {

        return new ShopFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((Lumo) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);

        //Set up and subscribe (observe) to the ViewModel
        listItemCollectionViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(NewShopItemViewModel.class);

        try {
            mShopItemViewModel = ViewModelProviders.of(this, viewModelFactory)
                    .get(ShopItemViewModel.class);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

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

        mRecyclerView = (RecyclerView) v.findViewById(R.id.shop_recycler);
        mItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        mShopRef = FirebaseDatabase.getInstance().getReference().child("Shop");
        mShopRef.keepSynced(true);
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mUserRef.keepSynced(true);
        mAuth = FirebaseAuth.getInstance();

        try {
            userId = mAuth.getCurrentUser().getUid();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        mAddProduct = (FloatingActionButton) v.findViewById(R.id.fb_add);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemViewCacheSize(8);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(mItemDecoration);

        Picasso picasso = Picasso.with(getContext());
        picasso.setIndicatorsEnabled(false);

        mAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddProductActivity.class));
            }
        });

        getUserData();
        //getProductData();

        return v;
    }

    public void getProductData() {

        firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Shop, ShopViewHolder>(Shop.class,R.layout.shop_list_item,ShopViewHolder.class,mShopRef) {
                    @Override
                    protected void populateViewHolder(final ShopViewHolder viewHolder, final Shop model, int position) {

                        mShopViewHolder = viewHolder;

                        productName = model.getName();
                        viewHolder.setPrice(model.getPrice());
                        viewHolder.setTitle(model.getName());
                        viewHolder.setStore(model.getLocation());
                        viewHolder.setImage(model.getImage());

                        try {

                            mShopItemViewModel.getListItemById(productName).observe(mLifecycleOwner, new Observer<WishListModel>() {
                                @Override
                                public void onChanged(@Nullable WishListModel listItem) {
                                    try {

                                        wish = listItem.getSaved();
                                    } catch (NullPointerException e) {
                                        e.printStackTrace();
                                    }

                                    if (wish == 0) {
                                        mShopViewHolder.mWishListBtn.setImageResource(R.drawable.heart_button);
                                    } else {
                                        mShopViewHolder.mWishListBtn.setImageResource(R.drawable.like);
                                    }
                                }
                            });
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }

                        viewHolder.mWishListBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                viewHolder.mWishListBtn.setImageResource(R.drawable.like);
                                WishListModel wishListModel =  new WishListModel(model.getName()
                                        ,model.getName(),model.getImage(),model.getPrice()
                                        ,model.getLocation(), model.getDetail(),1);
                                listItemCollectionViewModel.addNewItemToDatabase(wishListModel);
                                Toasty.info(getContext(), "saved to database", Toast.LENGTH_LONG).show();
                            }
                        });


                        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String productName = model.getName();
                                String productLocation = model.getLocation();
                                String productPrice = model.getPrice();
                                String productImage = model.getImage();
                                String productDetails = model.getDetail();

                                Intent detailIntent = new Intent(getContext(), WishListDetailActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("itemId", productName);
                                bundle.putString("name", productName);
                                bundle.putString("location", productLocation);
                                bundle.putString("price", productPrice);
                                bundle.putString("image", productImage);
                                bundle.putString("detail", productDetails);
                                detailIntent.putExtras(bundle);
                                startActivity(detailIntent);

                            }
                        });

                    }
                };
        firebaseRecyclerAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    @Override
    public void onStart() {
        super.onStart();
        getProductData();
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
    public void onStop() {
        super.onStop();
    }

    public static class ShopViewHolder extends RecyclerView.ViewHolder {

        TextView mShopTitle;
        TextView mShopPrice;
        ImageView mShop_image;
        TextView mShopStore;
        ImageButton mWishListBtn;
        ProgressBar mProgressBar;

        public ShopViewHolder(View itemView) {
            super(itemView);

            mShop_image = (ImageView) itemView.findViewById(R.id.shop_image);
            mShopPrice = (TextView) itemView.findViewById(R.id.shop_price);
            mShopTitle = (TextView) itemView.findViewById(R.id.shop_title);
            mShopStore = (TextView) itemView.findViewById(R.id.shop_store);
            mWishListBtn = (ImageButton) itemView.findViewById(R.id.wishlist_btn);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.shop_pic_progress);


        }

        public void setPrice(String price) {
            mShopPrice.setText("₦"+ price);

        }
        public void setTitle(String title) {
            mShopTitle.setText(title);

        }
        public void setStore(String store) {
            mShopStore.setText(store);

        }
        public void setImage(final String image) {

            Picasso.with(itemView.getContext()).load(image).resize(220, 200).into(mShop_image, new Callback() {
                @Override
                public void onSuccess() {
                    mProgressBar.setVisibility(View.INVISIBLE);
                    Picasso.with(itemView.getContext()).load(image).resize(220, 200).into(mShop_image);
                }

                @Override
                public void onError() {
                    mProgressBar.setVisibility(View.VISIBLE);
                    Picasso.with(itemView.getContext()).load(image).into(mShop_image);
                }
            });
        }
    }

}
