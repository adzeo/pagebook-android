package com.example.pagebook.ui.friendrequests.adapter;

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
import com.example.pagebook.models.RespondFriendRequest;
import com.example.pagebook.models.user.User;
import com.example.pagebook.models.user.UserBuilder;
import com.example.pagebook.networkmanager.RetrofitBuilder;
import com.example.pagebook.ui.friendrequests.FriendRequestsActivity;
import com.example.pagebook.models.FriendRequests;
import com.example.pagebook.ui.friendrequests.network.IFriendRequestsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class FriendRequestsRecyclerViewAdapter extends RecyclerView.Adapter<FriendRequestsRecyclerViewAdapter.ViewHolder> {

    private final List<FriendRequests> mFriendRequestsDataList;
    private final FriendRequestsActivity friendRequestsActivity;

    User myUser = UserBuilder.getInstance();

    public FriendRequestsRecyclerViewAdapter(List<FriendRequests> friendRequestsDataList, FriendRequestsActivity friendRequestsActivity) {
        this.mFriendRequestsDataList = friendRequestsDataList;
        this.friendRequestsActivity = friendRequestsActivity;
    }

    @NonNull
    @Override
    public FriendRequestsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_request_item, parent, false);
        return new FriendRequestsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendRequestsRecyclerViewAdapter.ViewHolder holder, int position) {
        FriendRequests friendRequest = mFriendRequestsDataList.get(position);

        // set user name, image and time for the post
        Glide.with(holder.ivFriendRequestUser.getContext()).load(friendRequest.getImgUrl()).placeholder(R.drawable.user_placeholder).into(holder.ivFriendRequestUser);
        holder.tvFriendRequestName.setText(friendRequest.getFirstName());

        holder.btnAccept.setOnClickListener(v -> {
            mFriendRequestsDataList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mFriendRequestsDataList.size());

            holder.rootView.setVisibility(View.GONE);
            holder.rootView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));

            RespondFriendRequest friendObj = new RespondFriendRequest(myUser.getId(), friendRequest.getRequestorId());

            Retrofit retrofit = RetrofitBuilder.getInstance(String.valueOf(R.string.baseUrl));
            IFriendRequestsApi iFriendRequestsApi = retrofit.create(IFriendRequestsApi.class);
            Call<Void> responses = iFriendRequestsApi.acceptRequest(friendObj);
            responses.enqueue(new Callback<Void>() {
                @Override
                public void onResponse (Call<Void> call, retrofit2.Response<Void> responseData) {

                    if(responseData.body() != null) {
                        Toast.makeText(friendRequestsActivity, "Request Accepted", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(friendRequestsActivity, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure (Call<Void> call, Throwable t) {
                    Toast.makeText(friendRequestsActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        holder.btnDecline.setOnClickListener(v -> {
            mFriendRequestsDataList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, mFriendRequestsDataList.size());

            holder.rootView.setVisibility(View.GONE);
            holder.rootView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));

            RespondFriendRequest friendObj = new RespondFriendRequest(myUser.getId(), friendRequest.getRequestorId());

            Retrofit retrofit = RetrofitBuilder.getInstance(String.valueOf(R.string.baseUrl));
            IFriendRequestsApi iFriendRequestsApi = retrofit.create(IFriendRequestsApi.class);
            Call<Void> responses = iFriendRequestsApi.declineRequest(friendObj);
            responses.enqueue(new Callback<Void>() {
                @Override
                public void onResponse (Call<Void> call, retrofit2.Response<Void> responseData) {

                    if(responseData.body() != null) {
                        Toast.makeText(friendRequestsActivity, "Request Declined", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(friendRequestsActivity, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure (Call<Void> call, Throwable t) {
                    Toast.makeText(friendRequestsActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return mFriendRequestsDataList == null ? 0 : mFriendRequestsDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivFriendRequestUser;
        private final TextView tvFriendRequestName;
        private final Button btnAccept;
        private final Button btnDecline;
        private final View rootView;

        public ViewHolder(View view) {
            super(view);
            rootView = view;
            ivFriendRequestUser = view.findViewById(R.id.iv_friend_request);
            tvFriendRequestName = view.findViewById(R.id.tv_friend_request_name);
            btnAccept = view.findViewById(R.id.btn_accept_request);
            btnDecline = view.findViewById(R.id.btn_decline_request);
        }
    }
}