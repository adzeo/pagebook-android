package com.example.pagebook.ui.friendsprofile.network;

import com.example.pagebook.models.PostDTO;
import com.example.pagebook.models.Profile;
import com.example.pagebook.models.SendFriendRequests;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IFriendProfileApi {
    @GET("profile/userProfileById/{userId}")
    Call<Profile> getFriendProfile(@Path("userId") String friendUserId, @Header("Authorization") String tokenKey);

    @GET("post/getUsersPost/{userId}")
    Call<List<PostDTO>> getFriendsPosts(@Path("userId") String friendId, @Header("Authorization") String tokenKey);

    @POST("profile/addRequest")
    Call<SendFriendRequests> sendFriendRequest(@Body SendFriendRequests friendRequest, @Header("Authorization") String tokenKey);
}
