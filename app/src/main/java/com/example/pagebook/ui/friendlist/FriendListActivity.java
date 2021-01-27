package com.example.pagebook.ui.friendlist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.pagebook.R;
import com.example.pagebook.models.FriendsDTO;
import com.example.pagebook.models.user.User;
import com.example.pagebook.models.user.UserBuilder;
import com.example.pagebook.networkmanager.RetrofitBuilder;
import com.example.pagebook.ui.friendlist.adapter.FriendListRecyclerViewAdapter;
import com.example.pagebook.ui.friendlist.network.IFriendListApi;
import com.example.pagebook.ui.friendsprofile.FriendsProfileActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class FriendListActivity extends AppCompatActivity implements FriendListRecyclerViewAdapter.UserDataInterface {

    User myUser = UserBuilder.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_friend_list);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            initApi();
        }
    }

    @Override
    public void onUserClick(FriendsDTO friend) {
        Intent intent = new Intent(FriendListActivity.this, FriendsProfileActivity.class);
        intent.putExtra("friendUserId", friend.getFriendId());
        startActivity(intent);
    }

    private void initApi() {
        Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
        IFriendListApi iFriendListApi = retrofit.create(IFriendListApi.class);
        Call<List<FriendsDTO>> responses = iFriendListApi.getFriendList(myUser.getId());
        responses.enqueue(new Callback<List<FriendsDTO>>() {
            @Override
            public void onResponse(Call<List<FriendsDTO>> call, retrofit2.Response<List<FriendsDTO>> responseData) {

                if (responseData.body() != null) {

                    List<FriendsDTO> friendsList = responseData.body();

                    //fetching the id of recycler view
                    RecyclerView recyclerView = findViewById(R.id.friend_list_recycle_view);
                    FriendListRecyclerViewAdapter friendListRecyclerViewAdapter = new FriendListRecyclerViewAdapter(friendsList, FriendListActivity.this);

                    //setting Linear Layout manager in the recycler view
                    recyclerView.setLayoutManager(new LinearLayoutManager(FriendListActivity.this));
                    recyclerView.setAdapter(friendListRecyclerViewAdapter);
                } else {
                    Toast.makeText(FriendListActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FriendsDTO>> call, Throwable t) {
                Toast.makeText(FriendListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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