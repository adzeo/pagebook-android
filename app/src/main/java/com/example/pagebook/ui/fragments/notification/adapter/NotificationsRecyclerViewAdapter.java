package com.example.pagebook.ui.fragments.notification.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pagebook.R;
import com.example.pagebook.models.Notification;

import java.util.List;

public class NotificationsRecyclerViewAdapter extends RecyclerView.Adapter<NotificationsRecyclerViewAdapter.ViewHolder> {

    private final List<Notification> mNotificationsDataList;

    public NotificationsRecyclerViewAdapter(List<Notification> notificationsDataList) {
        this.mNotificationsDataList = notificationsDataList;
    }

    @NonNull
    @Override
    public NotificationsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new NotificationsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsRecyclerViewAdapter.ViewHolder holder, int position) {
        Notification notification = mNotificationsDataList.get(position);
        holder.tvNotification.setText(notification.getMessage());
    }

    @Override
    public int getItemCount() {
        return mNotificationsDataList == null ? 0 : mNotificationsDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvNotification;
        private final View rootView;

        public ViewHolder(View view) {
            super(view);
            rootView = view;
            tvNotification = view.findViewById(R.id.tv_notification_text);
        }
    }
}
