package com.example.statcube;

public class Course {
    private String courseID;
    private String adminID;
    private String topicID;
    private String title;
    private String description;

    public Course(String courseID, String adminID, String topicID, String title, String description) {
        this.courseID = courseID;
        this.adminID = adminID;
        this.topicID = topicID;
        this.title = title;
        this.description = description;
    }

    public String getCourseID() { return courseID; }

    public void setCourseID(String courseID) { this.courseID = courseID; }

    public String getAdminID() { return adminID; }

    public void setAdminID(String adminID) { this.adminID = adminID; }

    public String getTopicID() { return topicID; }

    public void setTopicID(String topicID) { this.topicID = topicID; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }
}
