package com.example.pagebook.ui.fragments.profile.network;

import com.example.pagebook.models.Profile;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IRegisterApi {
    @POST("profile/save")
    Call<Profile> saveUser(@Body Profile userProfile, @Header("Authorization") String tokenKey);

    @POST("search/user/")
    Call<Profile> saveUserInSearch(@Body Profile userProfile, @Header("Authorization") String tokenKey);

    @PUT("profile/updateUser/{userId}")
    Call<Profile> changeUser(@Body Profile userProfile, @Path("userId") String userId, @Header("Authorization") String tokenKey);

    @PUT("search/user/{id}")
    Call<Profile> changeUserInSearch(@Body Profile userProfile, @Path("id") String userId, @Header("Authorization") String tokenKey);
}
