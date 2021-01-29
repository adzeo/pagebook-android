package com.example.pagebook.ui.fragments.business;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.pagebook.R;
import com.example.pagebook.models.Post;
import com.example.pagebook.models.PostDTO;
import com.example.pagebook.networkmanager.RetrofitBuilder;
import com.example.pagebook.ui.fragments.business.adapter.UnapprovedPostsRecyclerViewAdapter;
import com.example.pagebook.ui.fragments.business.network.IBusinessProfileApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class UnapprovedPostsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unapproved_posts);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_unapproved_posts);
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
        IBusinessProfileApi iBusinessProfileApi = retrofit.create(IBusinessProfileApi.class);
        Call<List<PostDTO>> responses = iBusinessProfileApi.getUnapprovedBusinessPosts(getIntent().getStringExtra("unapprovedPostsBusinessId"), getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE).getString("AuthToken", ""));
        responses.enqueue(new Callback<List<PostDTO>>() {
            @Override
            public void onResponse(Call<List<PostDTO>> call, retrofit2.Response<List<PostDTO>> responseData) {

                if (responseData.body() != null) {

                    List<PostDTO> unapprovedPostsList = responseData.body();

                    //fetching the id of recycler view
                    RecyclerView recyclerView = findViewById(R.id.unapproved_posts_recycle_view);
                    UnapprovedPostsRecyclerViewAdapter unapprovedPostsRecyclerViewAdapter = new UnapprovedPostsRecyclerViewAdapter(unapprovedPostsList, UnapprovedPostsActivity.this);

                    //setting Linear Layout manager in the recycler view
                    recyclerView.setLayoutManager(new LinearLayoutManager(UnapprovedPostsActivity.this));
                    recyclerView.setAdapter(unapprovedPostsRecyclerViewAdapter);
                } else {
                    Toast.makeText(UnapprovedPostsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PostDTO>> call, Throwable t) {
                Toast.makeText(UnapprovedPostsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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