package com.example.pagebook.ui.comments.network;

import com.example.pagebook.models.Comments;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ICommentsApi {

    @POST("post/addComment")
    Call<Comments> addComment(@Body Comments comment);

    @GET("post/getComments")
    Call<List<Comments>> getPostComments(@Query("parentCommentId") String parentCommentId, @Query("postId") String postId);


}
