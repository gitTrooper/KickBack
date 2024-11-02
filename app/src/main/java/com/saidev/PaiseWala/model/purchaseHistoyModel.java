package com.saidev.PaiseWala.model;

public class purchaseHistoyModel {

    String ProductName, status, BarColor;

    public purchaseHistoyModel() {
    }

    public purchaseHistoyModel(String productName, String barColor, String status) {
        ProductName = productName;
        BarColor = barColor;
        this.status = status;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getBarColor() {
        return BarColor;
    }

    public void setBarColor(String barColor) {
        BarColor = barColor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
