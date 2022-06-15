package com.example.statcube.model.validation;

public class UsernameRule extends Rule {
    String Username;

    public UsernameRule(String Username) {
        this.Username = Username;
    }

    @Override
    public void validate() {
        if (Username.trim().length() == 0){
            IsValid = false;
            ErrorMessage = "Username must not be empty";
            return;
        }

        IsValid = true;
        ErrorMessage = "";
    }
}
