package com.example.pagebook.ui.addposts.network;

import com.example.pagebook.models.Post;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IAddPostAPi {

    @POST("post")
    Call<Post> addPost(@Body Post post, @Header("Authorization") String tokenKey);
}
