package com.example.pagebook.ui.fragments.business.network;

import com.example.pagebook.models.SearchBusinesses;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ISearchBusinessApi {
    @GET("search/business/{query}")
    Call<List<SearchBusinesses>> getSearchBusiness(@Path("query") String query);
}
