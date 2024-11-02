package com.saidev.PaiseWala.viewmodel;

public class viewPagerModel {

    int imageId;
    String stepNo, mainStep;

    public viewPagerModel() {
    }

    public viewPagerModel(int imageId, String stepNo, String mainStep) {
        this.imageId = imageId;
        this.stepNo = stepNo;
        this.mainStep = mainStep;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getStepNo() {
        return stepNo;
    }

    public void setStepNo(String stepNo) {
        this.stepNo = stepNo;
    }

    public String getMainStep() {
        return mainStep;
    }

    public void setMainStep(String mainStep) {
        this.mainStep = mainStep;
    }
}
