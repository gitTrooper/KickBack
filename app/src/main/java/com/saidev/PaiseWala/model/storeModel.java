package com.saidev.PaiseWala.model;

public class storeModel {

    String storeImgLink, storeName, storeLink;

    public storeModel() {
    }

    public storeModel(String storeImgLink, String storeName, String storeLink) {
        this.storeImgLink = storeImgLink;
        this.storeName = storeName;
        this.storeLink = storeLink;
    }

    public String getStoreImgLink() {
        return storeImgLink;
    }

    public void setStoreImgLink(String storeImgLink) {
        this.storeImgLink = storeImgLink;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreLink() {
        return storeLink;
    }

    public void setStoreLink(String storeLink) {
        this.storeLink = storeLink;
    }
}
