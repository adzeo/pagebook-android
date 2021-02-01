package com.example.pagebook.ui.fragments.business;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pagebook.R;
import com.example.pagebook.models.BusinessProfile;
import com.example.pagebook.models.user.User;
import com.example.pagebook.models.user.UserBuilder;
import com.example.pagebook.networkmanager.RetrofitBuilder;
import com.example.pagebook.ui.fragments.business.network.IRegisterBusinessApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BusinessRegistrationActivity extends AppCompatActivity {

    User myUser = UserBuilder.getInstance();

    List<String> businessCategoryMenuList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapterBusinessCategory;

    BusinessProfile businessProfile = new BusinessProfile();
    private Uri filePath;

    ImageView ivBusinessProfile;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_registration);

        progressDialog = new ProgressDialog(this);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_business_registration);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //to display business category autocomplete view
        TextInputEditText etBusinessName = findViewById(R.id.et_business_name);
        TextInputEditText etBusinessEmail = findViewById(R.id.et_business_reg_email);
        TextInputEditText etBusinessDesc = findViewById(R.id.et_business_reg_desc);
        AutoCompleteTextView businessCategory = findViewById(R.id.auto_tv_business_reg_category);

        businessCategoryMenuList.add("Bollywood");
        businessCategoryMenuList.add("Maths");
        businessCategoryMenuList.add("Science");
        businessCategoryMenuList.add("Sports");
        businessCategoryMenuList.add("Technology");

        arrayAdapterBusinessCategory = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, businessCategoryMenuList);
        businessCategory.setAdapter(arrayAdapterBusinessCategory);
        businessCategory.setThreshold(1);

        //NOTE: setting the values for the profile
        etBusinessEmail.setText(myUser.getEmail());
        ivBusinessProfile = findViewById(R.id.iv_business_reg_pic);

//        findViewById(R.id.btn_choose_business_dp).setOnClickListener(v -> {
//            Intent intent = new Intent();
//            intent.setType("image/*");
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
//        });

        findViewById(R.id.btn_upload_business_dp).setOnClickListener(v -> {

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);

//            if (filePath != null) {
//                progressDialog.setMessage("Please Wait, Image is Getting Uploaded...");
//                progressDialog.show();
//                progressDialog.setCanceledOnTouchOutside(false);
//                FirebaseStorage storage = FirebaseStorage.getInstance();
//                StorageReference storageRef = storage.getReference();
//                StorageReference riversRef = storageRef.child("images/" + new Date().toString() + ".jpg");
//                riversRef.putFile(filePath)
//                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                //if the upload is successful
//                                Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
//                                riversRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Uri> task) {
//                                        Log.d("myTag", task.getResult().toString());
//                                        businessProfile.setImageUrl(task.getResult().toString());
//                                        progressDialog.dismiss();
//                                    }
//                                });
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception exception) {
//                                //if the upload is not successful
//                                //and displaying error message
//                                Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
//                                progressDialog.dismiss();
//                            }
//                        });
//            }
//            //if there is not any file
//            else {
//                Toast.makeText(getApplicationContext(), "File Not Found", Toast.LENGTH_LONG).show();
//            }
        });

        findViewById(R.id.btn_business_register).setOnClickListener(v -> {
            // get input from the user
            businessProfile.setBusinessName(etBusinessName.getText().toString());
            businessProfile.setAdminEmail(etBusinessEmail.getText().toString());
            businessProfile.setCategory(businessCategory.getText().toString());
            businessProfile.setDescription(etBusinessDesc.getText().toString());

            // api call for adding in the Business Service
            Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
            IRegisterBusinessApi iRegisterBusinessApi = retrofit.create(IRegisterBusinessApi.class);
            Call<BusinessProfile> registerBusinessResponse = iRegisterBusinessApi.saveBusiness(businessProfile, myUser.getId(), getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE).getString("AuthToken", ""));
            registerBusinessResponse.enqueue(new Callback<BusinessProfile>() {
                @Override
                public void onResponse(Call<BusinessProfile> call, Response<BusinessProfile> response) {
                    Toast.makeText(BusinessRegistrationActivity.this, "User Registered", Toast.LENGTH_SHORT).show();
                    BusinessProfile savedBusinessProfile = response.body();

                    initSaveInSearchApi(savedBusinessProfile);

                    //re-directing to BusinessProfilePageActivity for user feeds
                    Intent intent = new Intent(BusinessRegistrationActivity.this, BusinessProfilePageActivity.class);
                    intent.putExtra("businessProfileId", savedBusinessProfile.getId());
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<BusinessProfile> call, Throwable t) {
                    Toast.makeText(BusinessRegistrationActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        });
    }

    private void initSaveInSearchApi(BusinessProfile savedBusinessProfile) {

        Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
        IRegisterBusinessApi iRegisterBusinessSearchApi = retrofit.create(IRegisterBusinessApi.class);

        //api call to for adding in the Search Service
        Call<BusinessProfile> registerSearchResponse = iRegisterBusinessSearchApi.saveBusinessInSearch(savedBusinessProfile, getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE).getString("AuthToken", ""));
        registerSearchResponse.enqueue(new Callback<BusinessProfile>() {
            @Override
            public void onResponse(Call<BusinessProfile> call, Response<BusinessProfile> response) {
                Log.d("myTag", "Saved Business in Search");
            }

            @Override
            public void onFailure(Call<BusinessProfile> call, Throwable t) {
                Toast.makeText(BusinessRegistrationActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ivBusinessProfile.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (filePath != null) {
            progressDialog.setMessage("Please Wait, Image is Getting Uploaded...");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();
            StorageReference riversRef = storageRef.child("images/" + new Date().toString() + ".jpg");
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successful
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                            riversRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Log.d("myTag", task.getResult().toString());
                                    businessProfile.setImageUrl(task.getResult().toString());
                                    progressDialog.dismiss();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successful
                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    });
        }
        //if there is not any file
        else {
            Toast.makeText(getApplicationContext(), "File Not Found", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}