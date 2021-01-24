package com.example.pagebook.networkmanager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    private static Retrofit instance;

    private RetrofitBuilder() {
        //private constructor
    }

    public static Retrofit getInstance() {
        if(instance == null) {
            synchronized (com.example.pagebook.networkmanager.RetrofitBuilder.class) {
                if(instance == null) {
                    instance = new Retrofit.Builder()
                            .baseUrl("http://192.168.149.1:9001")
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(new OkHttpClient())
                            .build();
                }
            }
        }
        return instance;
    }
}
