package com.example.pagebook.ui.friendrequests.network;

import com.example.pagebook.models.FriendRequests;
import com.example.pagebook.models.RespondFriendRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IFriendRequestsApi {
    @GET("profile/getRequestorList/{userId}")
    Call<List<FriendRequests>> getFriendRequests(@Path("userId") String userId);

    @POST("profile/addNewFriend")
    Call<Void> acceptRequest(@Body RespondFriendRequest friendRequest);

    @DELETE("profile/deleteRequest")
    Call<Void> declineRequest(@Body RespondFriendRequest friendRequest);

}

