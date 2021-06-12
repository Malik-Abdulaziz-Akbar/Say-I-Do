package com.example.android.sayido;

import java.io.Serializable;

public class BudgetOptions implements Serializable {
    private String text;
    private String Country_text;
    int price;
    private int imageId;
    VenueOptions venue;
    DecorOptions decor;
    VendorOptions vendor;

    BudgetOptions(String text, String country_text, int imageId,int price)
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
