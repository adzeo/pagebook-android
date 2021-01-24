package com.example.pagebook.ui.stories.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Story implements Serializable {

	@SerializedName("userImageUrl")
	private String userImageUrl;

	@SerializedName("id")
	private String id;

	@SerializedName("userName")
	private String userName;

	@SerializedName("userId")
	private String userId;

    @SerializedName("fileUrl")
    private List<String> fileUrl;

	public String getUserImageUrl(){
		return userImageUrl;
	}

	public String getId(){
		return id;
	}

	public String getUserName(){
		return userName;
	}

	public String getUserId(){
		return userId;
	}

    public List<String> getFileUrl() {
        return fileUrl;
    }
}