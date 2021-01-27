package com.example.pagebook.models;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Profile{

	@SerializedName("userId")
	private String userId;

	@SerializedName("imgUrl")
	private String imgUrl;

	@SerializedName("firstName")
	private String firstName;

	@SerializedName("lastName")
	private String lastName;

	@SerializedName("profileType")
	private String profileType;

	@SerializedName("interest")
	private String interest;

	@SerializedName("bio")
	private String bio;

	@SerializedName("email")
	private String email;

	@SerializedName("totalFriends")
	private Integer totalFriends;

	public Profile() {
	}

	public Profile(Map<String, String> map) {
		this.userId = map.get("userId");
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setProfileType(String profileType) {
		this.profileType = profileType;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getProfileType() {
		return profileType;
	}

	public String getInterest() {
		return interest;
	}

	public String getBio() {
		return bio;
	}

	public String getEmail() {
		return email;
	}

	public Integer getTotalFriends() {
		return totalFriends;
	}

	public void setTotalFriends(Integer totalFriends) {
		this.totalFriends = totalFriends;
	}
}