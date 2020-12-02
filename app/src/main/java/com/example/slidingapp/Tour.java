package com.example.slidingapp;

public class Tour {
    private String name;
    private String price;
    private String desc;
    private String image;

    public Tour (String tName, String tPrice, String tDesc, String tImage){
        this.name = tName;
        this.price = tPrice;
        this.desc  = tDesc;
        this.image = tImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
