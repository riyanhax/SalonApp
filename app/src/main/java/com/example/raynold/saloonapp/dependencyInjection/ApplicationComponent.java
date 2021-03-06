package com.example.raynold.saloonapp.dependencyInjection;

import android.app.Application;

import com.example.raynold.saloonapp.activity.ProductDetailActivity;
import com.example.raynold.saloonapp.activity.ServicesActivity;
import com.example.raynold.saloonapp.activity.ShopActivity;
import com.example.raynold.saloonapp.ShopFragment;
import com.example.raynold.saloonapp.detail.DetailFragment;
import com.example.raynold.saloonapp.saved.WishListFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by RAYNOLD on 9/19/2017.
 */

@Singleton
@Component(modules = {ApplicationModule.class,RoomModule.class})
public interface ApplicationComponent {

    void inject(WishListFragment wishListFragment);
    void inject(ServicesActivity servicesActivity);
    void inject(ProductDetailActivity productDetailActivity);
    void inject(ShopActivity shopActivity);
    void inject(DetailFragment detailFragment);
    void inject(ShopFragment shopFragment);

    Application application();

}
