package com.example.statcube.model;

public class Course {
    private Integer courseID;
    private Integer adminID;
    private String courseTitle;
    private String courseDescription;

    public Course(Integer courseID, Integer adminID, String courseTitle, String courseDescription) {
        this.courseID = courseID;
        this.adminID = adminID;
        this.courseTitle = courseTitle;
        this.courseDescription = courseDescription;
    }

    public Course(Integer courseID) { this.courseID = courseID; }

    public Integer getCourseID() { return courseID; }

    public void setCourseID(Integer courseID) { this.courseID = courseID; }

    public Integer getAdminID() { return adminID; }

    public void setAdminID(Integer adminID) { this.adminID = adminID; }

    public String getCourseTitle() { return courseTitle; }

    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }

    public String getCourseDescription() { return courseDescription; }

    public void setCourseDescription(String courseDescription) { this.courseDescription = courseDescription; }
}
