package com.example.pagebook.ui.fragments.notification.network;

import com.example.pagebook.models.Notification;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface INotificationApi {
    @GET("notification/getNotification/{userId}")
    Call<List<Notification>> getNotifications(@Path("userId") String userId, @Header("Authorization") String tokenKey);
}
