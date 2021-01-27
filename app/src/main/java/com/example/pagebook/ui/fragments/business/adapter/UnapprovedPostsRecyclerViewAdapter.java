package com.example.pagebook.ui.fragments.business.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pagebook.R;
import com.example.pagebook.models.Post;
import com.example.pagebook.models.PostDTO;
import com.example.pagebook.networkmanager.RetrofitBuilder;
import com.example.pagebook.ui.fragments.business.UnapprovedPostsActivity;
import com.example.pagebook.ui.fragments.business.network.IBusinessProfileApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class UnapprovedPostsRecyclerViewAdapter extends RecyclerView.Adapter<UnapprovedPostsRecyclerViewAdapter.ViewHolder> {

    private final List<PostDTO> mPostsDataList;
    private final UnapprovedPostsActivity unapprovedPostsActivity;

    public UnapprovedPostsRecyclerViewAdapter(List<PostDTO> userDataList, UnapprovedPostsActivity unapprovedPostsActivity) {
        this.mPostsDataList = userDataList;
        this.unapprovedPostsActivity = unapprovedPostsActivity;
    }

    @NonNull
    @Override
    public UnapprovedPostsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.unapproved_posts_item, parent, false);
        return new UnapprovedPostsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnapprovedPostsRecyclerViewAdapter.ViewHolder holder, int position) {
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

        holder.btnApprove.setOnClickListener(v -> {
            mPostsDataList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mPostsDataList.size());

            holder.rootView.setVisibility(View.GONE);
            holder.rootView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));

            Retrofit retrofit = RetrofitBuilder.getInstance(String.valueOf(R.string.baseUrl));
            IBusinessProfileApi iBusinessProfileApi = retrofit.create(IBusinessProfileApi.class);
            Call<Post> responses = iBusinessProfileApi.updateApprovedPost(post.getPost().getPostId());
            responses.enqueue(new Callback<Post>() {
                @Override
                public void onResponse (Call<Post> call, retrofit2.Response<Post> responseData) {

                    if(responseData.body() != null) {
                        Toast.makeText(unapprovedPostsActivity, "Post Approved", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(unapprovedPostsActivity, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure (Call<Post> call, Throwable t) {
                    Toast.makeText(unapprovedPostsActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        holder.btnReject.setOnClickListener(v -> {
            mPostsDataList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mPostsDataList.size());

            holder.rootView.setVisibility(View.GONE);
            holder.rootView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));

            Retrofit retrofit = RetrofitBuilder.getInstance(String.valueOf(R.string.baseUrl));
            IBusinessProfileApi iBusinessProfileApi = retrofit.create(IBusinessProfileApi.class);
            Call<Void> responses = iBusinessProfileApi.deleteUnapprovedPost(post.getPost().getPostId());
            responses.enqueue(new Callback<Void>() {
                @Override
                public void onResponse (Call<Void> call, retrofit2.Response<Void> responseData) {

                    if(responseData.body() != null) {
                        Toast.makeText(unapprovedPostsActivity, "Post Rejected", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(unapprovedPostsActivity, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure (Call<Void> call, Throwable t) {
                    Toast.makeText(unapprovedPostsActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
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

        private final Button btnApprove;
        private final Button btnReject;

        private final View rootView;

        public ViewHolder(View view) {
            super(view);
            rootView = view;

            ivUser = view.findViewById(R.id.iv_unapproved_post_user);
            tvUserName = view.findViewById(R.id.tv_unapproved_post_user_name);
            tvPostDate = view.findViewById(R.id.tv_unapproved_post_time);
            tvPostText = view.findViewById(R.id.tv_unapproved_post_data);
            ivPostImage = view.findViewById(R.id.iv_unapproved_post_data);
            btnApprove = view.findViewById(R.id.btn_approve_post);
            btnReject = view.findViewById(R.id.btn_reject_post);
        }
    }
}