package com.example.raynold.saloonapp.detail;


import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.raynold.saloonapp.R;
import com.example.raynold.saloonapp.data.SavedItemDatabase;
import com.example.raynold.saloonapp.data.WishListModel;
import com.example.raynold.saloonapp.saved.WishList;
import com.example.raynold.saloonapp.saved.WishListFragment;
import com.example.raynold.saloonapp.viewmodel.NewShopItemViewModel;
import com.example.raynold.saloonapp.viewmodel.SavedItemCollectionViewModel;
import com.example.raynold.saloonapp.viewmodel.ShopItemViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends LifecycleFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    NewShopItemViewModel listItemCollectionViewModel;
    ShopItemViewModel mShopItemViewModel;
    SavedItemCollectionViewModel mSavedItemCollectionViewModel;

    private Toolbar mToolbar;
    private TextView mProductName;
    private TextView mProductPrice;
    private TextView mProductInfo;
    private ImageView mProductImage;
    private TextView mProductLocation;
    private ImageButton mWishlist;
    private int wish = 0;
    private String productName;


    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WishListFragment.
     */
    public static DetailFragment newInstance(String id, String name, String image, String location, String details,String price, int saved) {

        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("itemId", id);
        bundle.putString("name", name);
        bundle.putString("location", location);
        bundle.putString("price", price);
        bundle.putString("image", image);
        bundle.putString("detail", details);

        detailFragment.setArguments(bundle);

        return detailFragment;
    }

    /*------------------------------- Lifecycle -------------------------------*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((Lumo) getActivity().getApplication())
                .getApplicationComponent()
                .inject(this);

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Set up and subscribe (observe) to the ViewModel
        mSavedItemCollectionViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SavedItemCollectionViewModel.class);

        listItemCollectionViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(NewShopItemViewModel.class);

        try {
            mShopItemViewModel = ViewModelProviders.of(this, viewModelFactory)
                    .get(ShopItemViewModel.class);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        mShopItemViewModel.getListItemById(productName).observe(this, new Observer<WishListModel>() {
            @Override
            public void onChanged(@Nullable WishListModel listItem) {

                try {
                    wish = listItem.getSaved();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

                if (wish == 0) {
                    mWishlist.setImageResource(R.drawable.heart_button);
                } else {
                    mWishlist.setImageResource(R.drawable.like);
                }
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_detail, container, false);


        mToolbar = (Toolbar) v.findViewById(R.id.product_layout);
        mProductName = (TextView) v.findViewById(R.id.product_name);
        mProductPrice = (TextView) v.findViewById(R.id.product_price);
        mProductImage = (ImageView) v.findViewById(R.id.product_image);
        mProductLocation = (TextView) v.findViewById(R.id.product_location);
        mProductInfo = (TextView) v.findViewById(R.id.product_info);
        mWishlist = (ImageButton) v.findViewById(R.id.wishlist_detail_btn);


        productName = getArguments().getString("name");
        final String productPrice = getArguments().getString("price" );
        final String productImage = getArguments().getString("image");
        final String location = getArguments().getString("location");
        final String details = getArguments().getString("detail");
        Picasso picasso = Picasso.with(getContext());
        picasso.setIndicatorsEnabled(false);

        //mToolbar.setTitle("Shop");

        mProductName.setText(productName);
        mProductPrice.setText(String.valueOf("N " +productPrice));

        Picasso.with(getContext()).load(productImage).placeholder(R.drawable.no_image_placeholder).into(mProductImage);
        mProductLocation.setText(location);
        mProductInfo.setText(details);
        mProductPrice.setText("N " + productPrice);

        mWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //mWishlist.setImageResource(R.drawable.like);
                if (wish == 0) {
                    WishListModel wishListModel = new WishListModel(productName,productName,productImage,productPrice,location,details,1);
                    listItemCollectionViewModel.addNewItemToDatabase(wishListModel);
                }

            }
        });

        // Inflate the layout for this fragment
        return v;
    }

}
