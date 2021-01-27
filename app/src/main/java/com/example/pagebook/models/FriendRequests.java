package com.example.pagebook.models;

import com.google.gson.annotations.SerializedName;

public class FriendRequests {

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("imgUrl")
    private String imgUrl;

    @SerializedName("requestorId")
    private  String requestorId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getRequestorId() {
        return requestorId;
    }

    public void setRequestorId(String requestorId) {
        this.requestorId = requestorId;
    }
}
