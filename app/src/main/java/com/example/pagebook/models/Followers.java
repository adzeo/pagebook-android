package com.example.pagebook.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Followers {

    @SerializedName("id")
    private String id;

    @SerializedName("businessId")
    private String businessId;

    @SerializedName("followers")
    private List<String> followers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public void setFollowers(List<String> followers) {
        this.followers = followers;
    }
}
