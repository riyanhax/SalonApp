package com.example.raynold.saloonapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.raynold.saloonapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.Random;

import es.dmoral.toasty.Toasty;

public class AddStyleActivity extends AppCompatActivity {

    private final int GALARY_PICKER = 2;

    private TextInputLayout mHairName;
    private TextInputLayout mHairDescription;
    private ImageView mHairImage;
    private Button mHairButton;
    private Toolbar mNewHairToolbar;
    private String hairName;
    private String downloadedUri;
    private ProgressDialog mImageDialog;
    private ProgressDialog mNewStyleDialog;
    private ImageView mImagePreview;

    private DatabaseReference mHairRef;
    private FirebaseStorage mHairStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_style);

        mHairRef = FirebaseDatabase.getInstance().getReference().child("HairStyles");
        mHairStorage = FirebaseStorage.getInstance();

        mHairName = (TextInputLayout) findViewById(R.id.et_add_style_name);
        mHairDescription = (TextInputLayout) findViewById(R.id.et_add_style_info);
        mHairButton = (Button) findViewById(R.id.bt_new_style);
        mHairImage = (ImageView) findViewById(R.id.add_hair_image);
        mNewHairToolbar = (Toolbar) findViewById(R.id.new_style_appbar);
        mImagePreview = (ImageView) findViewById(R.id.im_hair_preview);

        setSupportActionBar(mNewHairToolbar);
        try{
            getSupportActionBar().setTitle("Add New Hair");
        }catch (NullPointerException e) {
            e.printStackTrace();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mNewStyleDialog = new ProgressDialog(this);
        mImageDialog = new ProgressDialog(this);

        mNewStyleDialog.setCanceledOnTouchOutside(false);
        mNewStyleDialog.setTitle("Adding product");
        mNewStyleDialog.setMessage("Adding product to database...");

        mImageDialog = new ProgressDialog(this);
        mImageDialog.setTitle("Loading Image");
        mImageDialog.setCanceledOnTouchOutside(false);

        mHairImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galaryIntent = new Intent();
                galaryIntent.setType("image/*");
                galaryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galaryIntent, "SELECT IMAGE"), GALARY_PICKER);
            }
        });

        mHairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addHair();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALARY_PICKER && resultCode == RESULT_OK) {

            Uri imageUri = data.getData();

            CropImage.activity(imageUri).setAspectRatio(3, 3).start(this);

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                mImageDialog.show();

                Uri resultUri = result.getUri();

                Random random = new Random();
                int randomNum = random.nextInt(1 + 100000) + 28;

                StorageReference reference = mHairStorage.getReference().child("product_photos").child(randomNum+".jpg");

                reference.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {

                        if (task.isSuccessful()) {

                            downloadedUri = task.getResult().getDownloadUrl().toString();
                            Picasso.with(getApplicationContext()).load(downloadedUri).into(mImagePreview);

                            mImageDialog.dismiss();

                        }
                    }
                });

            }
        }

    }

    public void addHair() {

        hairName = mHairName.getEditText().getText().toString();
        String hairInfo = mHairDescription.getEditText().getText().toString();

        if (!TextUtils.isEmpty(hairName) || !TextUtils.isEmpty(hairInfo)) {

            mImageDialog.show();

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("title", hairName);
            hashMap.put("description", hairInfo);
            hashMap.put("picUrl",downloadedUri);

            mHairRef.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()){
                        mImageDialog.dismiss();
                        startActivity(new Intent(AddStyleActivity.this, MainActivity.class));
                        Toasty.success(AddStyleActivity.this, "product added succesfully", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {

            Toasty.error(AddStyleActivity.this, "fields cant be empty", Toast.LENGTH_LONG).show();
        }



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


