package com.example.pagebook.ui.fragments.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pagebook.R;
import com.example.pagebook.networkmanager.RetrofitBuilder;
import com.example.pagebook.ui.fragments.profile.ProfileFragment;
import com.example.pagebook.ui.fragments.search.adapter.SearchFriendsRecyclerViewAdapter;
import com.example.pagebook.models.SearchFriends;
import com.example.pagebook.ui.fragments.search.network.ISearchFriendApi;
import com.example.pagebook.ui.friendsprofile.FriendsProfileActivity;
import com.example.pagebook.models.user.UserBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class SearchResultFragment extends Fragment implements SearchFriendsRecyclerViewAdapter.UserDataInterface {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        String searchFriendQuery = "";
        if(bundle != null) {
            searchFriendQuery = bundle.getString("searchFriendQuery");
        }
        initApi(searchFriendQuery);
    }

    @Override
    public void onUserClick(SearchFriends searchFriends) {
        if(searchFriends.getId().equals(UserBuilder.getInstance().getId())) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new ProfileFragment())
                    .commit();
        }
        else {
            Intent intent = new Intent(getContext(), FriendsProfileActivity.class);
            intent.putExtra("friendUserId", searchFriends.getId());
            startActivity(intent);
        }
    }

    private void initApi(String searchFriendQuery) {
        Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
        ISearchFriendApi iSearchFriendApi = retrofit.create(ISearchFriendApi.class);
        Call<List<SearchFriends>> responses = iSearchFriendApi.getSearchFriends(searchFriendQuery, getActivity().getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE).getString("AuthToken", ""));
        responses.enqueue(new Callback<List<SearchFriends>>() {
            @Override
            public void onResponse (Call<List<SearchFriends>> call, retrofit2.Response<List<SearchFriends>> responseData) {

                if(responseData.body() != null) {

                    List<SearchFriends> searchFriendsList = responseData.body();

                    //fetching the id of recycler view
                    RecyclerView recyclerView = getView().findViewById(R.id.search_result_recycle_view);
                    SearchFriendsRecyclerViewAdapter searchFriendsRecyclerViewAdapter = new SearchFriendsRecyclerViewAdapter(searchFriendsList, SearchResultFragment.this);

                    //setting Linear Layout manager in the recycler view
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(searchFriendsRecyclerViewAdapter);
                    searchFriendsRecyclerViewAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure (Call<List<SearchFriends>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}