package com.example.raynold.saloonapp.detail;


import android.animation.Animator;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.raynold.saloonapp.model.Lumo;
import com.example.raynold.saloonapp.R;
import com.example.raynold.saloonapp.data.WishListModel;
import com.example.raynold.saloonapp.viewmodel.NewShopItemViewModel;
import com.example.raynold.saloonapp.viewmodel.SavedItemCollectionViewModel;
import com.example.raynold.saloonapp.viewmodel.ShopItemViewModel;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment{

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    NewShopItemViewModel listItemCollectionViewModel;
    ShopItemViewModel mShopItemViewModel;
    SavedItemCollectionViewModel mSavedItemCollectionViewModel;

    @BindView(R.id.product_name)
    TextView mProductName;
    @BindView(R.id.product_price)
    TextView mProductPrice;
    @BindView(R.id.product_info)
    TextView mProductInfo;
    @BindView(R.id.product_image)
    ImageView mProductImage;
    @BindView(R.id.product_location)
    TextView mProductLocation;
    @BindView(R.id.shop_collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.wishlist_detail_btn)
    ImageButton mWishlist;
    //private ImageButton mShare;
    @BindView(R.id.fb_call)
    FloatingActionButton mCall;
    private TextView mWishAdded;
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
        ButterKnife.bind(this, v);

        mWishAdded = (TextView) v.findViewById(R.id.tv_added_wishlist);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ShopExpandableAppbar);
        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
        mCall.setBackgroundColor(getResources().getColor(R.color.white));

        productName = getArguments().getString("name");
        final String productPrice = getArguments().getString("price" );
        final String productImage = getArguments().getString("image");
        final String location = getArguments().getString("location");
        final String details = getArguments().getString("detail");
        Picasso picasso = Picasso.with(getContext());
        picasso.setIndicatorsEnabled(false);
        mCollapsingToolbarLayout.setTitle(productName);

        mProductPrice.setText(String.valueOf("₦" + productPrice));
        mProductInfo.setText(details);
        mProductLocation.setText(location);
        mCall.bringToFront();



        try {
            Picasso.with(getContext()).load(productImage).into(mProductImage, new Callback() {
                @Override
                public void onSuccess() {
                    Picasso.with(getContext()).load(productImage).into(mProductImage);
                }

                @Override
                public void onError() {

                }
            });
        }catch (OutOfMemoryError e) {
            e.printStackTrace();
        }


        mWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (wish == 0) {
                    WishListModel wishListModel = new WishListModel(productName,productName,productImage,productPrice,location,details,1);
                    listItemCollectionViewModel.addNewItemToDatabase(wishListModel);
                    mWishAdded.setVisibility(View.VISIBLE);
                    mWishAdded.animate().alpha(1f).setDuration(100).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {

                            mWishlist.setImageResource(R.drawable.like);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                    wish = 1;
                    //mWishAdded.setVisibility(View.GONE);
                } else if (wish == 1){

                    mWishlist.setImageResource(R.drawable.heart_button);
                    wish = 0;
                }

            }
        });

        mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:08094823173"));

                startActivity(callIntent);
            }
        });

//        mShare.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String whatsAppMessage = "";
//
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT, whatsAppMessage);
//                sendIntent.setType("text/plain");
//
//                // Do not forget to add this to open whatsApp App specifically
//                sendIntent.setPackage("com.whatsapp");
//                startActivity(sendIntent);
//            }
//        });

        // Inflate the layout for this fragment
        return v;
    }

}
