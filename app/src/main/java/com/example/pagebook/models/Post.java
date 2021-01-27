package com.example.pagebook.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Post{

//	---------------------------------------------------------
// 	---------------------------------------------------------

	@SerializedName("postCategory")
	private String postCategory;

	@SerializedName("profileType")
	private String profileType;

	@SerializedName("fileURL")
	private String fileURL;

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

	public String getFileURL() {
		return fileURL;
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

	public void setPostCategory(String postCategory) {
		this.postCategory = postCategory;
	}

	public void setProfileType(String profileType) {
		this.profileType = profileType;
	}

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public void setApproved(boolean approved) {
		isApproved = approved;
	}

	//	---------------------------------------------------------
// 	---------------------------------------------------------

//	private String postCategory;
//	private String profileType;
//	private String fileUrl;
//	private String postId;
//	private Date uploadTime;
//	private String userId;
//	private String fileType;
//	private String businessId;
//	private boolean isApproved;
//
//	public Post(String postCategory, String profileType, String fileUrl, String postId, Date uploadTime, String userId, String fileType, String businessId, boolean isApproved) {
//		this.postCategory = postCategory;
//		this.profileType = profileType;
//		this.fileUrl = fileUrl;
//		this.postId = postId;
//		this.uploadTime = uploadTime;
//		this.userId = userId;
//		this.fileType = fileType;
//		this.businessId = businessId;
//		this.isApproved = isApproved;
//	}
//
//	public String getPostCategory() {
//		return postCategory;
//	}
//
//	public String getProfileType() {
//		return profileType;
//	}
//
//	public String getFileUrl() {
//		return fileUrl;
//	}
//
//	public String getPostId() {
//		return postId;
//	}
//
//	public Date getUploadTime() {
//		return uploadTime;
//	}
//
//	public String getUserId() {
//		return userId;
//	}
//
//	public String getFileType() {
//		return fileType;
//	}
//
//	public String getBusinessId() {
//		return businessId;
//	}
//
//	public boolean isApproved() {
//		return isApproved;
//	}

}