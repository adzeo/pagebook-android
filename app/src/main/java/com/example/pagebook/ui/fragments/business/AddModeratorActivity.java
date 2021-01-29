package com.example.pagebook.ui.fragments.business;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.pagebook.PbMainActivity;
import com.example.pagebook.R;
import com.example.pagebook.mainmodel.ResponseToken;
import com.example.pagebook.models.Moderators;
import com.example.pagebook.models.Profile;
import com.example.pagebook.network.IMainRegistrationApi;
import com.example.pagebook.networkmanager.MainRetrofitBuilder;
import com.example.pagebook.networkmanager.RetrofitBuilder;
import com.example.pagebook.ui.commonlogin.AppRedirectActivity;
import com.example.pagebook.ui.commonlogin.LoginActivity;
import com.example.pagebook.ui.commonlogin.network.IAppLoginApi;
import com.example.pagebook.ui.fragments.business.network.IBusinessProfileApi;
import com.example.pagebook.ui.fragments.profile.PbRegistrationActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddModeratorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_moderator);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_add_moderator);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        TextInputEditText etModeratorEmail = findViewById(R.id.et_add_moderator_name);

        findViewById(R.id.btn_post_moderator).setOnClickListener(v -> {
            //checking if the entered email exists in the profile service
            Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
            IAppLoginApi iAppLoginApi = retrofit.create(IAppLoginApi.class);
            Call<Profile> responses = iAppLoginApi.checkUserByEmail(etModeratorEmail.getText().toString(), getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE).getString("AuthToken", ""));
            responses.enqueue(new Callback<Profile>() {
                @Override
                public void onResponse(Call<Profile> call, Response<Profile> response) {
                    if(response.body() != null) {
                        Profile profile = response.body();
                        initApi(profile.getUserId());
                    }
                }

                @Override
                public void onFailure(Call<Profile> call, Throwable t) {
                    Toast.makeText(AddModeratorActivity.this, "User Doesn't Exist", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void initApi(String moderatorId) {
        Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
        IBusinessProfileApi iBusinessProfileApi = retrofit.create(IBusinessProfileApi.class);
        Call<Moderators> responses = iBusinessProfileApi.addModerator(getIntent().getStringExtra("businessId"), moderatorId, getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE).getString("AuthToken", ""));
        responses.enqueue(new Callback<Moderators>() {
            @Override
            public void onResponse(Call<Moderators> call, Response<Moderators> response) {
                if(response.body() != null) {
                    Toast.makeText(AddModeratorActivity.this, "Moderator Added", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Moderators> call, Throwable t) {
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}