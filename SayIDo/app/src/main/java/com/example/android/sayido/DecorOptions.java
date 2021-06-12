package com.example.android.sayido;

import java.io.Serializable;

public class DecorOptions implements Serializable {
     String text;
     String Country_text;
     int imageId;
     int price;
    public void setText(String text) {
        this.text = text;
    }

    public void setCountry_text(String country_text) {
        Country_text = country_text;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public DecorOptions() {
    }

    public DecorOptions(String text, String country_text, int imageId,int price)
    {
        this.text = text;
        this.imageId = imageId;
        this.Country_text = country_text;
        this.price = price;
    }

    public int getImageId() {
        return imageId;
    }

    public String getText() {
        return text;
    }

    public String getCountry_text() {
        return Country_text;
    }
}
