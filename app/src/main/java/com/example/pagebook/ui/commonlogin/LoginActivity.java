package com.example.pagebook.ui.commonlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.pagebook.R;
import com.example.pagebook.mainmodel.LoginRequest;
import com.example.pagebook.mainmodel.ResponseToken;
import com.example.pagebook.network.IMainRegistrationApi;
import com.example.pagebook.networkmanager.MainRetrofitBuilder;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputEditText etLoginEmail = findViewById(R.id.et_login_email);
        TextInputEditText etLoginPwd = findViewById(R.id.et_login_password);

        LoginRequest loginRequest = new LoginRequest();
        findViewById(R.id.btn_login).setOnClickListener(v -> {

            loginRequest.setUserName(etLoginEmail.getText().toString());
            loginRequest.setPassword(etLoginPwd.getText().toString());

            Retrofit retrofit = MainRetrofitBuilder.getInstance(getString(R.string.mainBaseUrl));
            IMainRegistrationApi iMainRegistrationApi = retrofit.create(IMainRegistrationApi.class);
            // api call for adding in the Profile Service
            Call<ResponseToken> responses = iMainRegistrationApi.loginUser(loginRequest);
            responses.enqueue(new Callback<ResponseToken>() {
                @Override
                public void onResponse(Call<ResponseToken> call, Response<ResponseToken> response) {
                    if (response.body() != null) {
//                        if (!response.body().getError().equals("Incorrect Username and Password")) {
                            SharedPreferences sharedPreferences = getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("AuthToken", response.body().getData().getJwtToken());

                            editor.putString("UserEmail", etLoginEmail.getText().toString());
                            editor.apply();

                            Intent intent = new Intent(LoginActivity.this, AppRedirectActivity.class);
                            startActivity(intent);
                            finish();
//                        }
//                        else {
//                            Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
//                            startActivity(intent);
//                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseToken> call, Throwable t) {
                    Log.d("myTag", "onFailure in app re-direct");

                }
            });

            //TODO: api call to common infra for OAuth
        });

        findViewById(R.id.tv_sign_up).setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });
    }
}