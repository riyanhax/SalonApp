package com.example.raynold.saloonapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.raynold.saloonapp.R;

public class LogInActivity extends AppCompatActivity {

    private Button mSignInBtn;
    private Button mRegBtn;
    private Toolbar mLoginToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mSignInBtn = (Button) findViewById(R.id.goto_signin_activity);
        mRegBtn = (Button)findViewById(R.id.goto_reg_activity);
        mLoginToolbar = (Toolbar) findViewById(R.id.login_toolbar);

        setSupportActionBar(mLoginToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, SignInActivity.class));
            }
        });

        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
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
