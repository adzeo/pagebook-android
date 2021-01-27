package com.example.pagebook.models;

import com.google.gson.annotations.SerializedName;

public class Comment {

	@SerializedName("commentId")
	private String commentId;

	@SerializedName("parentCommentId")
	private String parentCommentId;

	@SerializedName("postId")
	private String postId;

	@SerializedName("text")
	private String text;

	@SerializedName("isApproved")
	private boolean isApproved;

	@SerializedName("userId")
	private String userId;

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public void setParentCommentId(String parentCommentId) {
		this.parentCommentId = parentCommentId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setApproved(boolean approved) {
		isApproved = approved;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCommentId(){
		return commentId;
	}

	public String getParentCommentId(){
		return parentCommentId;
	}

	public String getPostId(){
		return postId;
	}

	public String getText(){
		return text;
	}

	public boolean isIsApproved(){
		return isApproved;
	}

	public String getUserId(){
		return userId;
	}
}