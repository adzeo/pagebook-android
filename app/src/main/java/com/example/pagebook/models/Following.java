package com.example.pagebook.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Following {

    @SerializedName("id")
    private String id;

    @SerializedName("userId")
    private String userId;

    @SerializedName("following")
    private List<String> following;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getFollowing() {
        return following;
    }

    public void setFollowing(List<String> following) {
        this.following = following;
    }
}
