package com.example.pagebook.generalmodels;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Post{

	@SerializedName("postCategory")
	private String postCategory;

	@SerializedName("profileType")
	private String profileType;

	@SerializedName("fileUrl")
	private String fileUrl;

	@SerializedName("postId")
	private String postId;

	@SerializedName("uploadTime")
	private Date uploadTime;

	@SerializedName("userId")
	private String userId;

	@SerializedName("fileType")
	private String fileType;

	@SerializedName("businessId")
	private String businessId;

	@SerializedName("isApproved")
	private boolean isApproved;

	public String getPostCategory(){
		return postCategory;
	}

	public String getProfileType(){
		return profileType;
	}

	public String getFileUrl(){
		return fileUrl;
	}

	public String getPostId(){
		return postId;
	}

	public Date getUploadTime(){
		return uploadTime;
	}

	public String getUserId(){
		return userId;
	}

	public String getFileType(){
		return fileType;
	}

	public String getBusinessId() {
		return businessId;
	}

	public boolean isApproved() {
		return isApproved;
	}


}