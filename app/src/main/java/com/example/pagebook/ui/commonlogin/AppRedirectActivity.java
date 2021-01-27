package com.example.pagebook.ui.commonlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.pagebook.PbMainActivity;
import com.example.pagebook.R;
import com.example.pagebook.models.Profile;
import com.example.pagebook.networkmanager.RetrofitBuilder;
import com.example.pagebook.ui.commonlogin.network.IAppLoginApi;
import com.example.pagebook.ui.fragments.profile.PbRegistrationActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AppRedirectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_redirect);

        findViewById(R.id.btn_pagebook).setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE);
            String loggedInEmail = sharedPreferences.getString("UserEmail", "");

            Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
            IAppLoginApi iAppLoginApi = retrofit.create(IAppLoginApi.class);

            // api call for adding in the Profile Service
            Call<Profile> responses = iAppLoginApi.checkUserByEmail(loggedInEmail);
            responses.enqueue(new Callback<Profile>() {
                @Override
                public void onResponse(Call<Profile> call, Response<Profile> response) {
                    if(response.body() != null) {
                        //re-directing to PbMAinActivity for user feeds
                        Log.d("myTag", "onResponse if in app re-direct");
                        Intent intent = new Intent(AppRedirectActivity.this, PbMainActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<Profile> call, Throwable t) {
                    Log.d("myTag", "onFailure in app re-direct");
                    Toast.makeText(AppRedirectActivity.this, "User Details Not Registered", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AppRedirectActivity.this, PbRegistrationActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        });

        //TODO: app re-direct listeners
        findViewById(R.id.btn_quora).setOnClickListener(v -> {

        });

        findViewById(R.id.btn_quiz).setOnClickListener(v -> {

        });

        findViewById(R.id.tv_app_logout).setOnClickListener(v -> {
            SharedPreferences sharedPref = getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("UserEmail", "");
            editor.apply();
            finish();
        });
    }
}