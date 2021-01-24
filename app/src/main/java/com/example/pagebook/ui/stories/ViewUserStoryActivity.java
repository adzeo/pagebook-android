package com.example.pagebook.ui.stories;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.pagebook.R;
import com.example.pagebook.ui.stories.model.Story;
import com.example.pagebook.ui.stories.model.UserStory;

import java.util.ArrayList;
import java.util.List;

public class ViewUserStoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_story);

        UserStory userStory =  (UserStory) getIntent().getSerializableExtra("userStories");

        for(String story : userStory.getUserStoryList()) {

        }
    }
}