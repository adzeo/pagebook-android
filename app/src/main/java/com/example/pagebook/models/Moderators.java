package com.example.pagebook.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Moderators {

    @SerializedName("id")
    private String id;

    @SerializedName("businessId")
    private String businessId;

    @SerializedName("moderators")
    private List<String> moderators;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public List<String> getModerators() {
        return moderators;
    }

    public void setModerators(List<String> moderators) {
        this.moderators = moderators;
    }
}
