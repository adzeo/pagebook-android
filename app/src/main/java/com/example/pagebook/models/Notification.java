package com.example.pagebook.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Notification {

    @SerializedName("notificationId")
    private String notificationId;

    @SerializedName("userId")
    private String userId;

    @SerializedName("message")
    private String message;

    @SerializedName("uploadTime")
    private Date uploadTime;

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }
}
