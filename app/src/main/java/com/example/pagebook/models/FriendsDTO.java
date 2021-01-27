package com.example.pagebook.models;

import com.google.gson.annotations.SerializedName;

public class FriendsDTO {

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("imgUrl")
    private String imgUrl;

    @SerializedName("friendId")
    private String friendId;

    public String getFirstName() {
        return firstName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getFriendId() {
        return friendId;
    }
}
