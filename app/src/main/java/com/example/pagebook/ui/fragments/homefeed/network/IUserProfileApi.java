package com.example.pagebook.ui.fragments.homefeed.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface IUserProfileApi {

    @GET("profile/getFriendsId/{userId}")
    Call<List<String>> getMyFriendsIdList(@Path("userId") String userId, @Header("Authorization") String tokenKey);

    @GET("business/following/{userId}")
    Call<List<String>> getMyPagesFollowingIdList(@Path("userId") String userId, @Header("Authorization") String tokenKey);
}
