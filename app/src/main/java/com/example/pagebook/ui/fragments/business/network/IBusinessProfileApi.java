package com.example.pagebook.ui.fragments.business.network;

import com.example.pagebook.models.BusinessDTO;
import com.example.pagebook.models.BusinessProfile;
import com.example.pagebook.models.Comment;
import com.example.pagebook.models.Followers;
import com.example.pagebook.models.Moderators;
import com.example.pagebook.models.Post;
import com.example.pagebook.models.PostDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IBusinessProfileApi {
    @GET("business/{businessId}")
    Call<BusinessProfile> getBusinessProfile(@Path("businessId") String businessId, @Header("Authorization") String tokenKey);

    @GET("business/following/details/{userId}")
    Call<List<BusinessDTO>> getBusinessPagesFollowingList(@Path("userId") String userId, @Header("Authorization") String tokenKey);

    @GET("business/moderators/{businessId}")
    Call<Moderators> getBusinessModeratorsList(@Path("businessId") String businessId, @Header("Authorization") String tokenKey);

    @POST("business//addmoderator/{businessId}/{moderatorId}")
    Call<Moderators> addModerator(@Path("businessId") String businessId, @Path("moderatorId") String moderatorId, @Header("Authorization") String tokenKey);

    @GET("post/getBusinessPost/{businessId}")
    Call<List<PostDTO>> getBusinessPosts(@Path("businessId") String businessId, @Header("Authorization") String tokenKey);

    @GET("post/getUnapprovedPost/{businessId}")
    Call<List<PostDTO>> getUnapprovedBusinessPosts(@Path("businessId") String businessId, @Header("Authorization") String tokenKey);

    @PUT("post/approvePost/{postId}")
    Call<Integer> updateApprovedPost(@Path("postId") String postId, @Header("Authorization") String tokenKey);

    @DELETE("post/unApprovePost/{postId}")
    Call<Void> deleteUnapprovedPost(@Path("postId") String postId, @Header("Authorization") String tokenKey);

    @PUT("post/approveComment/{commentId}")
    Call<Comment> updateApprovedComment(@Path("commentId") String commentId, @Header("Authorization") String tokenKey);

    @DELETE("post/unApproveComment/{commentId}")
    Call<Void> deleteUnapprovedComment(@Path("commentId") String commentId, @Header("Authorization") String tokenKey);

    @POST("business/addfollower/{businessId}/{followerId}")
    Call<Followers> addFollower(@Path("businessId") String businessId, @Path("followerId") String followerId, @Header("Authorization") String tokenKey);

}
