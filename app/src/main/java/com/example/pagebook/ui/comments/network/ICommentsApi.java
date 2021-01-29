package com.example.pagebook.ui.comments.network;

import com.example.pagebook.models.CommentDTO;
import com.example.pagebook.models.Comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ICommentsApi {

    @POST("post/addComment")
    Call<Comment> addComment(@Body Comment comment, @Header("Authorization") String tokenKey);

    @GET("post/getComments")
    Call<List<CommentDTO>> getPostComments(@Query("parentCommentId") String parentCommentId, @Query("postId") String postId, @Header("Authorization") String tokenKey);


}
