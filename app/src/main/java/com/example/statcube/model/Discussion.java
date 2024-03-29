package com.example.statcube.model;

import java.io.Serializable;
import java.util.Date;

public class Discussion implements Serializable {
    private Integer discussionID;
    private Integer topicID;
    private Integer userID;
    private Date discussionDate;
    private String discussionTitle;
    private String discussionContent;

    public Discussion(Integer discussionID, Integer topicID, Integer userID, Date discussionDate, String discussionTitle, String discussionContent) {
        this.discussionID = discussionID;
        this.topicID = topicID;
        this.userID = userID;
        this.discussionDate = discussionDate;
        this.discussionTitle = discussionTitle;
        this.discussionContent = discussionContent;
    }

    public Discussion(Integer discussionID, Integer topicID, Integer userID, String discussionTitle, String discussionContent) {
        this.discussionID = discussionID;
        this.topicID = topicID;
        this.userID = userID;
        this.discussionTitle = discussionTitle;
        this.discussionContent = discussionContent;
    }

    public Discussion(Integer discussionID) { this.discussionID = discussionID; }

    public Integer getDiscussionID() { return discussionID; }

    public void setDiscussionID(Integer discussionID) { this.discussionID = discussionID; }

    public Integer getTopicID() { return topicID; }

    public void setTopicID(Integer topicID) { this.topicID = topicID; }

    public Integer getUserID() { return userID; }

    public void setUserID(Integer userID) { this.userID = userID; }

    public Date getDiscussionDate() { return discussionDate; }

    public void setDiscussionDate(Date discussionDate) { this.discussionDate = discussionDate; }

    public String getDiscussionTitle() { return discussionTitle; }

    public void setDiscussionTitle(String discussionTitle) { this.discussionTitle = discussionTitle; }

    public String getDiscussionContent() { return discussionContent; }

    public void setDiscussionContent(String discussionContent) { this.discussionContent = discussionContent; }
}
