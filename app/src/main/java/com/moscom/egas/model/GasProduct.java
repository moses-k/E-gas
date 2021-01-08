package com.moscom.egas.model;

import android.graphics.drawable.Drawable;

public class GasProduct {
    public int getImage() {
        return image;
    }
    public void setImage(int image) {
        this.image = image;
    }

    public Drawable getImageDrw() {
        return imageDrw;
    }

    public void setImageDrw(Drawable imageDrw) {
        this.imageDrw = imageDrw;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int image;
    public Drawable imageDrw;
    public String title;
    public String price;

    public GasProduct() {
    }
}
