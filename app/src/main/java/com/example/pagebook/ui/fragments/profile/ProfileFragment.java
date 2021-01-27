package com.example.pagebook.ui.fragments.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pagebook.R;
import com.example.pagebook.models.PostDTO;
import com.example.pagebook.networkmanager.RetrofitBuilder;
import com.example.pagebook.ui.addposts.AddPostActivity;
import com.example.pagebook.ui.fragments.homefeed.network.IPostsApi;
import com.example.pagebook.ui.fragments.profile.adapter.ProfileFeedRecyclerViewAdapter;
import com.example.pagebook.ui.friendlist.FriendListActivity;
import com.example.pagebook.models.user.User;
import com.example.pagebook.models.user.UserBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class ProfileFragment extends Fragment {

    User myUser = UserBuilder.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setting my profile details
        ImageView myUserImage = view.findViewById(R.id.iv_profile_user);
        TextView myUserName = view.findViewById(R.id.tv_profile_user_name);
        TextView myUserEmail = view.findViewById(R.id.tv_profile_email);
        TextView myInterests = view.findViewById(R.id.tv_profile_interests);
        TextView myBio = view.findViewById(R.id.tv_profile_bio);
        TextView myTotalFriends = view.findViewById(R.id.tv_total_friends);

        Glide.with(myUserImage.getContext()).load(myUser.getImgUrl()).placeholder(R.drawable.user_placeholder).into(myUserImage);
        myUserName.setText(myUser.getFirstName() + " " + myUser.getLastName());
        myUserEmail.setText(myUser.getEmail());
        myInterests.setText(myUser.getInterest());
        myBio.setText(myUser.getBio());
        myTotalFriends.setText(String.valueOf(myUser.getTotalFriends()));

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

        initApi();
    }

    private void initApi() {
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