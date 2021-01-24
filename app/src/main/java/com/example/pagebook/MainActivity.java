package com.example.pagebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.pagebook.ui.fragments.business.BusinessFragment;
import com.example.pagebook.ui.fragments.homefeed.HomeFeedFragment;
import com.example.pagebook.ui.fragments.notification.NotificationFragment;
import com.example.pagebook.ui.fragments.profile.ProfileFragment;
import com.example.pagebook.ui.fragments.search.SearchFragment;
import com.example.pagebook.ui.friendrequests.FriendRequestsActivity;
import com.example.pagebook.ui.stories.StoriesActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_home_feed);
        setSupportActionBar(toolbar);

        //loading the default fragment
        loadFragment(new HomeFeedFragment());

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.bottom_navigation_home_feed:
                fragment = new HomeFeedFragment();
                break;

            case R.id.bottom_navigation_search:
                fragment = new SearchFragment();
                break;

            case R.id.bottom_navigation_notification:
                fragment = new NotificationFragment();
                break;

            case R.id.bottom_navigation_business:
                fragment = new BusinessFragment();
                break;

            case R.id.bottom_navigation_profile:
                fragment = new ProfileFragment();
                break;
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_app_stories:
                startActivity(new Intent(this, StoriesActivity.class));
                break;

            case R.id.action_app_friend_requests:
                startActivity(new Intent(this, FriendRequestsActivity.class));
                break;

            case R.id.action_app_logout:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}