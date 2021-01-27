package com.example.pagebook.ui.commonlogin.network;

import com.example.pagebook.models.Profile;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IAppLoginApi {

    @GET("profile/userProfile/{email}")
    Call<Profile> checkUserByEmail(@Path("email") String email);
}
