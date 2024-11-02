package com.saidev.PaiseWala.model;

public class payoutDataModel {

    String userPhoneNumber,userAmount, userEmail;

    public payoutDataModel() {
    }

    public payoutDataModel(String userPhoneNumber,String userEmail, String userAmount) {
        this.userPhoneNumber = userPhoneNumber;
        this.userAmount = userAmount;
        this.userEmail = userEmail;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }


    public String getUserAmount() {
        return userAmount;
    }

    public void setUserAmount(String userAmount) {
        this.userAmount = userAmount;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
