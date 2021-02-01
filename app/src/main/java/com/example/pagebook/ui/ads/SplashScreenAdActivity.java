package com.example.pagebook.ui.ads;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.pagebook.PbMainActivity;
import com.example.pagebook.R;

public class SplashScreenAdActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_ad);

        CardView closeAd = findViewById(R.id.cardView_close_ad);

        ImageView ivAd = findViewById(R.id.iv_pop_up_ad);
        Glide.with(ivAd).load("https://thumbs.dreamstime.com/b/autumn-fall-nature-scene-autumnal-park-beautiful-77869343.jpg").placeholder(R.drawable.user_placeholder).into(ivAd);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                closeAd.setVisibility(View.VISIBLE);
                closeAd.setClickable(true);
            }
        }, SPLASH_TIME_OUT);

        closeAd.setOnClickListener(v -> {
            Intent i = new Intent(SplashScreenAdActivity.this, PbMainActivity.class);
            startActivity(i);
            finish();
        });
    }
}