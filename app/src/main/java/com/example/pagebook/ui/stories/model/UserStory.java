package com.example.pagebook.ui.stories.model;

import java.io.Serializable;
import java.util.List;

public class UserStory implements Serializable {

    List<String> userStoryList;

    public List<String> getUserStoryList() {
        return userStoryList;
    }

    public void setUserStoryList(List<String> userStoryList) {
        this.userStoryList = userStoryList;
    }
}
