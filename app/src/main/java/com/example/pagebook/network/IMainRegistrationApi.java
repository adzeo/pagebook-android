package com.example.pagebook.network;

import com.example.pagebook.mainmodel.LoginRequest;
import com.example.pagebook.mainmodel.RegistrationRequest;
import com.example.pagebook.mainmodel.ResponseToken;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IMainRegistrationApi {

    @POST("auth/register")
    Call<ResponseToken> registerUser(@Body RegistrationRequest registrationRequest);

    @POST("auth/authenticate")
    Call<ResponseToken> loginUser(@Body LoginRequest loginRequest);
}
