package com.example.raynold.saloonapp.Remote;

import com.example.raynold.saloonapp.model.MyResponse;
import com.example.raynold.saloonapp.model.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by RAYNOLD on 1/28/2018.
 */

public interface APIService {

    @Headers(

            {
                    "Content-type:application/json",
                    "Authorization:key=AAAAflEKcJs:APA91bFGZBVKFq_6MOWsjrEk1ljrYlM3KTK6GWT9qbIpZGTh3PUkg1t5_XXm99r6gOOQX5E-7D87PwaxkU15nOoXFu05ROQ-sCfyd0cwpdqiPt2cu6ALTKku-I7mQvy_xohmxZH0g8HK"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
