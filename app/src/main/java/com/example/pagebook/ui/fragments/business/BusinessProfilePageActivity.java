package com.example.pagebook.ui.fragments.business;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pagebook.R;
import com.example.pagebook.models.BusinessProfile;
import com.example.pagebook.models.Followers;
import com.example.pagebook.models.Moderators;
import com.example.pagebook.models.PostDTO;
import com.example.pagebook.models.user.User;
import com.example.pagebook.models.user.UserBuilder;
import com.example.pagebook.networkmanager.RetrofitBuilder;
import com.example.pagebook.ui.addposts.AddPostActivity;
import com.example.pagebook.ui.fragments.business.adapter.BusinessPostsRecyclerViewAdapter;
import com.example.pagebook.ui.fragments.business.network.IBusinessProfileApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class BusinessProfilePageActivity extends AppCompatActivity {

    User myUser = UserBuilder.getInstance();
    BusinessProfile businessProfile = new BusinessProfile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_profile_page);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_business_profile);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        initBusinessModeratorsApi();

        findViewById(R.id.btn_business_add_posts).setOnClickListener(v -> {
            Intent intent = new Intent(BusinessProfilePageActivity.this, AddPostActivity.class);
            intent.putExtra("business","true");
            intent.putExtra("businessId", getIntent().getStringExtra("businessProfileId"));
            startActivity(intent);
        });

        findViewById(R.id.btn_edit_business_profile).setOnClickListener(v -> {
            Intent intent = new Intent(BusinessProfilePageActivity.this, EditBusinessPageActivity.class);
            intent.putExtra("editBusinessPageRecord", businessProfile);
            startActivity(intent);
        });

        findViewById(R.id.btn_unapproved_posts).setOnClickListener(v -> {
            Intent intent = new Intent(BusinessProfilePageActivity.this, UnapprovedPostsActivity.class);
            intent.putExtra("unapprovedPostsBusinessId", getIntent().getStringExtra("businessProfileId"));
            startActivity(intent);
        });

        findViewById(R.id.btn_add_moderator).setOnClickListener(v -> {
            Intent intent = new Intent(BusinessProfilePageActivity.this, AddModeratorActivity.class);
            intent.putExtra("businessId", getIntent().getStringExtra("businessProfileId"));
            startActivity(intent);
        });

        Button btnFollowBusiness = findViewById(R.id.btn_follow_business);

        if (btnFollowBusiness.isEnabled()) {
            btnFollowBusiness.setOnClickListener(v -> {
                Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
                IBusinessProfileApi iBusinessProfileApi = retrofit.create(IBusinessProfileApi.class);
                Call<Followers> friendProfileResponses = iBusinessProfileApi.addFollower(getIntent().getStringExtra("businessProfileId"), myUser.getId());
                friendProfileResponses.enqueue(new Callback<Followers>() {
                    @Override
                    public void onResponse(Call<Followers> call, retrofit2.Response<Followers> responseData) {

                        if (responseData.body() != null) {
                            Toast.makeText(BusinessProfilePageActivity.this, "Following Page", Toast.LENGTH_SHORT).show();
                            btnFollowBusiness.setText("Following");
                            btnFollowBusiness.setEnabled(false);
                        }
                        else {
                            Toast.makeText(BusinessProfilePageActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Followers> call, Throwable t) {
                        Toast.makeText(BusinessProfilePageActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            });
        }

        //TODO: api call to add moderator

    }

    private void initBusinessModeratorsApi() {
        Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
        IBusinessProfileApi iBusinessProfileApi = retrofit.create(IBusinessProfileApi.class);

        Call<Moderators> businessProfileResponses = iBusinessProfileApi.getBusinessModeratorsList(getIntent().getStringExtra("businessProfileId"));
        businessProfileResponses.enqueue(new Callback<Moderators>() {
            @Override
            public void onResponse(Call<Moderators> call, retrofit2.Response<Moderators> responseData) {

                if (responseData.body() != null) {

                    Moderators moderators = responseData.body();
                    if(!moderators.getModerators().contains(myUser.getId())) {
                        findViewById(R.id.ll_moderator_options).setVisibility(View.GONE);
                    }

                    initBusinessProfileApi();
                }
                else {
                    Toast.makeText(BusinessProfilePageActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Moderators> call, Throwable t) {
                Toast.makeText(BusinessProfilePageActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initBusinessProfileApi() {
        ImageView businessImage = findViewById(R.id.iv_business_dp);
        TextView businessName = findViewById(R.id.tv_business_profile_name);
        TextView businessEmail = findViewById(R.id.tv_business_profile_email);
        TextView businessCategory = findViewById(R.id.tv_business_profile_category);
        TextView businessDesc = findViewById(R.id.tv_business_profile_desc);
        Button followBusiness = findViewById(R.id.btn_follow_business);

        Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
        IBusinessProfileApi iBusinessProfileApi = retrofit.create(IBusinessProfileApi.class);
        Call<BusinessProfile> businessProfileResponses = iBusinessProfileApi.getBusinessProfile(getIntent().getStringExtra("businessProfileId"));
        businessProfileResponses.enqueue(new Callback<BusinessProfile>() {
            @Override
            public void onResponse(Call<BusinessProfile> call, retrofit2.Response<BusinessProfile> responseData) {

                if (responseData.body() != null) {

                    businessProfile = responseData.body();

                    Glide.with(businessImage.getContext()).load(businessProfile.getImageUrl()).placeholder(R.drawable.user_placeholder).into(businessImage);
                    businessName.setText(businessProfile.getBusinessName());
                    businessEmail.setText(businessProfile.getAdminEmail());
                    businessCategory.setText(businessProfile.getCategory());
                    businessDesc.setText(businessProfile.getDescription());

                    if (myUser.getPagesFollowingList().contains(businessProfile.getId())) {
                        followBusiness.setText("Following");
                        followBusiness.setEnabled(false);
                    }

                    initBusinessPostsApi();
                }
                else {
                    Toast.makeText(BusinessProfilePageActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BusinessProfile> call, Throwable t) {
                Toast.makeText(BusinessProfilePageActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initBusinessPostsApi() {
        Retrofit retrofit = RetrofitBuilder.getInstance(getString(R.string.baseUrl));
        IBusinessProfileApi iBusinessProfileApi = retrofit.create(IBusinessProfileApi.class);
        Call<List<PostDTO>> businessPostsResponses = iBusinessProfileApi.getBusinessPosts(getIntent().getStringExtra("businessProfileId"));
        businessPostsResponses.enqueue(new Callback<List<PostDTO>>() {
            @Override
            public void onResponse(Call<List<PostDTO>> call, retrofit2.Response<List<PostDTO>> responseData) {

                if (responseData.body() != null) {

                    List<PostDTO> businessPostsList = responseData.body();

                    //fetching the id of recycler view
                    RecyclerView recyclerView = findViewById(R.id.business_posts_recycle_view);
                    BusinessPostsRecyclerViewAdapter businessPostsRecyclerViewAdapter = new BusinessPostsRecyclerViewAdapter(businessPostsList, BusinessProfilePageActivity.this);

                    //setting Linear Layout manager in the recycler view
                    recyclerView.setLayoutManager(new LinearLayoutManager(BusinessProfilePageActivity.this));
                    recyclerView.setAdapter(businessPostsRecyclerViewAdapter);
                } else {
                    Toast.makeText(BusinessProfilePageActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PostDTO>> call, Throwable t) {
                Toast.makeText(BusinessProfilePageActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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