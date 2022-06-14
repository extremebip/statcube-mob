package com.example.statcube.model;

import java.io.Serializable;

public class Topic implements Serializable {
    private Integer topicID;
    private Integer courseID;
    private String topicTitle;
    private String topicContent;
    private String topicThumbnail;

    public Topic(Integer topicID, Integer courseID, String topicTitle, String topicContent, String topicThumbnail) {
        this.topicID = topicID;
        this.courseID = courseID;
        this.topicTitle = topicTitle;
        this.topicContent = topicContent;
        this.topicThumbnail = topicThumbnail;
    }

    public Topic(Integer topicID) { this.topicID = topicID; }

    public Integer getTopicID() { return topicID; }

    public void setTopicID(Integer topicID) { this.topicID = topicID; }

    public Integer getCourseID() { return courseID; }

    public void setCourseID(Integer courseID) { this.courseID = courseID; }

    public String getTopicTitle() { return topicTitle; }

    public void setTopicTitle(String topicTitle) { this.topicTitle = topicTitle; }

    public String getTopicContent() { return topicContent; }

    public void setTopicContent(String topicContent) { this.topicContent = topicContent; }

    public String getTopicThumbnail() { return topicThumbnail; }

    public void setTopicThumbnail(String topicThumbnail) { this.topicThumbnail = topicThumbnail; }
}

