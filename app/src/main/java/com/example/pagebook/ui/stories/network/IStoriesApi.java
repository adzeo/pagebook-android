package com.example.pagebook.ui.stories.network;

import com.example.pagebook.responsemodel.GenericResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IStoriesApi {
    @GET("posts")
    Call<GenericResponse> getStories();
}
