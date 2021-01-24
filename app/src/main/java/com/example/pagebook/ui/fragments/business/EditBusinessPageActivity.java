package com.example.pagebook.ui.fragments.business;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.pagebook.R;

import java.util.ArrayList;
import java.util.List;

public class EditBusinessPageActivity extends AppCompatActivity {

    List<String> businessCategoryMenuList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapterBusinessCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_business_page);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_business_edit_profile);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //for business category autocomplete view
        AutoCompleteTextView businessCategory = findViewById(R.id.auto_tv_change_business_category);

        businessCategoryMenuList.add("Bollywood");
        businessCategoryMenuList.add("Maths");
        businessCategoryMenuList.add("Science");
        businessCategoryMenuList.add("Sports");
        businessCategoryMenuList.add("Technology");

        arrayAdapterBusinessCategory = new ArrayAdapter<>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, businessCategoryMenuList);
        businessCategory.setAdapter(arrayAdapterBusinessCategory);
        businessCategory.setThreshold(1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}