package com.stegano.myjavaapplication.adapters;

import java.util.ArrayList;

public class RecyclerViewItem {
    private int subject;
    private String id;
    private String english;
    private String korean;
    private boolean isChecked;
    private ArrayList<RecyclerView2Item> subList;

    public RecyclerViewItem(int subject, String id, String english, String korean, boolean isChecked, ArrayList<RecyclerView2Item> subList) {
        this.subject = subject;
        this.id = id;
        this.english = english;
        this.korean = korean;
        this.isChecked = isChecked;
        this.subList = (ArrayList<RecyclerView2Item>) subList.clone();  //
    }

    public ArrayList<RecyclerView2Item> getSubList() {
        return subList;
    }

    public void setSubList(ArrayList<RecyclerView2Item> subList) {
        this.subList = subList;
    }

    public int getSubject() {
        return subject;
    }

    public void setSubject(int subject) {
        this.subject = subject;
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
