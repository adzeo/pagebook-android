package com.example.pagebook.models;

import com.google.gson.annotations.SerializedName;

public class CommentDTO {

    @SerializedName("userName")
    private String userName;

    @SerializedName("comment")
    private Comment comment;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
