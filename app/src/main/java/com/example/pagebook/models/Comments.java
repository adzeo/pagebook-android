package com.example.pagebook.models;

import com.google.gson.annotations.SerializedName;

public class Comments{

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