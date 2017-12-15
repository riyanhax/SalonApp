package com.example.raynold.saloonapp.activity;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.raynold.saloonapp.model.Lumo;
import com.example.raynold.saloonapp.R;
import com.example.raynold.saloonapp.model.Services;
import com.example.raynold.saloonapp.adapter.ServicesAdapter;
import com.example.raynold.saloonapp.viewmodel.NewShopItemViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import es.dmoral.toasty.Toasty;

public class ServicesActivity extends AppCompatActivity implements ServicesAdapter.ListItemClickListener{

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private Toolbar mServicesToolbar;
    private ServicesAdapter mServicesAdapter;
    private RecyclerView mServicesRecycler;
    private List<Services> mServicesList = new ArrayList<>();
    private RecyclerView.ItemDecoration mItemDecoration;
    private NewShopItemViewModel newListItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        ((Lumo) this.getApplication())
                .getApplicationComponent()
                .inject(this);


        mServicesList.add(new Services("Washing", "Starting at N500", R.drawable.lumo_pic_five, "Washing can be; with black soap & ACV, with castile soap, or with regular shampoo and conditioner"));
        mServicesList.add(new Services("Rinse", "Starting at N1000", R.drawable.lumo_pic_five, "Rinses can be added to wash routine for an added charge of N500."));
        mServicesList.add(new Services("Deep Conditioning", "Starting at N2500", R.drawable.lumo_pic_five, "Deep conditioning can be done weekly or biweekly. Customers on our chart get 10% discount for frequent DCs. DC with client’s product is N2500.*for hair maintenance on a budget, please consult the manager."));
        mServicesList.add(new Services("Styling", "Starting at N500", R.drawable.lumo_pic_five, "Home service will be for double salon fee and attracts an extra transport charge of N1000."+"\n" + "Prices may also vary according to length needed, time spent and technique involved."));
        mServicesList.add(new Services("Hair & Skin Consult", "Starting at N500", R.drawable.lumo_pic_five, "Consults will include customer hair history, minor tests and a session of information sharing."));


        mServicesAdapter = new ServicesAdapter(mServicesList, this);

        mServicesToolbar = (Toolbar) findViewById(R.id.services_toolbar);
        mServicesRecycler = (RecyclerView) findViewById(R.id.services_recyclerview);
        mServicesRecycler.setLayoutManager(new LinearLayoutManager(this));
        mItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mServicesRecycler.setHasFixedSize(true);
        mServicesRecycler.addItemDecoration(mItemDecoration);
        mServicesRecycler.setAdapter(mServicesAdapter);
        setSupportActionBar(mServicesToolbar);
        getSupportActionBar().setTitle("Services");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Set up and subscribe (observe) to the ViewModel
        newListItemViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(NewShopItemViewModel.class);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(Services services) {

//        Intent serviceDetailIntent = new Intent(ServicesActivity.this, ServicesDetailActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("title",services.getTitle());
//        bundle.putString("detail", services.getDetails());
//        bundle.putString("price", services.getPrice());
//        serviceDetailIntent.putExtras(bundle);
//        startActivity(serviceDetailIntent);
    }
}
