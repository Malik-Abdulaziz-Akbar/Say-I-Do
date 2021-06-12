package com.example.android.sayido;

import java.io.Serializable;

public class HomeOptions implements Serializable {
    private String text;
    private int imageId;
    private int ActivityId;


    public HomeOptions(String text,int imageId,int ActivityId)
    {
        this.text = text;
        this.imageId = imageId;
        this.ActivityId = ActivityId;
    }

    public int getImageId() {
        return imageId;
    }

    public String getText() {
        return text;
    }

    public int getActivityId() {
        return ActivityId;
    }
}
