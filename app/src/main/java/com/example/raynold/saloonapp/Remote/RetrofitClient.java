package com.example.raynold.saloonapp.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by RAYNOLD on 1/28/2018.
 */

public class RetrofitClient {

    private static Retrofit sRetrofit = null;

    public static Retrofit getRetrofit(String baseUrl) {

        if (sRetrofit ==null) {

            sRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return sRetrofit;
    }
}

