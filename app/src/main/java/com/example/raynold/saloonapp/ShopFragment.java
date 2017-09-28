package com.example.raynold.saloonapp;


import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raynold.saloonapp.Activity.AddProductActivity;
import com.example.raynold.saloonapp.Activity.ShopActivity;
import com.example.raynold.saloonapp.Model.Lumo;
import com.example.raynold.saloonapp.Model.Shop;
import com.example.raynold.saloonapp.data.WishListModel;
import com.example.raynold.saloonapp.detail.WishListDetailActivity;
import com.example.raynold.saloonapp.viewmodel.NewShopItemViewModel;
import com.example.raynold.saloonapp.viewmodel.SavedItemCollectionViewModel;
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shop, container, false);

        mProgressBar = (ProgressBar) v.findViewById(R.id.shop_progressbar);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.shop_recycler);
        mItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        mShopRef = FirebaseDatabase.getInstance().getReference().child("Shop");
        mShopRef.keepSynced(true);
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mUserRef.keepSynced(true);
        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        mAddProduct = (FloatingActionButton) v.findViewById(R.id.fb_add);

        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(mItemDecoration);

        Picasso picasso = Picasso.with(getContext());
        picasso.setIndicatorsEnabled(false);

        mAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddProductActivity.class));
                //Toasty.info(ShopActivity.this, "Clicked on the Float action button", Toast.LENGTH_LONG).show();
            }
        });

        mUserRef.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDataSnapshot = dataSnapshot;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseRecyclerAdapter<Shop, ShopViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Shop, ShopViewHolder>(Shop.class,R.layout.shop_list_item,ShopViewHolder.class,mShopRef) {
                    @Override
                    protected void populateViewHolder(final ShopViewHolder viewHolder, final Shop model, int position) {

                        mShopViewHolder = viewHolder;

                        productName = model.getName();
                        viewHolder.setPrice(model.getPrice());
                        viewHolder.setTitle(model.getName());
                        viewHolder.setStore(model.getLocation());
                        viewHolder.setImage(model.getImage());

                        if (mDataSnapshot.hasChild("admin")) {
                            mAddProduct.setVisibility(View.VISIBLE);
                        } else {
                            mAddProduct.setVisibility(View.INVISIBLE);
                        }

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

                        mProgressBar.setVisibility(View.INVISIBLE);
                        mRecyclerView.setVisibility(View.VISIBLE);

                        viewHolder.mWishListBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                viewHolder.mWishListBtn.setImageResource(R.drawable.like);
                                WishListModel wishListModel =  new WishListModel(model.getName()
                                        ,model.getName(),model.getImage(),model.getPrice()
                                        ,model.getLocation(), model.getDetails(),1);
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
                                String productDetails = model.getDetails();

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
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

        return v;
    }

    public static class ShopViewHolder extends RecyclerView.ViewHolder {

        TextView mShopTitle;
        TextView mShopPrice;
        ImageView mShop_image;
        TextView mShopStore;
        ImageButton mWishListBtn;

        public ShopViewHolder(View itemView) {
            super(itemView);

            mShop_image = (ImageView) itemView.findViewById(R.id.shop_image);
            mShopPrice = (TextView) itemView.findViewById(R.id.shop_price);
            mShopTitle = (TextView) itemView.findViewById(R.id.shop_title);
            mShopStore = (TextView) itemView.findViewById(R.id.shop_store);
            mWishListBtn = (ImageButton) itemView.findViewById(R.id.wishlist_btn);


        }

        public void setPrice(String price) {
            mShopPrice.setText("N "+ price);

        }
        public void setTitle(String title) {
            mShopTitle.setText(title);

        }
        public void setStore(String store) {
            mShopStore.setText(store);

        }
        public void setImage(final String image) {
            Picasso.with(itemView.getContext()).load(image).placeholder(R.drawable.no_image_placeholder).into(mShop_image, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {

                    Picasso.with(itemView.getContext()).load(R.drawable.no_image_placeholder).into(mShop_image);
                }
            });
        }
    }

}
