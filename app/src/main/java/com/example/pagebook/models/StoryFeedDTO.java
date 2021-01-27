package com.example.pagebook.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StoryFeedDTO {

    @SerializedName("stories")
    private List<Story> stories;

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public List<Story> getStories() {
        return stories;
    }
}
