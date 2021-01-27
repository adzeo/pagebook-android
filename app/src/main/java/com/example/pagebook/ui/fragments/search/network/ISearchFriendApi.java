package com.example.pagebook.ui.fragments.search.network;

import com.example.pagebook.models.SearchFriends;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ISearchFriendApi {
    @GET("search/user/{query}")
    Call<List<SearchFriends>> getSearchFriends(@Path("query") String query);
}
