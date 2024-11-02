package com.saidev.PaiseWala.model;

public class budget_model {

    public String imgLink, storeLink;
    public Integer viewType;

    public budget_model() {
    }

    public budget_model(String imgLink, String storeLink, Integer viewType) {
        this.imgLink = imgLink;
        this.storeLink = storeLink;
        this.viewType = viewType;
    }

    public Integer getViewType() {
        return viewType;
    }

    public void setViewType(Integer viewType) {
        this.viewType = viewType;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public String getStoreLink() {
        return storeLink;
    }

    public void setStoreLink(String storeLink) {
        this.storeLink = storeLink;
    }
}
