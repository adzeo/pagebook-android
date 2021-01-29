package com.example.pagebook.models;

import com.google.gson.annotations.SerializedName;

public class PostDTO {

//	---------------------------------------------------------
// 	---------------------------------------------------------

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

	@SerializedName("totalWowEmoji")
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

	public void setTotalSadEmoji(int totalSadEmoji) {
		this.totalSadEmoji = totalSadEmoji;
	}

	public void setTotalComments(int totalComments) {
		this.totalComments = totalComments;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public void setUserImgURL(String userImgURL) {
		this.userImgURL = userImgURL;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setTotalDislikes(int totalDislikes) {
		this.totalDislikes = totalDislikes;
	}

	public void setTotalLikes(int totalLikes) {
		this.totalLikes = totalLikes;
	}

	public void setPerformedAction(int performedAction) {
		this.performedAction = performedAction;
	}

	public void setTotalHappyEmoji(int totalHappyEmoji) {
		this.totalHappyEmoji = totalHappyEmoji;
	}
	//	---------------------------------------------------------
//	---------------------------------------------------------

//	private int totalSadEmoji;
//	private int totalComments;
//	private Post post;
//	private String userImgURL;
//	private String userName;
//	private int totalDislikes;
//	private int totalLikes;
//	private int performedAction;
//	private int totalHappyEmoji;
//
//	public PostDTO(int totalSadEmoji, int totalComments, Post post, String userImgURL, String userName, int totalDislikes, int totalLikes, int performedAction, int totalHappyEmoji) {
//		this.totalSadEmoji = totalSadEmoji;
//		this.totalComments = totalComments;
//		this.post = post;
//		this.userImgURL = userImgURL;
//		this.userName = userName;
//		this.totalDislikes = totalDislikes;
//		this.totalLikes = totalLikes;
//		this.performedAction = performedAction;
//		this.totalHappyEmoji = totalHappyEmoji;
//	}
//
//	public int getTotalSadEmoji() {
//		return totalSadEmoji;
//	}
//
//	public int getTotalComments() {
//		return totalComments;
//	}
//
//	public Post getPost() {
//		return post;
//	}
//
//	public String getUserImgURL() {
//		return userImgURL;
//	}
//
//	public String getUserName() {
//		return userName;
//	}
//
//	public int getTotalDislikes() {
//		return totalDislikes;
//	}
//
//	public int getTotalLikes() {
//		return totalLikes;
//	}
//
//	public int getPerformedAction() {
//		return performedAction;
//	}
//
//	public int getTotalHappyEmoji() {
//		return totalHappyEmoji;
//	}
}