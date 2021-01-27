package com.example.pagebook.ui.friendrequests;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.pagebook.R;
import com.example.pagebook.models.user.User;
import com.example.pagebook.models.user.UserBuilder;
import com.example.pagebook.networkmanager.RetrofitBuilder;
import com.example.pagebook.ui.friendrequests.adapter.FriendRequestsRecyclerViewAdapter;
import com.example.pagebook.models.FriendRequests;
import com.example.pagebook.ui.friendrequests.network.IFriendRequestsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class FriendRequestsActivity extends AppCompatActivity {

    User myUser = UserBuilder.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_requests);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_friend_requests);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        initApi();
    }

    private void initApi() {
        Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
        IFriendRequestsApi iFriendRequestsApi = retrofit.create(IFriendRequestsApi.class);
        Call<List<FriendRequests>> responses = iFriendRequestsApi.getFriendRequests(myUser.getId());
        responses.enqueue(new Callback<List<FriendRequests>>() {
            @Override
            public void onResponse (Call<List<FriendRequests>> call, retrofit2.Response<List<FriendRequests>> responseData) {

                if(responseData.body() != null) {

                    List<FriendRequests> friendRequestsList = (List<FriendRequests>) responseData.body();

                    //fetching the id of recycler view
                    RecyclerView recyclerView = findViewById(R.id.friend_requests_recycle_view);
                    FriendRequestsRecyclerViewAdapter friendRequestsRecyclerViewAdapter = new FriendRequestsRecyclerViewAdapter(friendRequestsList, FriendRequestsActivity.this);

                    //setting Linear Layout manager in the recycler view
                    recyclerView.setLayoutManager(new LinearLayoutManager(FriendRequestsActivity.this));
                    recyclerView.setAdapter(friendRequestsRecyclerViewAdapter);
                }
                else {
                    Toast.makeText(FriendRequestsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure (Call<List<FriendRequests>> call, Throwable t) {
                Toast.makeText(FriendRequestsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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