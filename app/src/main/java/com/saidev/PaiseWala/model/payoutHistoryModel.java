package com.saidev.PaiseWala.model;

public class payoutHistoryModel {

    Integer WithdrawalAmount, WithdrawStatus;

    public payoutHistoryModel() {
    }

    public payoutHistoryModel(Integer withdrawalAmount, Integer withdrawStatus) {
        WithdrawalAmount = withdrawalAmount;
        WithdrawStatus = withdrawStatus;
    }

    public Integer getWithdrawalAmount() {
        return WithdrawalAmount;
    }

    public void setWithdrawalAmount(Integer withdrawalAmount) {
        WithdrawalAmount = withdrawalAmount;
    }

    public Integer getWithdrawStatus() {
        return WithdrawStatus;
    }

    public void setWithdrawStatus(Integer withdrawStatus) {
        WithdrawStatus = withdrawStatus;
    }
}
