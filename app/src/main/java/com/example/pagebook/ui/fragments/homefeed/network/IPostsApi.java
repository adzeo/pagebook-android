package com.example.pagebook.ui.fragments.homefeed.network;

import com.example.pagebook.responsemodel.GenericResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IPostsApi {

    @GET("posts")
    Call<GenericResponse> getFeedPosts();
}
