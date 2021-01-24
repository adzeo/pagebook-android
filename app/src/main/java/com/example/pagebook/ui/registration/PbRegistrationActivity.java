package com.example.pagebook.ui.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.pagebook.MainActivity;
import com.example.pagebook.R;

public class PbRegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pb_registration);


        findViewById(R.id.btn_user_register).setOnClickListener(v -> {

            // TODO: call profile post api

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}