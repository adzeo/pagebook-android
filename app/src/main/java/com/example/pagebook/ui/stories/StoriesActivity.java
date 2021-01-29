package com.example.pagebook.ui.stories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pagebook.R;
import com.example.pagebook.models.StoryFeedDTO;
import com.example.pagebook.models.user.User;
import com.example.pagebook.models.user.UserBuilder;
import com.example.pagebook.networkmanager.RetrofitBuilder;
import com.example.pagebook.ui.stories.adapter.StoriesRecyclerViewAdapter;
import com.example.pagebook.models.Story;
import com.example.pagebook.ui.stories.network.IStoriesApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class StoriesActivity extends AppCompatActivity implements StoriesRecyclerViewAdapter.UserDataInterface {

    User myUser = UserBuilder.getInstance();

    ImageView ivMyUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_stories);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ivMyUser = findViewById(R.id.iv_my_story);

        findViewById(R.id.layout_add_my_story).setOnClickListener(v -> {
            Intent intent = new Intent(this, AddStoryActivity.class);
            startActivity(intent);
        });

        Glide.with(ivMyUser.getContext()).load(myUser.getImgUrl()).placeholder(R.drawable.user_placeholder).into(ivMyUser);

        initApi();
    }

    @Override
    public void onUserClick(Story story) {
        Intent intent = new Intent(StoriesActivity.this, ViewUserStoryActivity.class);
        intent.putExtra("userStory", story.getFileUrl());
        startActivity(intent);
    }

    private void initApi() {
        Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
        IStoriesApi iStoriesApi = retrofit.create(IStoriesApi.class);
        Call<StoryFeedDTO> responses = iStoriesApi.getStories(myUser.getId(), getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE).getString("AuthToken", ""));
        responses.enqueue(new Callback<StoryFeedDTO>() {
            @Override
            public void onResponse (Call<StoryFeedDTO> call, retrofit2.Response<StoryFeedDTO> responseData) {

                if(responseData.body() != null) {

                    List<Story> storiesList = responseData.body().getStories();

                    //fetching the id of recycler view
                    RecyclerView recyclerView = findViewById(R.id.stories_recycle_view);
                    StoriesRecyclerViewAdapter storiesRecyclerViewAdapter = new StoriesRecyclerViewAdapter(storiesList, StoriesActivity.this);

                    //setting Linear Layout manager in the recycler view
                    recyclerView.setLayoutManager(new LinearLayoutManager(StoriesActivity.this));
                    recyclerView.setAdapter(storiesRecyclerViewAdapter);
                }
                else {
                    Toast.makeText(StoriesActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure (Call<StoryFeedDTO> call, Throwable t) {
                Toast.makeText(StoriesActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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