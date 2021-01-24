package com.example.pagebook.ui.addposts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.pagebook.R;

import java.util.ArrayList;
import java.util.List;

public class AddPostActivity extends AppCompatActivity {

    List<String> postCategoryMenuList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapterPostCategory;

    List<String> postTypeMenuList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapterPostType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_business_search);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //for post category autocomplete view
        AutoCompleteTextView postCategory = findViewById(R.id.auto_tv_post_category);

        postCategoryMenuList.add("Bollywood");
        postCategoryMenuList.add("Maths");
        postCategoryMenuList.add("Science");
        postCategoryMenuList.add("Sports");
        postCategoryMenuList.add("Technology");

        arrayAdapterPostCategory = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, postCategoryMenuList);
        postCategory.setAdapter(arrayAdapterPostCategory);
        postCategory.setThreshold(1);


        //for post type autocomplete view
        AutoCompleteTextView postType = findViewById(R.id.auto_tv_post_type);

        postTypeMenuList.add("Text");
        postTypeMenuList.add("Image");

        arrayAdapterPostType = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, postTypeMenuList);
        postType.setText("Text", false);
        postType.setAdapter(arrayAdapterPostType);
        postType.setThreshold(1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}