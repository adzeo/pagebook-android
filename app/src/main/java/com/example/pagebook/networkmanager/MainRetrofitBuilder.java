package com.example.pagebook.networkmanager;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainRetrofitBuilder {
    private static Retrofit instance;

    private MainRetrofitBuilder(Context context) {
        //private constructor
    }

    public static Retrofit getInstance(String baseUrl) {
        if(instance == null) {
            synchronized (com.example.pagebook.networkmanager.RetrofitBuilder.class) {
                if(instance == null) {
                    instance = new Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(new OkHttpClient())
                            .build();
                }
            }
        }
        return instance;
    }
}
