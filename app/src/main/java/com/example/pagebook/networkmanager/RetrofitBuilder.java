package com.example.pagebook.networkmanager;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pagebook.R;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder extends AppCompatActivity {
    private static Retrofit instance;

//    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request newRequest  = chain.request().newBuilder()
//                    .addHeader("Authorization", getSharedPreferences("com.example.pagebook", Context.MODE_PRIVATE).getString("AuthToken", ""))
//                    .build();
//            return chain.proceed(newRequest);
//        }
//    }).build();

    private RetrofitBuilder(Context context) {
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
