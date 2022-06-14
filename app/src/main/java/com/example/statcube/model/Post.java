package com.example.statcube.model;

import java.io.Serializable;

public class Post implements Serializable {
    private Integer postID;
    private Integer discussionID;
    private Integer adminID;
    private Integer userID;
    private String postContent;
    private String postDate;

    public Post(Integer postID, Integer discussionID, Integer adminID, Integer userID, String postContent, String postDate) {
        this.postID = postID;
        this.discussionID = discussionID;
        this.adminID = adminID;
        this.userID = userID;
        this.postContent = postContent;
        this.postDate = postDate;
    }

    public Post(Integer postID) { this.postID = postID; }

    public Integer getPostID() { return postID; }

    public void setPostID(Integer postID) { this.postID = postID; }

    public Integer getDiscussionID() { return discussionID; }

    public void setDiscussionID(Integer discussionID) { this.discussionID = discussionID; }

    public Integer getAdminID() { return adminID; }

    public void setAdminID(Integer adminID) { this.adminID = adminID; }

    public Integer getUserID() { return userID; }

    public void setUserID(Integer userID) { this.userID = userID; }

    public String getPostContent() { return postContent; }

    public void setPostContent(String postContent) { this.postContent = postContent; }

    public String getPostDate() { return postDate; }

    public void setPostDate(String postDate) { this.postDate = postDate; }
}
