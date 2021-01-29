package com.example.pagebook.ui.stories.network;

import com.example.pagebook.models.StoryFeedDTO;
import com.example.pagebook.models.Story;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IStoriesApi {
    @GET("story/feed/user/{userId}")
    Call<StoryFeedDTO> getStories(@Path("userId") String userId, @Header("Authorization") String tokenKey);

    @POST("story/")
    Call<Story> postUserStory(@Body Story story, @Header("Authorization") String tokenKey);
}
