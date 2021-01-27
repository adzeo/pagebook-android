package com.example.pagebook.models;

import com.google.gson.annotations.SerializedName;

public class SearchBusinesses {
    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private String id;

    @SerializedName("flag")
    private boolean flag;

    public String getImageUrl(){
        return imageUrl;
    }

    public String getName(){
        return name;
    }

    public String getId(){
        return id;
    }
}
