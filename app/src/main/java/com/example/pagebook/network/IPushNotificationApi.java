package com.example.pagebook.network;

import com.example.pagebook.models.PushNotificationRequest;
import com.example.pagebook.models.PushNotificationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface IPushNotificationApi {

    @POST("notification/data")
    Call<PushNotificationResponse> sendNotification(@Body PushNotificationRequest pushNotificationRequest, @Header("Authorization") String token);
}
