package com.example.pagebook.ui.fragments.homefeed.network;

import android.app.Notification;

import com.example.pagebook.models.PostAction;
import com.example.pagebook.models.PostDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IPostsApi {

    @GET("post/getFeedPosts/{userId}/{page}")
    Call<List<PostDTO>> getFeedPosts(@Path("userId") String userId, @Path("page") int page, @Header("Authorization") String tokenKey);

    @GET("post/getUsersPost/{userId}")
    Call<List<PostDTO>> getUsersPosts(@Path("userId") String userId, @Header("Authorization") String tokenKey);

    @POST("post/addAction")
    Call<PostAction> postPostAction(@Body PostAction postAction, @Header("Authorization") String tokenKey);
}
