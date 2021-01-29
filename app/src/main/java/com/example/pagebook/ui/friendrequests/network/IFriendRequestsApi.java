package com.example.pagebook.ui.friendrequests.network;

import com.example.pagebook.models.FriendRequests;
import com.example.pagebook.models.RespondFriendRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IFriendRequestsApi {
    @GET("profile/getRequestorList/{userId}")
    Call<List<FriendRequests>> getFriendRequests(@Path("userId") String userId, @Header("Authorization") String tokenKey);

    @POST("profile/addNewFriend")
    Call<List<RespondFriendRequest>> acceptRequest(@Body RespondFriendRequest friendRequest, @Header("Authorization") String tokenKey);

    @DELETE("profile/deleteRequest")
    Call<Void> declineRequest(@Query("requestorId") String requestorId, @Query("userId") String userId, @Header("Authorization") String tokenKey);

}

