package com.example.pagebook.ui.fragments.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.pagebook.PbMainActivity;
import com.example.pagebook.R;
import com.example.pagebook.networkmanager.RetrofitBuilder;
import com.example.pagebook.models.Profile;
import com.example.pagebook.ui.fragments.profile.network.IRegisterApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PbRegistrationActivity extends AppCompatActivity {

    Profile userProfile = new Profile();
    private Uri filePath;

    ImageView ivProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pb_registration);

        //setting the values for the profile
        ivProfile = findViewById(R.id.iv_reg_pic);

        findViewById(R.id.btn_choose_dp).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
        });

        findViewById(R.id.btn_upload_dp).setOnClickListener(v -> {
            if (filePath != null) {
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
                                        userProfile.setImgUrl(task.getResult().toString());
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
                            }
                        });
            }

            //if there is not any file
            else {
                Toast.makeText(getApplicationContext(), "File Not Found", Toast.LENGTH_LONG).show();
            }
        });

        TextInputEditText etFName = findViewById(R.id.et_reg_fname);
        TextInputEditText etLName = findViewById(R.id.et_reg_lname);
        TextInputEditText etEmail = findViewById(R.id.et__reg_email);
        RadioGroup rgProfileType = findViewById(R.id.radioGroup_reg_profile_type);
        ChipGroup cgInterests = findViewById(R.id.chip_group_reg_interests);
        TextInputEditText etBio = findViewById(R.id.et_reg_bio);

        etEmail.setText(getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE).getString("UserEmail", ""));
        userProfile.setProfileType("PUBLIC");

        rgProfileType.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_reg_private:
                    userProfile.setProfileType("PRIVATE");
                    break;
                case R.id.rb_reg_public:
                    userProfile.setProfileType("PUBLIC");
                    break;
            }
            Log.d("ProfileType",userProfile.getProfileType());
        });

        findViewById(R.id.btn_user_register).setOnClickListener(v -> {
            // get input from the user
            userProfile.setFirstName(etFName.getText().toString());
            userProfile.setLastName(etLName.getText().toString());
            userProfile.setEmail(etEmail.getText().toString());


            String userInterests = "";

            int chipCount = cgInterests.getChildCount();
            for (int i = 0; i < chipCount; i++) {
                Chip selectedChip = (Chip) cgInterests.getChildAt(i);
                if (selectedChip.isChecked()) {
                    userInterests += selectedChip.getText().toString() + ",";
                }
            }
            userProfile.setInterest(userInterests);
            userProfile.setBio(etBio.getText().toString());


            // api call for adding in the Profile Service
            Retrofit retrofit1 = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
            IRegisterApi iRegisterApi = retrofit1.create(IRegisterApi.class);
            Call<Profile> registerProfileResponse = iRegisterApi.saveUser(userProfile);
            registerProfileResponse.enqueue(new Callback<Profile>() {
                @Override
                public void onResponse(Call<Profile> call, Response<Profile> response) {
                    if (response.body() != null) {
                        Toast.makeText(PbRegistrationActivity.this, "User Registered", Toast.LENGTH_SHORT).show();
                        Profile savedProfile = response.body();
                        initSaveUserInSearchApi(savedProfile);

                        //re-directing to PbMAinActivity for user feeds
                        Intent intent = new Intent(PbRegistrationActivity.this, PbMainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(PbRegistrationActivity.this, "Unable to Register the User", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Profile> call, Throwable t) {
                    Toast.makeText(PbRegistrationActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void initSaveUserInSearchApi(Profile savedProfile) {
        //api call to for adding in the Search Service
        Retrofit retrofit2 = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
        IRegisterApi iRegisterInSearchApi = retrofit2.create(IRegisterApi.class);
        Call<Profile> registerSearchResponse = iRegisterInSearchApi.saveUserInSearch(savedProfile);
        registerSearchResponse.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Toast.makeText(PbRegistrationActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
                ivProfile.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}