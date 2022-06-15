package com.example.statcube.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Post implements Serializable {
    private Integer postID;
    private Integer discussionID;
    private Integer adminID;
    private Integer userID;
    private String author;
    private String postContent;
    private Date postDate;
    private boolean isDeleted;

    public Post(Integer postID, Integer discussionID, Integer adminID, Integer userID, String postContent, Date postDate) {
        this.postID = postID;
        this.discussionID = discussionID;
        this.adminID = adminID;
        this.userID = userID;
        this.postContent = postContent;
        this.postDate = postDate;
    }

    public Post (Integer postID, Integer discussionID, Integer userID, String author, String postContent, Date postDate, boolean isDeleted) {
        this.postID = postID;
        this.discussionID = discussionID;
        this.userID = userID;
        this.author = author;
        this.postContent = postContent;
        this.postDate = postDate;
        this.isDeleted = isDeleted;
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

    public String getAuthor() {
        return author;
    }

    public String getPostContent() { return postContent; }

    public void setPostContent(String postContent) { this.postContent = postContent; }

    public Date getPostDate() { return postDate; }

    public String getPostDateFormatted() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy HH:mm");
        return formatter.format(postDate);
    }

    public void setPostDate(Date postDate) { this.postDate = postDate; }

    public boolean isDeleted() {
        return isDeleted;
    }
}
