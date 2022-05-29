package com.example.statcube.model.validation;

public abstract class Rule {
    protected boolean IsValid;
    protected String ErrorMessage;
    public abstract void validate();
    public boolean isValid(){
        return IsValid;
    }
    public String getErrorMessage(){
        return ErrorMessage;
    }
}