package com.example.pagebook.ui.friendsprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pagebook.R;
import com.example.pagebook.models.PostDTO;
import com.example.pagebook.models.Profile;
import com.example.pagebook.models.PushNotificationRequest;
import com.example.pagebook.models.PushNotificationResponse;
import com.example.pagebook.models.SendFriendRequests;
import com.example.pagebook.models.user.User;
import com.example.pagebook.models.user.UserBuilder;
import com.example.pagebook.network.IPushNotificationApi;
import com.example.pagebook.networkmanager.MainRetrofitBuilder;
import com.example.pagebook.networkmanager.NotificationRetrofitBuilder;
import com.example.pagebook.networkmanager.RetrofitBuilder;
import com.example.pagebook.ui.fragments.homefeed.network.IUserProfileApi;
import com.example.pagebook.ui.friendsprofile.adapter.FriendsProfilePostsRecyclerViewAdapter;
import com.example.pagebook.ui.friendsprofile.network.IFriendProfileApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FriendsProfileActivity extends AppCompatActivity {

    User myUser = UserBuilder.getInstance();
    Boolean isFriend = false;
    String friendProfileType = "PRIVATE";

    ImageView friendImage;
    TextView friendName;
    TextView friendEmail;
    TextView friendInterests;
    TextView friendBio;
    Button btnFollowFriend;

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

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefreshFriendPage);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initApi();
                pullToRefresh.setRefreshing(false);
            }
        });

        friendImage = findViewById(R.id.iv_friend_dp);
        friendName = findViewById(R.id.tv_friend_profile_name);
        friendEmail = findViewById(R.id.tv_friend_profile_email);
        friendInterests = findViewById(R.id.tv_friend_profile_interests);
        friendBio = findViewById(R.id.tv_friend_profile_bio);
        btnFollowFriend = findViewById(R.id.btn_follow_friend);

        if (btnFollowFriend.isEnabled()) {
            btnFollowFriend.setOnClickListener(v -> {
                SendFriendRequests friendRequest = new SendFriendRequests(myUser.getId(), getIntent().getStringExtra("friendUserId"));

                Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
                IFriendProfileApi iFriendProfileApi = retrofit.create(IFriendProfileApi.class);
                Call<SendFriendRequests> friendProfileResponses = iFriendProfileApi.sendFriendRequest(friendRequest, getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE).getString("AuthToken", ""));
                friendProfileResponses.enqueue(new Callback<SendFriendRequests>() {
                    @Override
                    public void onResponse(Call<SendFriendRequests> call, retrofit2.Response<SendFriendRequests> responseData) {

                        if (responseData.body() != null) {
                            Toast.makeText(FriendsProfileActivity.this, "Friend Request Sent", Toast.LENGTH_SHORT).show();
                            btnFollowFriend.setText("Request Sent");
                            btnFollowFriend.setEnabled(false);
                            initNotifApi();
                        } else {
                            Toast.makeText(FriendsProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<SendFriendRequests> call, Throwable t) {
                        Toast.makeText(FriendsProfileActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            });
        }
        initApi();
    }

    private void initNotifApi() {
        PushNotificationRequest notificationRequest=new PushNotificationRequest();
        notificationRequest.setTitle("PageBook");
        notificationRequest.setMessage(myUser.getFirstName()+" sent you a Friend Request");
//        notificationRequest.setTopic("batman");
        notificationRequest.setTopic(getIntent().getStringExtra("friendUserId"));
        notificationRequest.setToken(getSharedPreferences("com.example.pagebook",Context.MODE_PRIVATE).getString("FCMToken",""));
        Retrofit retrofit = NotificationRetrofitBuilder.getInstance();
        IPushNotificationApi iPushNotificationApi = retrofit.create(IPushNotificationApi.class);
        Map<String,String> data=new HashMap<>();
        notificationRequest.setData(data);
        Call<PushNotificationResponse> responseCall=iPushNotificationApi.sendNotification(notificationRequest,getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE).getString("AuthToken", ""));
        responseCall.enqueue(new Callback<PushNotificationResponse>() {
            @Override
            public void onResponse(Call<PushNotificationResponse> call, Response<PushNotificationResponse> response) {
                if(response.body()!=null)
                {
                    Log.d("Notification Response",response.body().getMessage().toString());
                }
            }

            @Override
            public void onFailure(Call<PushNotificationResponse> call, Throwable t) {

                Log.d("Notification Response", t.getMessage());
            }
        });
    }

    private void initApi() {
        //api call to get Friend's Profile
        Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
        IFriendProfileApi iFriendProfileApi = retrofit.create(IFriendProfileApi.class);
        Call<Profile> friendProfileResponses = iFriendProfileApi.getFriendProfile(getIntent().getStringExtra("friendUserId"), getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE).getString("AuthToken", ""));
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

                    initFriendsApi();

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

    private void initFriendsApi() {
        Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
        IUserProfileApi iUserProfileApi = retrofit.create(IUserProfileApi.class);

        //api call to get current User friends id lists to save in the User Singleton
        Call<List<String>> getUserFriendsIdResponses = iUserProfileApi.getMyFriendsIdList(myUser.getId(), getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE).getString("AuthToken", ""));
        getUserFriendsIdResponses.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.body() != null) {
                    List<String> myFriendsList = response.body();

                    isFriend = myFriendsList.contains(getIntent().getStringExtra("friendUserId"));
                    if (isFriend) {
                        btnFollowFriend.setText("Following");
                        btnFollowFriend.setEnabled(false);
                    }

                    if (isFriend || friendProfileType.equalsIgnoreCase("PUBLIC")) {
                        initFriendsPostApi();
                    }

                } else {
                    Log.d("PageBook", "HomeFeed Current User Friends List Details Error");
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(FriendsProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initFriendsPostApi() {
        //api call to get friend's post based on the profile type
        Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
        IFriendProfileApi iFriendPostsApi = retrofit.create(IFriendProfileApi.class);
        Call<List<PostDTO>> friendPostsResponse = iFriendPostsApi.getFriendsPosts(getIntent().getStringExtra("friendUserId"), getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE).getString("AuthToken", ""));
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