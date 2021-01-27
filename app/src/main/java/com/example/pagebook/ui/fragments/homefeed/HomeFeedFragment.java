package com.example.pagebook.ui.fragments.homefeed;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pagebook.R;
import com.example.pagebook.models.Profile;
import com.example.pagebook.models.user.User;
import com.example.pagebook.models.user.UserBuilder;
import com.example.pagebook.networkmanager.RetrofitBuilder;
import com.example.pagebook.ui.commonlogin.network.IAppLoginApi;
import com.example.pagebook.ui.fragments.homefeed.adapter.HomeFeedRecyclerViewAdapter;
import com.example.pagebook.models.PostDTO;
import com.example.pagebook.ui.fragments.homefeed.network.IPostsApi;
import com.example.pagebook.ui.fragments.homefeed.network.IUserProfileApi;

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
        return inflater.inflate(R.layout.fragment_home_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        onButtonShowPopupWindowClick(view);

        if(myUser.getId() == null) {
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
        Call<Profile> getUserResponses = iAppLoginApi.checkUserByEmail(loggedInEmail);
        getUserResponses.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if(response.body() != null) {

                    myUser.setId(response.body().getUserId());
                    myUser.setFirstName(response.body().getFirstName());
                    myUser.setLastName(response.body().getLastName());
                    myUser.setEmail(response.body().getEmail());
                    myUser.setImgUrl(response.body().getImgUrl());
                    myUser.setInterest(response.body().getInterest());
                    myUser.setBio(response.body().getBio());
                    myUser.setProfileType(response.body().getProfileType());
                    if(response.body().getTotalFriends() == null) {
                        myUser.setTotalFriends(0);
                    }
                    else {
                        myUser.setTotalFriends(response.body().getTotalFriends());
                    }

                    initFriendsApi();
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

    private void initFriendsApi() {
        Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
        IUserProfileApi iUserProfileApi = retrofit.create(IUserProfileApi.class);

        //api call to get current User friends id lists to save in the User Singleton
        Call<List<String>> getUserFriendsIdResponses = iUserProfileApi.getMyFriendsIdList(myUser.getId());
        getUserFriendsIdResponses.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.body() != null) {
                    List<String> myFriendsList = response.body();
                    myUser.setFriendsList(myFriendsList);
                    initFollowingApi();
                    initPostsApi();
                }
                else {
                    Log.d("PageBook", "HomeFeed Current User Friends List Details Error");
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initFollowingApi() {
        Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
        IUserProfileApi iUserProfileApi = retrofit.create(IUserProfileApi.class);

        //api call to get current User business pages following id lists to save in the User Singleton
        Call<List<String>> getUserPagesFollowingIdResponses = iUserProfileApi.getMyPagesFollowingIdList(myUser.getId());
        getUserPagesFollowingIdResponses.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.body() != null) {
                    List<String> myFollowingList = response.body();
                    myUser.setPagesFollowingList(myFollowingList);
                }
                else {
                    Log.d("PageBook", "HomeFeed Current User Following List Details Error");
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initPostsApi() {
        //api call for myFeedPosts
        Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
        IPostsApi iPostsApi = retrofit.create(IPostsApi.class);

        // TODO: Implement Pagination
        Call<List<PostDTO>> responses = iPostsApi.getFeedPosts(myUser.getId(), 1);
        responses.enqueue(new Callback<List<PostDTO>>() {
            @Override
            public void onResponse (Call<List<PostDTO>> call, retrofit2.Response<List<PostDTO>> responseData) {

                if(responseData.body() != null) {

                    List<PostDTO> postsList = responseData.body();

                    //fetching the id of recycler view
                    RecyclerView recyclerView = getView().findViewById(R.id.home_feed_recycle_view);
                    HomeFeedRecyclerViewAdapter homeFeedRecyclerViewAdapter = new HomeFeedRecyclerViewAdapter(postsList, HomeFeedFragment.this);

                    //setting Linear Layout manager in the recycler view
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(homeFeedRecyclerViewAdapter);
                }
                else {
                    Toast.makeText(getContext(), "Unable To Fetch Posts", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure (Call<List<PostDTO>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onButtonShowPopupWindowClick(View view) {
        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_advertisement, null);
        // Glide.with()
        ImageView popUpAd = popupView.findViewById(R.id.iv_pop_up_ad);
        Glide.with(this).load("https://picsum.photos/200/300").placeholder(R.drawable.loading_placeholder).into(popUpAd);

        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        width = 550;

        boolean focusable = false; // lets taps outside the popup also dismiss it
        PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window token
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //popupWindow.dismiss();
                return true;
            }
        });
    }
}
