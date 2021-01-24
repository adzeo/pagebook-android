package com.example.pagebook.ui.stories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.pagebook.R;
import com.example.pagebook.networkmanager.RetrofitBuilder;
import com.example.pagebook.responsemodel.GenericResponse;
import com.example.pagebook.ui.stories.adapter.StoriesRecyclerViewAdapter;
import com.example.pagebook.ui.stories.model.Story;
import com.example.pagebook.ui.stories.model.UserStory;
import com.example.pagebook.ui.stories.network.IStoriesApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class StoriesActivity extends AppCompatActivity implements StoriesRecyclerViewAdapter.UserDataInterface {

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

        findViewById(R.id.layout_add_my_story).setOnClickListener(v -> {
            Intent intent = new Intent(this, AddStoryActivity.class);
            startActivity(intent);
        });

        initApi();
    }

    @Override
    public void onUserClick(Story story) {
        UserStory userStory = new UserStory();
        userStory.setUserStoryList(story.getFileUrl());

        Intent intent = new Intent(StoriesActivity.this, ViewUserStoryActivity.class);
        intent.putExtra("userStories", userStory);
        startActivity(intent);
    }

    private void initApi() {
        Retrofit retrofit = RetrofitBuilder.getInstance();
        IStoriesApi iStoriesApi = retrofit.create(IStoriesApi.class);
        Call<GenericResponse> responses = iStoriesApi.getStories();
        responses.enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse (Call<GenericResponse> call, retrofit2.Response<GenericResponse> storyResponseData) {

                if(storyResponseData.body().isStatus()) {

                    List<Story> storiesList = (List<Story>) storyResponseData.body().getBody();

                    //fetching the id of recycler view
                    RecyclerView recyclerView = findViewById(R.id.stories_recycle_view);
                    StoriesRecyclerViewAdapter storiesRecyclerViewAdapter = new StoriesRecyclerViewAdapter(storiesList, StoriesActivity.this);

                    //setting Linear Layout manager in the recycler view
                    recyclerView.setLayoutManager(new LinearLayoutManager(StoriesActivity.this));
                    recyclerView.setAdapter(storiesRecyclerViewAdapter);
                }
                else {
                    Toast.makeText(StoriesActivity.this, storyResponseData.body().getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure (Call<GenericResponse> call, Throwable t) {
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