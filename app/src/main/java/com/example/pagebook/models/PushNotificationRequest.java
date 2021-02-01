package com.example.pagebook.models;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class PushNotificationRequest {

    @SerializedName("title")
    private String title;
    @SerializedName("message")
    private String message;
    @SerializedName("topic")
    private String topic;
    @SerializedName("token")
    private String token;
    @SerializedName("data")
    private Map<String, String> data;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
