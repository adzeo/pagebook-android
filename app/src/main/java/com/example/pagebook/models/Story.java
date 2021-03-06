package com.example.pagebook.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

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
    private String fileUrl;

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

    public String getFileUrl() {
        return fileUrl;
    }

	public void setUserImageUrl(String userImageUrl) {
		this.userImageUrl = userImageUrl;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
}