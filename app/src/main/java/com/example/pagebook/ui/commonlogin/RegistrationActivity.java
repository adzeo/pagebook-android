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
import com.example.pagebook.mainmodel.RegistrationRequest;
import com.example.pagebook.mainmodel.ResponseToken;
import com.example.pagebook.models.Profile;
import com.example.pagebook.network.IMainRegistrationApi;
import com.example.pagebook.networkmanager.MainRetrofitBuilder;
import com.example.pagebook.networkmanager.RetrofitBuilder;
import com.example.pagebook.ui.commonlogin.network.IAppLoginApi;
import com.example.pagebook.ui.fragments.profile.PbRegistrationActivity;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        RegistrationRequest registrationRequest = new RegistrationRequest();
        TextInputEditText etLoginEmail = findViewById(R.id.et_register_email);
        TextInputEditText etLoginPwd = findViewById(R.id.et_register_password);

        List<String> chipList = new ArrayList<>();
        ChipGroup chipGroup = (ChipGroup) findViewById(R.id.chip_group_main_login_interests);
        findViewById(R.id.btn_register).setOnClickListener(v -> {


            int chipCount = chipGroup.getChildCount();
            for (int i = 0; i < chipCount; i++) {
                Chip selectedChip = (Chip) chipGroup.getChildAt(i);
                if (selectedChip.isChecked()) {
                    chipList.add(selectedChip.getText().toString());
                }
            }

            registrationRequest.setUserName(etLoginEmail.getText().toString());
            registrationRequest.setPassword(etLoginPwd.getText().toString());
            registrationRequest.setInterests(chipList);

            Retrofit retrofit = MainRetrofitBuilder.getInstance(getString(R.string.mainBaseUrl));
            IMainRegistrationApi iMainRegistrationApi = retrofit.create(IMainRegistrationApi.class);
            // api call for adding in the Profile Service
            Call<ResponseToken> responses = iMainRegistrationApi.registerUser(registrationRequest);
            responses.enqueue(new Callback<ResponseToken>() {
                @Override
                public void onResponse(Call<ResponseToken> call, Response<ResponseToken> response) {
                    if (response.body() != null) {
                        SharedPreferences sharedPreferences = getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("AuthToken", response.body().getData().getJwtToken());
                        editor.apply();
                        Intent intent = new Intent(RegistrationActivity.this, AppRedirectActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<ResponseToken> call, Throwable t) {
                    Log.d("myTag", "onFailure in app re-direct");

                }
            });
        });
    }
}