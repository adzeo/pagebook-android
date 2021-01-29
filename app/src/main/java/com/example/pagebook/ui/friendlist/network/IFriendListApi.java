package com.example.pagebook.ui.friendlist.network;

import com.example.pagebook.models.FriendsDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface IFriendListApi {
    @GET("profile/findFriendList/{userId}")
    Call<List<FriendsDTO>> getFriendList(@Path("userId") String userId, @Header("Authorization") String tokenKey);
}
