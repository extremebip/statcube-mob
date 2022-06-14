package com.example.statcube.model;

public class Discussion {
    private Integer discussionID;
    private Integer topicID;
    private Integer userID;
    private String discussionDate;
    private String discussionTitle;
    private String discussionContent;

    public Discussion(Integer discussionID, Integer topicID, Integer userID, String discussionDate, String discussionTitle, String discussionContent) {
        this.discussionID = discussionID;
        this.topicID = topicID;
        this.userID = userID;
        this.discussionDate = discussionDate;
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

    public String getDiscussionDate() { return discussionDate; }

    public void setDiscussionDate(String discussionDate) { this.discussionDate = discussionDate; }

    public String getDiscussionTitle() { return discussionTitle; }

    public void setDiscussionTitle(String discussionTitle) { this.discussionTitle = discussionTitle; }

    public String getDiscussionContent() { return discussionContent; }

    public void setDiscussionContent(String discussionContent) { this.discussionContent = discussionContent; }
}
