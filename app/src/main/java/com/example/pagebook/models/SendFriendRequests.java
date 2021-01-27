package com.example.pagebook.models;

import com.google.gson.annotations.SerializedName;

public class SendFriendRequests {
    @SerializedName("friendId")
    private String friendId;

    @SerializedName("requestorId")
    private String requestorId;

    public SendFriendRequests(String requestorId, String friendId) {
        this.requestorId = requestorId;
        this.friendId = friendId;
    }

    public void setMyId(String myId) {
        this.friendId = friendId;
    }

    public void setRequestorId(String requestorId) {
        this.requestorId = requestorId;
    }
}
