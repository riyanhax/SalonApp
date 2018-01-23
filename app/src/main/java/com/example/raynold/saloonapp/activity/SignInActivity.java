package com.example.raynold.saloonapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.raynold.saloonapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import mehdi.sakout.fancybuttons.FancyButton;

public class SignInActivity extends AppCompatActivity {

    @BindView(R.id.email)
    MaterialEditText mUsername;
    @BindView(R.id.password)
    MaterialEditText mPassword;
    @BindView(R.id.sign_in_button)
    FancyButton mLoginBtn;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog;
    private DatabaseReference mUserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(this);


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(mUsername.getText().toString()) && !TextUtils.isEmpty(mPassword.getText().toString())) {
                    String email = mUsername.getText().toString();
                    String password = mPassword.getText().toString();

                    if (email.contains("@")) {
                        mProgressDialog.show();
                        mProgressDialog.setTitle("Login In");
                        mProgressDialog.setMessage("Please wait while we log you in");
                        mProgressDialog.setCanceledOnTouchOutside(false);
                        loginUser(email, password);
                    } else {
                        Toasty.error(SignInActivity.this, "Invalid Email", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toasty.error(SignInActivity.this, "Sorry Login forms can't be empty", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void loginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            String userId = mAuth.getCurrentUser().getUid();
                            String device_token = FirebaseInstanceId.getInstance().getToken();
                            mUserRef.child(userId).child("device_token").setValue(device_token).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    mProgressDialog.dismiss();
                                    Intent mainIntent = new Intent(SignInActivity.this, MainActivity.class);
                                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(mainIntent);
                                    finish();
                                }
                            });

                        }

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            mProgressDialog.hide();
                            Log.w("LoginActivity", "signInWithEmail:failed", task.getException());
                            Toasty.error(SignInActivity.this,"Failed to Sign In",Toast.LENGTH_LONG).show();
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
}
