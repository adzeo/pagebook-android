package com.example.pagebook.ui.fragments.homefeed.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pagebook.R;
import com.example.pagebook.ui.fragments.homefeed.HomeFeedFragment;
import com.example.pagebook.generalmodels.PostDTO;

import java.util.List;

public class HomeFeedRecyclerViewAdapter extends RecyclerView.Adapter<HomeFeedRecyclerViewAdapter.ViewHolder> {

    private final List<PostDTO> mPostsDataList;
    private final HomeFeedFragment homeFeedFragment;

    public HomeFeedRecyclerViewAdapter(List<PostDTO> userDataList, HomeFeedFragment homeFeedFragment) {
        this.mPostsDataList = userDataList;
        this.homeFeedFragment = homeFeedFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostDTO post = mPostsDataList.get(position);

        // set user name, image and time for the post
        Glide.with(holder.ivUser.getContext()).load(post.getUserImgURL()).placeholder(R.drawable.user_placeholder).into(holder.ivUser);
        holder.tvUserName.setText(post.getUserName());
        holder.tvPostDate.setText(post.getPost().getUploadTime().toString());

        // set the post content
        if(post.getPost().getFileType().equals("Text")) {
            holder.tvPostText.setText(post.getPost().getFileUrl());
        }
        else if (post.getPost().getFileType().equals("Image")){
            Glide.with(holder.ivPostImage.getContext()).load(post.getPost().getFileUrl()).placeholder(R.drawable.loading_placeholder).into(holder.ivPostImage);
        }

        // set the post likes, dislikes, emojis and comments count
        holder.tvHappy.setText(post.getTotalHappyEmoji());
        holder.tvSad.setText(post.getTotalSadEmoji());
        holder.tvLikes.setText(post.getTotalLikes());
        holder.tvDislikes.setText(post.getTotalDislikes());
        holder.tvComments.setText(post.getTotalComments());
    }

    @Override
    public int getItemCount() {
        return mPostsDataList == null ? 0 : mPostsDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivUser;
        private final TextView tvUserName;
        private final TextView tvPostDate;
        private final TextView tvPostText;
        private final ImageView ivPostImage;
        private final TextView tvHappy;
        private final TextView tvSad;
        private final TextView tvLikes;
        private final TextView tvDislikes;
        private final TextView tvComments;
        private final View rootView;

        public ViewHolder(View view) {
            super(view);
            rootView = view;
            ivUser = view.findViewById(R.id.iv_post_user);
            tvUserName = view.findViewById(R.id.tv_post_user_name);
            tvPostDate = view.findViewById(R.id.tv_post_time);
            tvPostText = view.findViewById(R.id.tv_post_data);
            ivPostImage = view.findViewById(R.id.iv_post_data);
            tvHappy = view.findViewById(R.id.tv_happy_emoji);
            tvSad = view.findViewById(R.id.tv_sad_emoji);
            tvLikes = view.findViewById(R.id.tv_like);
            tvDislikes = view.findViewById(R.id.tv_dislike);
            tvComments = view.findViewById(R.id.tv_comments);
        }
    }
}