package com.example.pagebook.ui.friendsprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pagebook.R;
import com.example.pagebook.models.PostDTO;
import com.example.pagebook.models.Profile;
import com.example.pagebook.models.SendFriendRequests;
import com.example.pagebook.models.user.User;
import com.example.pagebook.models.user.UserBuilder;
import com.example.pagebook.networkmanager.RetrofitBuilder;
import com.example.pagebook.ui.friendsprofile.adapter.FriendsProfilePostsRecyclerViewAdapter;
import com.example.pagebook.ui.friendsprofile.network.IFriendProfileApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class FriendsProfileActivity extends AppCompatActivity {

    User myUser = UserBuilder.getInstance();
    Boolean isFriend = false;
    String friendProfileType = "PRIVATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_profile);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_friend_profile);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Button btnFollowFriend = findViewById(R.id.btn_follow_friend);

        if (btnFollowFriend.isEnabled()) {
            btnFollowFriend.setOnClickListener(v -> {

                SendFriendRequests friendRequest = new SendFriendRequests(myUser.getId(), getIntent().getStringExtra("friendUserId"));

                Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
                IFriendProfileApi iFriendProfileApi = retrofit.create(IFriendProfileApi.class);
                Call<Void> friendProfileResponses = iFriendProfileApi.sendFriendRequest(friendRequest);
                friendProfileResponses.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, retrofit2.Response<Void> responseData) {

                        if (responseData.body() != null) {
                            Toast.makeText(FriendsProfileActivity.this, "Friend Request Sent", Toast.LENGTH_SHORT).show();
                            btnFollowFriend.setText("Request Sent");
                            btnFollowFriend.setEnabled(false);
                        } else {
                            Toast.makeText(FriendsProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(FriendsProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            });
        }

        initApi();
    }

    private void initApi() {
        ImageView friendImage = findViewById(R.id.iv_friend_dp);
        TextView friendName = findViewById(R.id.tv_friend_profile_name);
        TextView friendEmail = findViewById(R.id.tv_friend_profile_email);
        TextView friendInterests = findViewById(R.id.tv_friend_profile_interests);
        TextView friendBio = findViewById(R.id.tv_friend_profile_bio);
        Button followFriend = findViewById(R.id.btn_follow_friend);

        //api call to get Friend's Profile
        Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
        IFriendProfileApi iFriendProfileApi = retrofit.create(IFriendProfileApi.class);
        Call<Profile> friendProfileResponses = iFriendProfileApi.getFriendProfile(getIntent().getStringExtra("friendUserId"));
        friendProfileResponses.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, retrofit2.Response<Profile> responseData) {

                if (responseData.body() != null) {

                    Profile friendProfile = responseData.body();

                    Glide.with(friendImage.getContext()).load(friendProfile.getImgUrl()).placeholder(R.drawable.user_placeholder).into(friendImage);
                    friendName.setText(friendProfile.getFirstName() + " " + friendProfile.getLastName());
                    friendEmail.setText(friendProfile.getEmail());
                    friendInterests.setText(friendProfile.getInterest());
                    friendBio.setText(friendProfile.getBio());

                    friendProfileType = friendProfile.getProfileType();
                    isFriend = myUser.getFriendsList().contains(friendProfile.getUserId());
                    if (isFriend) {
                        followFriend.setText("Following");
                        followFriend.setEnabled(false);
                    }

                    if (isFriend || friendProfileType.equals("PUBLIC")) {
                        initFriendsPostApi();
                    }

                } else {
                    Toast.makeText(FriendsProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                Toast.makeText(FriendsProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initFriendsPostApi() {
        //api call to get friend's post based on the profile type
        Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
        IFriendProfileApi iFriendPostsApi = retrofit.create(IFriendProfileApi.class);
        Call<List<PostDTO>> friendPostsResponse = iFriendPostsApi.getFriendsPosts(getIntent().getStringExtra("friendUserId"));
        friendPostsResponse.enqueue(new Callback<List<PostDTO>>() {
            @Override
            public void onResponse(Call<List<PostDTO>> call, retrofit2.Response<List<PostDTO>> responseData) {

                if (responseData.body() != null) {

                    List<PostDTO> friendsPostsList = responseData.body();

                    //fetching the id of recycler view
                    RecyclerView recyclerView = findViewById(R.id.friend_posts_recycle_view);
                    FriendsProfilePostsRecyclerViewAdapter friendsProfilePostsRecyclerViewAdapter = new FriendsProfilePostsRecyclerViewAdapter(friendsPostsList, FriendsProfileActivity.this);

                    //setting Linear Layout manager in the recycler view
                    recyclerView.setLayoutManager(new LinearLayoutManager(FriendsProfileActivity.this));
                    recyclerView.setAdapter(friendsProfilePostsRecyclerViewAdapter);
                } else {
                    Toast.makeText(FriendsProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PostDTO>> call, Throwable t) {
                Toast.makeText(FriendsProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}