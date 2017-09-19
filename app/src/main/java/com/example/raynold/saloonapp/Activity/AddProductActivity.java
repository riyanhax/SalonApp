package com.example.raynold.saloonapp.Activity;

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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.raynold.saloonapp.Model.Shop;
import com.example.raynold.saloonapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class AddProductActivity extends AppCompatActivity {

    private final int GALARY_PICKER = 1;

    private Toolbar mToolbar;
    private DatabaseReference mProductRef;
    private TextInputLayout mProductName;
    private TextInputLayout mProductPrice;
    private TextInputLayout mProductLocation;
    private ImageButton mProductImage;
    private TextInputLayout mProductInfo;
    private ProgressDialog mProgressDialog;
    private Button mAddProduct;
    private FirebaseStorage mImageStorage;
    private String productName;
    private String downloadedUri;
    private ProgressDialog mImageDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        mToolbar = (Toolbar) findViewById(R.id.add_product);
        mProductRef = FirebaseDatabase.getInstance().getReference().child("Shop");
        mImageStorage = FirebaseStorage.getInstance();

        mProductName = (TextInputLayout) findViewById(R.id.add_product_name);
        mProductPrice = (TextInputLayout) findViewById(R.id.add_product_price);
        mProductImage = (ImageButton) findViewById(R.id.add_product_image);
        mProductInfo = (TextInputLayout) findViewById(R.id.add_product_info);
        mProductLocation = (TextInputLayout) findViewById(R.id.add_product_location);
        mAddProduct = (Button) findViewById(R.id.add_new_product);
        mImageDialog = new ProgressDialog(this);


        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Adding product");
        mProgressDialog.setMessage("Adding product to database...");
        mProgressDialog.setCanceledOnTouchOutside(false);

        mImageDialog.setTitle("Loading Image");
        mImageDialog.setMessage("Loading image...");
        mImageDialog.setCanceledOnTouchOutside(false);

        mProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galaryIntent = new Intent();
                galaryIntent.setType("image/*");
                galaryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galaryIntent, "SELECT IMAGE"),GALARY_PICKER);
            }
        });


        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Add product");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addProduct();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALARY_PICKER && resultCode == RESULT_OK) {

            Uri imageUri = data.getData();

            CropImage.activity(imageUri).setAspectRatio(1, 1).start(this);

        }

            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

                CropImage.ActivityResult result = CropImage.getActivityResult(data);

                if (resultCode == RESULT_OK) {

                    mImageDialog.show();

                    Uri resultUri = result.getUri();

                    StorageReference reference = mImageStorage.getReference().child("product_photos").child(productName+".jpg");

                    reference.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @SuppressWarnings("VisibleForTests")
                        @Override
                        public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {

                            if (task.isSuccessful()) {

                                downloadedUri = task.getResult().getDownloadUrl().toString();

                                mImageDialog.dismiss();

                            }
                        }
                    });

                }
            }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {


        }
        return super.onOptionsItemSelected(item);
    }

    public void addProduct() {

        productName = mProductName.getEditText().getText().toString();
        String productPrice = mProductPrice.getEditText().getText().toString();
        String productInfo = mProductInfo.getEditText().getText().toString();
        String productLocation = mProductLocation.getEditText().getText().toString();

        if (!TextUtils.isEmpty(productName) || !TextUtils.isEmpty(productPrice) || !TextUtils.isEmpty(productInfo) || !TextUtils.isEmpty(productLocation)) {

            mProgressDialog.show();

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("name", productName);
            hashMap.put("price", productPrice);
            hashMap.put("detail", productInfo);
            hashMap.put("image",downloadedUri);
            hashMap.put("location",productLocation);

            mProductRef.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()){
                        mProgressDialog.dismiss();
                        startActivity(new Intent(AddProductActivity.this, ShopActivity.class));
                        Toasty.info(AddProductActivity.this, ""+downloadedUri, Toast.LENGTH_LONG).show();
                        Toasty.success(AddProductActivity.this, "product added succesfully", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {

            Toasty.error(AddProductActivity.this, "fields cant be empty", Toast.LENGTH_LONG).show();
        }



    }
}
