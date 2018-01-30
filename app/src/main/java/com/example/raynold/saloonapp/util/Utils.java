package com.example.raynold.saloonapp.util;

import com.example.raynold.saloonapp.Remote.APIService;
import com.example.raynold.saloonapp.Remote.RetrofitClient;

import retrofit2.Retrofit;

/**
 * Created by RAYNOLD on 1/28/2018.
 */

public class Utils {


    private static final String BASE_URL = "https://fcm.googleapis.com/";

    public static APIService getFCMService(){

        return RetrofitClient.getRetrofit(BASE_URL).create(APIService.class);
    }
}
