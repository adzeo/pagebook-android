package com.example.pagebook.ui.fragments.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pagebook.R;
import com.example.pagebook.models.PostDTO;
import com.example.pagebook.models.Profile;
import com.example.pagebook.networkmanager.RetrofitBuilder;
import com.example.pagebook.ui.addposts.AddPostActivity;
import com.example.pagebook.ui.commonlogin.network.IAppLoginApi;
import com.example.pagebook.ui.fragments.homefeed.network.IPostsApi;
import com.example.pagebook.ui.fragments.profile.adapter.ProfileFeedRecyclerViewAdapter;
import com.example.pagebook.ui.friendlist.FriendListActivity;
import com.example.pagebook.models.user.User;
import com.example.pagebook.models.user.UserBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileFragment extends Fragment {

    User myUser = UserBuilder.getInstance();

    ImageView myUserImage;
    TextView myUserName;
    TextView myUserEmail;
    TextView myInterests;
    TextView myBio;
    TextView myTotalFriends;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myUserImage = getView().findViewById(R.id.iv_profile_user);
        myUserName = getView().findViewById(R.id.tv_profile_user_name);
        myUserEmail = getView().findViewById(R.id.tv_profile_email);
        myInterests = getView().findViewById(R.id.tv_profile_interests);
        myBio = getView().findViewById(R.id.tv_profile_bio);
        myTotalFriends = getView().findViewById(R.id.tv_total_friends);

        view.findViewById(R.id.btn_friend_list).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), FriendListActivity.class);
            startActivity(intent);
        });

        view.findViewById(R.id.btn_edit_profile).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), EditProfilePageActivity.class);
            startActivity(intent);
        });

        view.findViewById(R.id.btn_my_add_posts).setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddPostActivity.class);
            startActivity(intent);
        });

        initProfileApi();
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

                    //setting my profile details
                    Glide.with(myUserImage.getContext()).load(myUser.getImgUrl()).placeholder(R.drawable.user_placeholder).into(myUserImage);
                    myUserName.setText(myUser.getFirstName() + " " + myUser.getLastName());
                    myUserEmail.setText(myUser.getEmail());
                    myInterests.setText(myUser.getInterest());
                    myBio.setText(myUser.getBio());
                    myTotalFriends.setText(String.valueOf(myUser.getTotalFriends()));

                    //calling the posts api for the current profile
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
        Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
        IPostsApi iPostsApi = retrofit.create(IPostsApi.class);

        // TODO: Implement Pagination
        Call<List<PostDTO>> responses = iPostsApi.getUsersPosts(myUser.getId());
        responses.enqueue(new Callback<List<PostDTO>>() {
            @Override
            public void onResponse (Call<List<PostDTO>> call, retrofit2.Response<List<PostDTO>> responseData) {

                if(responseData.body() != null) {

                    List<PostDTO> myPostsList = responseData.body();

                    //fetching the id of recycler view
                    RecyclerView recyclerView = getView().findViewById(R.id.profile_posts_recycle_view);
                    ProfileFeedRecyclerViewAdapter profileFeedRecyclerViewAdapter = new ProfileFeedRecyclerViewAdapter(myPostsList, ProfileFragment.this);

                    //setting Linear Layout manager in the recycler view
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(profileFeedRecyclerViewAdapter);
                }
                else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure (Call<List<PostDTO>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}