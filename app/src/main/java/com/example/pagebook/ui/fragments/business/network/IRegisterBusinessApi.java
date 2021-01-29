package com.example.pagebook.ui.fragments.business.network;

import com.example.pagebook.models.BusinessProfile;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IRegisterBusinessApi {

    @POST("business/moderator/{userId}")
    Call<BusinessProfile> saveBusiness(@Body BusinessProfile businessProfile, @Path("userId") String userId, @Header("Authorization") String tokenKey);

    @POST("search/business/")
    Call<BusinessProfile> saveBusinessInSearch(@Body BusinessProfile businessProfile, @Header("Authorization") String tokenKey);

    @PUT("business/{id}")
    Call<BusinessProfile> changeBusiness(@Body BusinessProfile businessProfile, @Path("id") String businessId, @Header("Authorization") String tokenKey);

    @PUT("search/business/{id}")
    Call<BusinessProfile> changeBusinessInSearch(@Body BusinessProfile businessProfile, @Path("id") String businessId, @Header("Authorization") String tokenKey);
}
