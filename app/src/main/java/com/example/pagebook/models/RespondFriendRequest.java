package com.example.pagebook.models;

import com.google.gson.annotations.SerializedName;

public class RespondFriendRequest {

    @SerializedName("userId")
    private String userId;

    @SerializedName("friendId")
    private String friendId;

    public RespondFriendRequest(String userId, String friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }
}
