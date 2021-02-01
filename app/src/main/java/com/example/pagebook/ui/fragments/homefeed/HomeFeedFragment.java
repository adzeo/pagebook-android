package com.example.pagebook.ui.fragments.homefeed;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pagebook.R;
import com.example.pagebook.models.Profile;
import com.example.pagebook.models.user.User;
import com.example.pagebook.models.user.UserBuilder;
import com.example.pagebook.networkmanager.RetrofitBuilder;
import com.example.pagebook.ui.commonlogin.network.IAppLoginApi;
import com.example.pagebook.ui.fragments.homefeed.adapter.HomeFeedRecyclerViewAdapter;
import com.example.pagebook.models.PostDTO;
import com.example.pagebook.ui.fragments.homefeed.network.IPostsApi;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFeedFragment extends Fragment {

    User myUser = UserBuilder.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_feed, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.pullToRefreshHomeFeed);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initPostsApi();
                pullToRefresh.setRefreshing(false);
            }
        });

        if (myUser.getId() == null) {
            initProfileApi();
        }
        else {
            initPostsApi();
        }
    }

    private void initProfileApi() {
        Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));

        //api call to get current User details to save in the User Singleton
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE);
        String loggedInEmail = sharedPreferences.getString("UserEmail", "");

        IAppLoginApi iAppLoginApi = retrofit.create(IAppLoginApi.class);
        Call<Profile> getUserResponses = iAppLoginApi.checkUserByEmail(loggedInEmail, getActivity().getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE).getString("AuthToken", ""));
        getUserResponses.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.body() != null) {

                    myUser.setId(response.body().getUserId());
                    myUser.setFirstName(response.body().getFirstName());
                    myUser.setLastName(response.body().getLastName());
                    myUser.setEmail(response.body().getEmail());
                    myUser.setImgUrl(response.body().getImgUrl());
                    myUser.setInterest(response.body().getInterest());
                    myUser.setBio(response.body().getBio());
                    myUser.setProfileType(response.body().getProfileType());
                    if (response.body().getTotalFriends() == null) {
                        myUser.setTotalFriends(0);
                    } else {
                        myUser.setTotalFriends(response.body().getTotalFriends());
                    }

                    FirebaseMessaging.getInstance().subscribeToTopic(myUser.getId());

                    initPostsApi();
                }
                else {
                    Log.d("PageBook", "HomeFeed Current User Details Error");
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initPostsApi() {
        //api call for myFeedPosts
        Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
        IPostsApi iPostsApi = retrofit.create(IPostsApi.class);

        // TODO: Implement Pagination
        Call<List<PostDTO>> responses = iPostsApi.getFeedPosts(myUser.getId(), 1, getActivity().getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE).getString("AuthToken", ""));
        responses.enqueue(new Callback<List<PostDTO>>() {
            @Override
            public void onResponse(Call<List<PostDTO>> call, retrofit2.Response<List<PostDTO>> responseData) {

                if (responseData.body() != null) {

                    List<PostDTO> postsList = responseData.body();

                    //fetching the id of recycler view
                    RecyclerView recyclerView = getView().findViewById(R.id.home_feed_recycle_view);
                    HomeFeedRecyclerViewAdapter homeFeedRecyclerViewAdapter = new HomeFeedRecyclerViewAdapter(postsList, HomeFeedFragment.this);

                    //setting Linear Layout manager in the recycler view
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(homeFeedRecyclerViewAdapter);
                } else {
                    Toast.makeText(getContext(), "Unable To Fetch Posts", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PostDTO>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
