package com.example.pagebook.ui.friendlist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pagebook.R;
import com.example.pagebook.models.FriendsDTO;
import com.example.pagebook.models.Story;
import com.example.pagebook.ui.friendlist.FriendListActivity;
import com.example.pagebook.ui.stories.StoriesActivity;
import com.example.pagebook.ui.stories.adapter.StoriesRecyclerViewAdapter;

import java.util.List;

public class FriendListRecyclerViewAdapter extends RecyclerView.Adapter<FriendListRecyclerViewAdapter.ViewHolder> {

    private final List<FriendsDTO> mFriendsDataList;
    private final FriendListActivity friendListActivity;

    public FriendListRecyclerViewAdapter(List<FriendsDTO> friendsDataList, FriendListActivity friendListActivity) {
        this.mFriendsDataList = friendsDataList;
        this.friendListActivity = friendListActivity;
    }

    @NonNull
    @Override
    public FriendListRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new FriendListRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendListRecyclerViewAdapter.ViewHolder holder, int position) {
        FriendsDTO friend = mFriendsDataList.get(position);

        // set user name, image and time for the post
        Glide.with(holder.ivFriendImg.getContext()).load(friend.getImgUrl()).placeholder(R.drawable.user_placeholder).into(holder.ivFriendImg);
        holder.tvFriendName.setText(friend.getFirstName());
        holder.rootView.setOnClickListener((view -> friendListActivity.onUserClick(friend)));
    }

    @Override
    public int getItemCount() {
        return mFriendsDataList == null ? 0 : mFriendsDataList.size();
    }

    public interface UserDataInterface {
        void onUserClick(FriendsDTO friend);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivFriendImg;
        private final TextView tvFriendName;
        private final View rootView;

        public ViewHolder(View view) {
            super(view);
            rootView = view;
            ivFriendImg = view.findViewById(R.id.iv_search_profile);
            tvFriendName = view.findViewById(R.id.tv_search_profile_name);
        }
    }
}