package com.example.raynold.saloonapp.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.raynold.saloonapp.R;

public class CallUsActivity extends AppCompatActivity {

    Intent callIntent;
    private Toolbar mCallUsToolbar;
    private TextView mCallNumber;
    private ImageButton mCallButton;
    private ImageButton mWhatsappButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_us);

        mCallUsToolbar = (Toolbar) findViewById(R.id.call_us_toolbar);
        mCallNumber = (TextView) findViewById(R.id.call_number);
        mCallButton = (ImageButton) findViewById(R.id.imageButton);
        mWhatsappButton = (ImageButton) findViewById(R.id.imageButton2);

        setSupportActionBar(mCallUsToolbar);
        getSupportActionBar().setTitle("Call Us");

        setSupportActionBar(mCallUsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:08094823173"));

                startActivity(callIntent);
                //finish();
            }
        });

        mWhatsappButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

                intent.putExtra(ContactsContract.Intents.Insert.NAME, "Lumo Naturals");
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, "08079602395");

                startActivity(intent);
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
