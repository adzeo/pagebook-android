package com.example.pagebook.ui.addposts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pagebook.R;
import com.example.pagebook.models.Post;
import com.example.pagebook.models.user.User;
import com.example.pagebook.models.user.UserBuilder;
import com.example.pagebook.networkmanager.RetrofitBuilder;
import com.example.pagebook.ui.addposts.network.IAddPostAPi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class AddPostActivity extends AppCompatActivity {

    List<String> postCategoryMenuList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapterPostCategory;

    List<String> postTypeMenuList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapterPostType;

    User myUser = UserBuilder.getInstance();

    private Uri filePath;

    ImageView ivPost;
    String fileUrl;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        progressDialog = new ProgressDialog(this);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_business_search);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //for post category autocomplete view
        AutoCompleteTextView postCategory = findViewById(R.id.auto_tv_post_category);
        //for post type autocomplete view
        AutoCompleteTextView postType = findViewById(R.id.auto_tv_post_type);

        TextInputLayout tvLayout = findViewById(R.id.tv_post_text);
        TextInputEditText etTextPost = findViewById(R.id.et_post_text);
        ConstraintLayout ivLayout = findViewById(R.id.iv_post_layout);
        ivPost = findViewById(R.id.iv_post_img);
        Button btnChoosePost = findViewById(R.id.btn_choose_post_img);
        Button btnUploadPost = findViewById(R.id.btn_upload_post_img);

        postCategoryMenuList.add("Bollywood");
        postCategoryMenuList.add("Maths");
        postCategoryMenuList.add("Science");
        postCategoryMenuList.add("Sports");
        postCategoryMenuList.add("Technology");

        arrayAdapterPostCategory = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, postCategoryMenuList);
        postCategory.setText("Bollywood", false);
        postCategory.setAdapter(arrayAdapterPostCategory);
        postCategory.setThreshold(1);

        postTypeMenuList.add("TEXT");
        postTypeMenuList.add("IMAGE");

        arrayAdapterPostType = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, postTypeMenuList);
        postType.setText("TEXT", false);
        postType.setAdapter(arrayAdapterPostType);
        postType.setThreshold(1);

        Post post = new Post();
        post.setPostCategory(postCategory.getText().toString());
        post.setFileType(postType.getText().toString());

        postCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                post.setPostCategory(arrayAdapterPostCategory.getItem(position));
            }
        });

        postType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                post.setFileType(arrayAdapterPostType.getItem(position));
                if (arrayAdapterPostType.getItem(position).equals("TEXT")) {
                    tvLayout.setVisibility(View.VISIBLE);
                    ivLayout.setVisibility(View.INVISIBLE);
                } else {
                    tvLayout.setVisibility(View.INVISIBLE);
                    ivLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        btnChoosePost.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
        });

        btnUploadPost.setOnClickListener(v -> {
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
                                        post.setFileURL(task.getResult().toString());
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
        });

        findViewById(R.id.btn_add_post).setOnClickListener(v -> {
            post.setUserId(myUser.getId());
            post.setProfileType(myUser.getProfileType());
            if (post.getFileType().equals("TEXT")) {
                post.setFileURL(etTextPost.getText().toString());
            }

            if(getIntent().getStringExtra("business").equals("true")) {
                post.setProfileType("BUSINESS");
                post.setBusinessId(getIntent().getStringExtra("businessId"));
            }

            Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
            IAddPostAPi iAddPostAPi = retrofit.create(IAddPostAPi.class);
            Call<Post> responses = iAddPostAPi.addPost(post, getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE).getString("AuthToken", ""));
            responses.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, retrofit2.Response<Post> responseData) {
                    if (responseData.body() != null) {
                        Toast.makeText(AddPostActivity.this, "Post successfully uploaded", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddPostActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {
                    Toast.makeText(AddPostActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
                ivPost.setImageBitmap(bitmap);

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