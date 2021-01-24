package com.example.pagebook.generalmodels;

import com.google.gson.annotations.SerializedName;

public class PostDTO {

	@SerializedName("totalSadEmoji")
	private int totalSadEmoji;

	@SerializedName("totalComments")
	private int totalComments;

	@SerializedName("post")
	private Post post;

	@SerializedName("userImgURL")
	private String userImgURL;

	@SerializedName("userName")
	private String userName;

	@SerializedName("totalDislikes")
	private int totalDislikes;

	@SerializedName("totalLikes")
	private int totalLikes;

	@SerializedName("performedAction")
	private int performedAction;

	@SerializedName("totalHappyEmoji")
	private int totalHappyEmoji;

	public int getTotalComments(){
		return totalComments;
	}

	public Post getPost(){
		return post;
	}

	public int getTotalDislikes(){
		return totalDislikes;
	}

	public int getTotalLikes(){
		return totalLikes;
	}

	public int getPerformedAction(){
		return performedAction;
	}

	public int getTotalSadEmoji() {
		return totalSadEmoji;
	}

	public String getUserImgURL() {
		return userImgURL;
	}

	public int getTotalHappyEmoji() {
		return totalHappyEmoji;
	}

	public String getUserName() {
		return userName;
	}
}