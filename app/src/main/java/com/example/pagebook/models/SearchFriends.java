package com.example.pagebook.models;

import com.google.gson.annotations.SerializedName;

public class SearchFriends{

	@SerializedName("imageUrl")
	private String imageUrl;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	public String getImageUrl(){
		return imageUrl;
	}

	public String getName(){
		return name;
	}

	public String getId(){
		return id;
	}
}
