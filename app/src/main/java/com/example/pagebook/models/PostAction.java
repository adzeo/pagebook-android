package com.example.pagebook.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PostAction{

	@SerializedName("actionType")
	private int actionType;

	@SerializedName("postId")
	private String postId;

	@SerializedName("userId")
	private String userId;

	@SerializedName("actionId")
	private String actionId;

	@SerializedName("actionTime")
	private Date actionTime;

	public void setActionType(int actionType){
		this.actionType = actionType;
	}

	public int getActionType(){
		return actionType;
	}

	public void setPostId(String postId){
		this.postId = postId;
	}

	public String getPostId(){
		return postId;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public Date getActionTime() {
		return actionTime;
	}

	public void setActionTime(Date actionTime) {
		this.actionTime = actionTime;
	}
}