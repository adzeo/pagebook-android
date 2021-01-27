package com.example.pagebook.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StoryFeedDTO {

    @SerializedName("stories")
    private List<Story> stories;

    public List<Story> getStories() {
        return stories;
    }
}
