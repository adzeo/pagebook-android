package com.example.pagebook.ui.commonlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.pagebook.R;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputEditText etLoginEmail = findViewById(R.id.et_login_email);
        TextInputEditText etLoginPwd = findViewById(R.id.et_login_password);

        findViewById(R.id.btn_login).setOnClickListener(v -> {

            SharedPreferences sharedPref = getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("UserEmail", etLoginEmail.getText().toString());
            editor.apply();

            Intent intent = new Intent(LoginActivity.this, AppRedirectActivity.class);
            startActivity(intent);

            //TODO: api call to common infra for OAuth
        });

        findViewById(R.id.tv_sign_up).setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });
    }
}