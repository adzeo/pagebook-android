package com.example.pagebook.models.user;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String imgUrl;
    private String firstName;
    private String lastName;
    private int totalFriends;
    private String profileType;
    private String interest;
    private String bio;
    private String id;
    private String email;
    private List<String> friendsList;
    private List<String> pagesFollowingList;


    public User() {
        this.friendsList = new ArrayList<>();
        this.pagesFollowingList = new ArrayList<>();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getTotalFriends() {
        return totalFriends;
    }

    public void setTotalFriends(int totalFriends) {
        this.totalFriends = totalFriends;
    }

    public String getProfileType() {
        return profileType;
    }

    public void setProfileType(String profileType) {
        this.profileType = profileType;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(List<String> friendsList) {
        this.friendsList = friendsList;
    }

    public List<String> getPagesFollowingList() {
        return pagesFollowingList;
    }

    public void setPagesFollowingList(List<String> pagesFollowingList) {
        this.pagesFollowingList = pagesFollowingList;
    }

    @Override
    public String toString() {
        return "User{" +
                "imgUrl='" + imgUrl + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", totalFriends=" + totalFriends +
                ", profileType='" + profileType + '\'' +
                ", interest='" + interest + '\'' +
                ", bio='" + bio + '\'' +
                ", id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", friendsList=" + friendsList +
                ", pagesFollowingList=" + pagesFollowingList +
                '}';
    }
}
