package com.example.pagebook.mainmodel;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("jwt")
    private String jwtToken;

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
