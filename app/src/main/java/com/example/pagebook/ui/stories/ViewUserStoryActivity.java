package com.example.pagebook.ui.stories;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.pagebook.R;

public class ViewUserStoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_story);

        ImageView userStory = findViewById(R.id.iv_user_story);
        userStory.setImageURI(Uri.parse(getIntent().getStringExtra("userStory")));

        userStory.setOnClickListener(v -> {
            finish();
        });
    }
}