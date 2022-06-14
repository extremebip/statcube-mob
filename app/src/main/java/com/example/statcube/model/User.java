package com.example.statcube.model;

import java.util.Date;

public class User {
    private int UserID;
    private String UserName;
    private String UserEmail;
    private String UserPassword;
    private Date UserSubscriptionEndDate;

    public User(int userID, String userName, String userEmail, String userPassword, Date userSubscriptionEndDate) {
        UserID = userID;
        UserName = userName;
        UserEmail = userEmail;
        UserPassword = userPassword;
        UserSubscriptionEndDate = userSubscriptionEndDate;
    }

    public int getUserID() {
        return UserID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public Date getUserSubscriptionEndDate() {
        return UserSubscriptionEndDate;
    }

    public void setUserSubscriptionEndDate(Date userSubscriptionEndDate) {
        UserSubscriptionEndDate = userSubscriptionEndDate;
    }
}
