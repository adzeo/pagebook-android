package com.example.pagebook.ui.fragments.profile.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pagebook.R;
import com.example.pagebook.models.PostAction;
import com.example.pagebook.models.PostDTO;
import com.example.pagebook.models.user.User;
import com.example.pagebook.models.user.UserBuilder;
import com.example.pagebook.networkmanager.RetrofitBuilder;
import com.example.pagebook.ui.comments.CommentsActivity;
import com.example.pagebook.ui.fragments.homefeed.network.IPostsApi;
import com.example.pagebook.ui.fragments.profile.ProfileFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class ProfileFeedRecyclerViewAdapter extends RecyclerView.Adapter<ProfileFeedRecyclerViewAdapter.ViewHolder> {

    private final List<PostDTO> mPostsDataList;
    private final ProfileFragment profileFragment;

    User myUser = UserBuilder.getInstance();

    public ProfileFeedRecyclerViewAdapter(List<PostDTO> userDataList, ProfileFragment profileFragment) {
        this.mPostsDataList = userDataList;
        this.profileFragment = profileFragment;
    }

    @NonNull
    @Override
    public ProfileFeedRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        return new ProfileFeedRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileFeedRecyclerViewAdapter.ViewHolder holder, int position) {
        PostDTO post = mPostsDataList.get(position);

        // set user name, image and time for the post
        Glide.with(holder.ivUser.getContext()).load(post.getUserImgURL()).placeholder(R.drawable.user_placeholder).into(holder.ivUser);
        holder.tvUserName.setText(post.getUserName());
        holder.tvPostDate.setText(post.getPost().getUploadTime().toString());

        // set the post content
        if(post.getPost().getFileType().equals("TEXT")) {
            holder.tvPostText.setVisibility(View.VISIBLE);
            holder.tvPostText.setText(post.getPost().getFileURL());
            holder.ivPostImage.setVisibility(View.GONE);
        }
        else if (post.getPost().getFileType().equals("IMAGE")){
            holder.ivPostImage.setVisibility(View.VISIBLE);
            Glide.with(holder.ivPostImage.getContext()).load(post.getPost().getFileURL()).placeholder(R.drawable.loading_placeholder).into(holder.ivPostImage);
            holder.tvPostText.setVisibility(View.GONE);
        }

        switch (post.getPerformedAction()) {
            case 1:
                holder.ivLikes.setImageResource(R.drawable.ic_like);

                holder.llLikes.setEnabled(false);
                holder.llDislikes.setEnabled(false);
                holder.llHappyEmoji.setEnabled(false);
                holder.llSadEmoji.setEnabled(false);
                break;
            case 2:
                holder.ivDislikes.setImageResource(R.drawable.ic_dislike);

                holder.llLikes.setEnabled(false);
                holder.llDislikes.setEnabled(false);
                holder.llHappyEmoji.setEnabled(false);
                holder.llSadEmoji.setEnabled(false);
                break;
        }

        // set the post likes, dislikes, emojis and comments count
        holder.tvHappy.setText(String.valueOf(post.getTotalHappyEmoji()));
        holder.tvSad.setText(String.valueOf(post.getTotalSadEmoji()));
        holder.tvLikes.setText(String.valueOf(post.getTotalLikes()));
        holder.tvDislikes.setText(String.valueOf(post.getTotalDislikes()));
        holder.tvComments.setText(String.valueOf(post.getTotalComments()));

        PostAction postAction = new PostAction();
        postAction.setPostId(post.getPost().getPostId());
        postAction.setUserId(myUser.getId());
        postAction.setActionType(0);

        //TODO: call post api for action
        holder.llLikes.setOnClickListener(v -> {
            postAction.setActionType(1);
            holder.ivLikes.setImageResource(R.drawable.ic_like);
            holder.tvLikes.setText(String.valueOf(Integer.parseInt(holder.tvLikes.getText().toString()) + 1));

            holder.llLikes.setEnabled(false);
            holder.llDislikes.setEnabled(false);
            holder.llHappyEmoji.setEnabled(false);
            holder.llSadEmoji.setEnabled(false);

            initSetActionApi(postAction, holder.rootView);

        });

        holder.llDislikes.setOnClickListener(v -> {
            postAction.setActionType(2);
            holder.ivDislikes.setImageResource(R.drawable.ic_dislike);
            holder.tvDislikes.setText(String.valueOf(Integer.parseInt(holder.tvDislikes.getText().toString()) + 1));

            holder.llLikes.setEnabled(false);
            holder.llDislikes.setEnabled(false);
            holder.llHappyEmoji.setEnabled(false);
            holder.llSadEmoji.setEnabled(false);

            initSetActionApi(postAction, holder.rootView);
        });

        holder.llHappyEmoji.setOnClickListener(v -> {
            postAction.setActionType(3);
            holder.tvHappy.setText(String.valueOf(Integer.parseInt(holder.tvHappy.getText().toString()) + 1));

            holder.llLikes.setEnabled(false);
            holder.llDislikes.setEnabled(false);
            holder.llHappyEmoji.setEnabled(false);
            holder.llSadEmoji.setEnabled(false);

            initSetActionApi(postAction, holder.rootView);
        });

        holder.llSadEmoji.setOnClickListener(v -> {
            postAction.setActionType(4);
            holder.tvSad.setText(String.valueOf(Integer.parseInt(holder.tvSad.getText().toString()) + 1));

            holder.llLikes.setEnabled(false);
            holder.llDislikes.setEnabled(false);
            holder.llHappyEmoji.setEnabled(false);
            holder.llSadEmoji.setEnabled(false);

            initSetActionApi(postAction, holder.rootView);
        });

        holder.llComments.setOnClickListener(v -> {
            Intent intent = new Intent(profileFragment.getContext(), CommentsActivity.class);
            intent.putExtra("postId", post.getPost().getPostId());
            intent.putExtra("parentCommentId", String.valueOf(1));
            holder.rootView.getContext().startActivity(intent);
        });
    }

    private void initSetActionApi(PostAction postAction, View view) {

        Retrofit retrofit = RetrofitBuilder.getInstance(String.valueOf(R.string.baseUrl));
        IPostsApi iPostsApi = retrofit.create(IPostsApi.class);
        Call<PostAction> responses = iPostsApi.postPostAction(postAction, view.getContext().getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE).getString("AuthToken", ""));
        responses.enqueue(new Callback<PostAction>() {
            @Override
            public void onResponse (Call<PostAction> call, retrofit2.Response<PostAction> responseData) {

                if(responseData.body() != null) {
                }
                else {
                    Toast.makeText(profileFragment.getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure (Call<PostAction> call, Throwable t) {
                Toast.makeText(profileFragment.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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

        private final ImageView ivLikes;
        private final ImageView ivDislikes;

        private final LinearLayout llHappyEmoji;
        private final LinearLayout llSadEmoji;
        private final LinearLayout llLikes;
        private final LinearLayout llDislikes;
        private final LinearLayout llComments;
        private final LinearLayout llShare;

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

            ivLikes = view.findViewById(R.id.iv_like_empty);
            ivDislikes = view.findViewById(R.id.iv_dislike_empty);

            llHappyEmoji = view.findViewById(R.id.post_emoji_happy);
            llSadEmoji = view.findViewById(R.id.post_emoji_sad);
            llLikes = view.findViewById(R.id.post_like);
            llDislikes = view.findViewById(R.id.post_dislike);
            llComments = view.findViewById(R.id.post_comments);
            llShare = view.findViewById(R.id.post_share);
        }
    }
}