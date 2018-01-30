package com.example.raynold.saloonapp.service;

import com.example.raynold.saloonapp.model.Token;
import com.example.raynold.saloonapp.util.FirebaseMessagingService;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by RAYNOLD on 1/28/2018.
 */

public class MyFirebaseIdService extends FirebaseInstanceIdService{

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String tokenRefresh = FirebaseInstanceId.getInstance().getToken();

        sendTokenToServer(tokenRefresh);
    }

    private void sendTokenToServer(String tokenRefresh) {
        FirebaseFirestore mTokenRef = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        Token token = new Token(tokenRefresh, false);

        mTokenRef.collection("Token").document(mAuth.getUid()).set(token);
    }
}
