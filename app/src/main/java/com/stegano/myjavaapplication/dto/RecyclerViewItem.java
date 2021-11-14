package com.stegano.myjavaapplication.dto;

public class RecyclerViewItem {
    private String id;
    private String english;
    private String korean;
    private boolean isChecked;

    public RecyclerViewItem(String id, String english, String korean, boolean isChecked) {
        this.id = id;
        this.english = english;
        this.korean = korean;
        this.isChecked = isChecked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getKorean() {
        return korean;
    }

    public void setKorean(String korean) {
        this.korean = korean;
    }

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
