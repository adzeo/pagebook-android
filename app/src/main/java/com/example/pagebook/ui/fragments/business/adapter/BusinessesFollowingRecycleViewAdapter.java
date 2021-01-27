package com.example.pagebook.ui.fragments.business.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pagebook.R;
import com.example.pagebook.models.BusinessDTO;
import com.example.pagebook.ui.fragments.business.BusinessFragment;

import java.util.List;

public class BusinessesFollowingRecycleViewAdapter extends RecyclerView.Adapter<BusinessesFollowingRecycleViewAdapter.ViewHolder> {

    private final List<BusinessDTO> mBusinessFollowingDataList;
    private final BusinessFragment businessFragment;

    public BusinessesFollowingRecycleViewAdapter(List<BusinessDTO> businessFollowingDataList, BusinessFragment businessFragment) {
        this.mBusinessFollowingDataList = businessFollowingDataList;
        this.businessFragment = businessFragment;
    }

    @NonNull
    @Override
    public BusinessesFollowingRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new BusinessesFollowingRecycleViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessesFollowingRecycleViewAdapter.ViewHolder holder, int position) {
        BusinessDTO business = mBusinessFollowingDataList.get(position);

        // set user name, image and time for the post
        Glide.with(holder.ivFriendImg.getContext()).load(business.getImageUrl()).placeholder(R.drawable.user_placeholder).into(holder.ivFriendImg);
        holder.tvFriendName.setText(business.getBusinessName());
        holder.rootView.setOnClickListener((view -> businessFragment.onUserClick(business)));
    }

    @Override
    public int getItemCount() {
        return mBusinessFollowingDataList == null ? 0 : mBusinessFollowingDataList.size();
    }

    public interface UserDataInterface {
        void onUserClick(BusinessDTO business);
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