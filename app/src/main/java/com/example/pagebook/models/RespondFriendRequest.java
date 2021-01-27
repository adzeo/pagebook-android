package com.example.pagebook.models;

import com.google.gson.annotations.SerializedName;

public class RespondFriendRequest {

    @SerializedName("myId")
    private String myId;

    @SerializedName("requestorId")
    private String requestorId;

    public RespondFriendRequest(String myId, String requestorId) {
        this.myId = myId;
        this.requestorId = requestorId;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public void setRequestorId(String requestorId) {
        this.requestorId = requestorId;
    }
}
