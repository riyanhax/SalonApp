package com.example.raynold.saloonapp.Activity;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raynold.saloonapp.Adapter.HairStyleAdapter;
import com.example.raynold.saloonapp.Model.HairStyle;
import com.example.raynold.saloonapp.Model.Lumo;
import com.example.raynold.saloonapp.R;
import com.example.raynold.saloonapp.Model.Shop;
import com.example.raynold.saloonapp.ShopFragment;
import com.example.raynold.saloonapp.Util.BaseActivity;
import com.example.raynold.saloonapp.data.WishListModel;
import com.example.raynold.saloonapp.detail.DetailFragment;
import com.example.raynold.saloonapp.detail.WishListDetailActivity;
import com.example.raynold.saloonapp.saved.WishListFragment;
import com.example.raynold.saloonapp.viewmodel.NewShopItemViewModel;
import com.example.raynold.saloonapp.viewmodel.SavedItemCollectionViewModel;
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

public class ShopActivity extends BaseActivity{

    private static final String SHOP_FRAG = "SHOP_FRAG";
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        mToolbar = (Toolbar) findViewById(R.id.shop_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Shop");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManager = getSupportFragmentManager();
        ShopFragment shopFragment = (ShopFragment) fragmentManager.findFragmentByTag(SHOP_FRAG);

        if (shopFragment == null) {
            shopFragment = ShopFragment.newInstance();

            addFragmentToActivity(fragmentManager,shopFragment,R.id.root_shop_activity, SHOP_FRAG);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
