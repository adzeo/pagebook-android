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
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IBusinessProfileApi {
    @GET("business/{businessId}")
    Call<BusinessProfile> getBusinessProfile(@Path("businessId") String businessId);

    @GET("business/following/details/{userId}")
    Call<List<BusinessDTO>> getBusinessPagesFollowingList(@Path("userId") String userId);

    @GET("business/moderators/{businessId}")
    Call<Moderators> getBusinessModeratorsList(@Path("businessId") String businessId);

    @POST("business//addmoderator/{businessId}/{moderatorId}")
    Call<Moderators> addModerator(@Path("businessId") String businessId, @Path("moderatorId") String moderatorId);

    @GET("post/getBusinessPost/{businessId}")
    Call<List<PostDTO>> getBusinessPosts(@Path("businessId") String businessId);

    @GET("post/getUnapprovedPost/{businessId}")
    Call<List<PostDTO>> getUnapprovedBusinessPosts(@Path("businessId") String businessId);

    @PUT("post/approvePost/{postId}")
    Call<Post> updateApprovedPost(@Path("postId") String postId);

    @DELETE("post/unApprovePost/{postId}")
    Call<Void> deleteUnapprovedPost(@Path("postId") String postId);

    @PUT("post/approveComment/{commentId}")
    Call<Comment> updateApprovedComment(@Path("commentId") String commentId);

    @DELETE("post/unApproveComment/{commentId}")
    Call<Void> deleteUnapprovedComment(@Path("commentId") String commentId);

    @POST("business/addfollower/{businessId}/{followerId}")
    Call<Followers> addFollower(@Path("businessId") String businessId, @Path("followerId") String followerId);

}
