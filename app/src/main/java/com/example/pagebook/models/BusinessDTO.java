package com.example.pagebook.models;

import com.google.gson.annotations.SerializedName;

public class BusinessDTO {
    @SerializedName("imageUrl")
    private String imageUrl;

    @SerializedName("id")
    private String id;

    @SerializedName("businessName")
    private String businessName;

    public String getImageUrl() {
        return imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
}
