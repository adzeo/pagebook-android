package com.example.pagebook.networkmanager;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationRetrofitBuilder {
    private static Retrofit instance;

    private NotificationRetrofitBuilder(Context context) {
        //private constructor
    }

    public static Retrofit getInstance() {
        if(instance == null) {
            synchronized (com.example.pagebook.networkmanager.NotificationRetrofitBuilder.class) {
                if(instance == null) {
                    instance = new Retrofit.Builder()
                            .baseUrl("http://10.177.2.29:8760/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(new OkHttpClient())
                            .build();
                }
            }
        }
        return instance;
    }
}
