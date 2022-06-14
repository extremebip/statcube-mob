package com.example.statcube.model;

public class User {
    private Integer UserID;
    private String UserName;
    private String UserEmail;
    private String UserPassword;
    private String UserSubscriptionEndDate;

    public User(Integer userID, String userName, String userEmail, String userPassword, String userSubscriptionEndDate) {
        UserID = userID;
        UserName = userName;
        UserEmail = userEmail;
        UserPassword = userPassword;
        UserSubscriptionEndDate = userSubscriptionEndDate;
    }

    public User(Integer userID) { UserID = userID; }

    public Integer getUserID() { return UserID; }

    public void setUserID(Integer userID) { UserID = userID; }

    public String getUserName() { return UserName; }

    public void setUserName(String userName) { UserName = userName; }

    public String getUserEmail() { return UserEmail; }

    public void setUserEmail(String userEmail) { UserEmail = userEmail; }

    public String getUserPassword() { return UserPassword; }

    public void setUserPassword(String userPassword) { UserPassword = userPassword; }

    public String getUserSubscriptionEndDate() { return UserSubscriptionEndDate; }

    public void setUserSubscriptionEndDate(String userSubscriptionEndDate) { UserSubscriptionEndDate = userSubscriptionEndDate; }
}
