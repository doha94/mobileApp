package com.example.recycle_practice;

// ImageItem.java
public class ImageItem {
    private String title;
    private int imageResId;

    public ImageItem(String title, int imageResId) {
        this.title = title;
        this.imageResId = imageResId;
    }

    public String getTitle() { return title; }
    public int getImageResId() { return imageResId; }
}
