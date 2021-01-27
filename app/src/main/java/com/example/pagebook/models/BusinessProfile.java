package com.example.pagebook.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BusinessProfile implements Serializable {

	@SerializedName("imageUrl")
	private String imageUrl;

	@SerializedName("id")
	private String id;

	@SerializedName("name")
	private String businessName;

	@SerializedName("description")
	private String description;

	@SerializedName("adminEmail")
	private String adminEmail;

	@SerializedName("category")
	private String category;

	public void setImageUrl(String imageUrl){
		this.imageUrl = imageUrl;
	}

	public String getImageUrl(){
		return imageUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setBusinessName(String businessName){
		this.businessName = businessName;
	}

	public String getBusinessName(){
		return businessName;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public void setCategory(String category){
		this.category = category;
	}

	public String getCategory(){
		return category;
	}
}