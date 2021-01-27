package com.example.pagebook.ui.commonlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.pagebook.R;
import com.google.android.material.textfield.TextInputEditText;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        TextInputEditText etLoginEmail = findViewById(R.id.et_register_email);
        TextInputEditText etLoginPwd = findViewById(R.id.et_register_password);

        findViewById(R.id.btn_register).setOnClickListener(v -> {

            //TODO: api call to common infra for OAuth and storing the user details

            finish();

        });
    }
}