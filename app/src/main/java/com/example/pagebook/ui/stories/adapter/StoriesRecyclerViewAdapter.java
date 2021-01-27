package com.example.pagebook.ui.stories.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pagebook.R;
import com.example.pagebook.ui.stories.StoriesActivity;
import com.example.pagebook.models.Story;

import java.util.List;

public class StoriesRecyclerViewAdapter extends RecyclerView.Adapter<StoriesRecyclerViewAdapter.ViewHolder> {

    private final List<Story> mStoriesDataList;
    private final StoriesActivity storiesActivity;

    public StoriesRecyclerViewAdapter(List<Story> storiesDataList, StoriesActivity storiesActivity) {
        this.mStoriesDataList = storiesDataList;
        this.storiesActivity = storiesActivity;
    }

    @NonNull
    @Override
    public StoriesRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.story_item, parent, false);
        return new StoriesRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoriesRecyclerViewAdapter.ViewHolder holder, int position) {
        Story story = mStoriesDataList.get(position);

        // set user name, image and time for the post
        Glide.with(holder.ivStoryUser.getContext()).load(story.getUserImageUrl()).placeholder(R.drawable.user_placeholder).into(holder.ivStoryUser);
        holder.tvStoryUserName.setText(story.getUserName());
        holder.rootView.setOnClickListener((view -> storiesActivity.onUserClick(story)));
    }

    @Override
    public int getItemCount() {
        return mStoriesDataList == null ? 0 : mStoriesDataList.size();
    }

    public interface UserDataInterface {
        void onUserClick(Story story);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivStoryUser;
        private final TextView tvStoryUserName;
        private final View rootView;

        public ViewHolder(View view) {
            super(view);
            rootView = view;
            ivStoryUser = view.findViewById(R.id.iv_feed_story);
            tvStoryUserName = view.findViewById(R.id.tv_story_user_name);
        }
    }
}