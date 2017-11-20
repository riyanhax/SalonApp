package com.example.raynold.saloonapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.raynold.saloonapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class SignUpActivity extends AppCompatActivity {

    private Toolbar mSignUpToolbar;
    private TextInputLayout mRegUsername;
    private TextInputLayout mRegEmail;
    private TextInputLayout mRegPhoneNum;
    private TextInputLayout mRegPassword;
    private TextInputLayout mRegRePassword;
    private Button mRegBtn;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private DatabaseReference mUserRef;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        referenceViews();

        mSignUpToolbar = (Toolbar) findViewById(R.id.sign_up_toolbar);
        setSupportActionBar(mSignUpToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sign up");

        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = mRegUsername.getEditText().getText().toString();
                String email = mRegEmail.getEditText().getText().toString();
                String phoneNumb = mRegPhoneNum.getEditText().getText().toString();
                String password = mRegPassword.getEditText().getText().toString();
                String rePassword = mRegRePassword.getEditText().getText().toString();

                if (!TextUtils.isEmpty(username) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(phoneNumb) || !TextUtils.isEmpty(password) || !TextUtils.isEmpty(rePassword)) {

                    if (password.length() > 6 ) {

                        if (password.equals(rePassword)) {

                            mProgressDialog.setTitle("Registering User");
                            mProgressDialog.setMessage("Please wait while we create your account");
                            mProgressDialog.setCanceledOnTouchOutside(false);
                            mProgressDialog.show();
                            registerUser(username,email,phoneNumb,password);


                        }else {
                            Toasty.error(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
                        }
                    } else {

                        Toasty.error(SignUpActivity.this, "Passwords too simple", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toasty.error(SignUpActivity.this, "Fields Cannot be empty", Toast.LENGTH_LONG).show();
                }


            }
        });

    }

    public void registerUser(final String username, final String email, final String phoneNumb, String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    String userId = mFirebaseUser.getUid();
                    String device_token = FirebaseInstanceId.getInstance().getToken();

                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("device_token", device_token);
                    hashMap.put("name", username);
                    hashMap.put("email", email);
                    hashMap.put("image", "default");
                    hashMap.put("thumb_image", "default");
                    hashMap.put("phone_number", phoneNumb);

                    mUserRef.child(userId).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                mProgressDialog.dismiss();
                                Intent mainIntent = new Intent(SignUpActivity.this, MainActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();
                            }

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                mProgressDialog.hide();
                                Toast.makeText(SignUpActivity.this, "Error registering you, please check your internet connection.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void referenceViews() {

        mRegUsername = (TextInputLayout) findViewById(R.id.reg_display_name);
        mRegEmail = (TextInputLayout) findViewById(R.id.register_email);
        mRegPhoneNum = (TextInputLayout) findViewById(R.id.reg_phone_num);
        mRegPassword = (TextInputLayout) findViewById(R.id.register_password);
        mRegRePassword = (TextInputLayout) findViewById(R.id.register_re_password);
        mProgressDialog = new ProgressDialog(this);
        mRegBtn = (Button) findViewById(R.id.reg_create_button);
        mAuth = FirebaseAuth.getInstance();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
    }
}

