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
import com.example.pagebook.ui.fragments.business.BusinessSearchResultFragment;
import com.example.pagebook.models.SearchBusinesses;

import java.util.List;

public class SearchBusinessRecyclerViewAdapter extends RecyclerView.Adapter<SearchBusinessRecyclerViewAdapter.ViewHolder> {

    private final List<SearchBusinesses> mSearchBusinessesDataList;
    private final BusinessSearchResultFragment businessSearchResultFragment;

    public SearchBusinessRecyclerViewAdapter(List<SearchBusinesses> searchBusinessesDataList, BusinessSearchResultFragment businessSearchResultFragment) {
        this.mSearchBusinessesDataList = searchBusinessesDataList;
        this.businessSearchResultFragment = businessSearchResultFragment;
    }

    @NonNull
    @Override
    public SearchBusinessRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new SearchBusinessRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchBusinessRecyclerViewAdapter.ViewHolder holder, int position) {
        SearchBusinesses searchBusinesses = mSearchBusinessesDataList.get(position);

        // set user name, image and time for the post
        Glide.with(holder.ivSearchUser.getContext()).load(searchBusinesses.getImageUrl()).placeholder(R.drawable.user_placeholder).into(holder.ivSearchUser);
        holder.tvSearchUserName.setText(searchBusinesses.getName());
        holder.rootView.setOnClickListener((view -> businessSearchResultFragment.onUserClick(searchBusinesses)));
    }

    @Override
    public int getItemCount() {
        return mSearchBusinessesDataList == null ? 0 : mSearchBusinessesDataList.size();
    }

    public interface UserDataInterface {
        void onUserClick(SearchBusinesses searchBusinesses);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivSearchUser;
        private final TextView tvSearchUserName;
        private final View rootView;

        public ViewHolder(View view) {
            super(view);
            rootView = view;
            ivSearchUser = view.findViewById(R.id.iv_search_profile);
            tvSearchUserName = view.findViewById(R.id.tv_search_profile_name);
        }
    }
}