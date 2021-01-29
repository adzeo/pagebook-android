package com.example.pagebook.ui.stories;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pagebook.R;
import com.example.pagebook.models.Story;
import com.example.pagebook.models.user.User;
import com.example.pagebook.models.user.UserBuilder;
import com.example.pagebook.networkmanager.RetrofitBuilder;
import com.example.pagebook.ui.stories.network.IStoriesApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddStoryActivity extends AppCompatActivity {

    User myUser = UserBuilder.getInstance();
    Story userStory = new Story();
    private Uri filePath;

    ImageView ivStory;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_story);
        progressDialog=new ProgressDialog(this);
        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_add_story);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //setting the values for the profile
        ivStory = findViewById(R.id.iv_story_img);

        findViewById(R.id.btn_choose_story_img).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
        });

        findViewById(R.id.btn_upload_story_img).setOnClickListener(v -> {
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
                                        userStory.setFileUrl(task.getResult().toString());
                                        userStory.setUserId(myUser.getId());
                                        userStory.setUserName(myUser.getFirstName() + " " + myUser.getLastName());
                                        userStory.setUserImageUrl(myUser.getImgUrl());
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
                                Toast.makeText(getApplicationContext(), "onFailure Error: " + exception.getMessage(), Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        });
            }
            //if there is not any file
            else {
                Toast.makeText(getApplicationContext(), "File Not Found", Toast.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.btn_add_story).setOnClickListener(v -> {

            Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
            IStoriesApi iStoriesApi = retrofit.create(IStoriesApi.class);

            // api call for adding in the Profile Service
            Call<Story> responses = iStoriesApi.postUserStory(userStory, getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE).getString("AuthToken", ""));
            responses.enqueue(new Callback<Story>() {
                @Override
                public void onResponse(Call<Story> call, Response<Story> response) {
                    Toast.makeText(AddStoryActivity.this, "Story Added", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<Story> call, Throwable t) {
                    Toast.makeText(AddStoryActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
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
                ivStory.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
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