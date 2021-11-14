package com.stegano.myjavaapplication.adapters;

public class RecyclerView2Item {
    private String imageUri;
    private String name;

    public RecyclerView2Item(String imageUri, String name) {
        this.imageUri = imageUri;
        this.name = name;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
