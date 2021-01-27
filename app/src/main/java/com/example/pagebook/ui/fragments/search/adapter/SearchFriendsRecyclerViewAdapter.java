package com.example.pagebook.ui.fragments.search.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pagebook.R;
import com.example.pagebook.ui.fragments.search.SearchResultFragment;
import com.example.pagebook.models.SearchFriends;

import java.util.List;

public class SearchFriendsRecyclerViewAdapter extends RecyclerView.Adapter<SearchFriendsRecyclerViewAdapter.ViewHolder> {

    private final List<SearchFriends> mSearchFriendsDataList;
    private final SearchResultFragment searchResultFragment;

    public SearchFriendsRecyclerViewAdapter(List<SearchFriends> searchFriendsDataList, SearchResultFragment searchResultFragment) {
        this.mSearchFriendsDataList = searchFriendsDataList;
        this.searchResultFragment = searchResultFragment;
    }

    @NonNull
    @Override
    public SearchFriendsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new SearchFriendsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchFriendsRecyclerViewAdapter.ViewHolder holder, int position) {
        SearchFriends searchFriends = mSearchFriendsDataList.get(position);

        // set user name, image and time for the post
        Glide.with(holder.ivSearchUser.getContext()).load(searchFriends.getImageUrl()).placeholder(R.drawable.user_placeholder).into(holder.ivSearchUser);
        holder.tvSearchUserName.setText(searchFriends.getName());
        holder.rootView.setOnClickListener((view -> searchResultFragment.onUserClick(searchFriends)));
    }

    @Override
    public int getItemCount() {
        return mSearchFriendsDataList == null ? 0 : mSearchFriendsDataList.size();
    }

    public interface UserDataInterface {
        void onUserClick(SearchFriends searchFriends);
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