package com.example.pagebook.ui.fragments.notification.network;

import com.example.pagebook.models.Notification;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface INotificationApi {
    @GET("")
    Call<List<Notification>> getNotifications();
}
