package com.example.pagebook.mainmodel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RegistrationRequest {

    @SerializedName("username")
    String userName;

    @SerializedName("password")
    String password;

    @SerializedName("client")
    String client;

    @SerializedName("roles")
    List<String> roles;

    @SerializedName("interests")
    List<String> interests;

    public RegistrationRequest() {
        this.client = "client-app";
        this.roles = new ArrayList<>();
        this.interests = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }
}
