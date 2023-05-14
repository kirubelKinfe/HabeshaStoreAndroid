package com.example.commerceapp.Models;

public class Row {
    private int img;
    private String title;
    private int price;
    private String description;


    public Row(int img, String title,int price,String description) {
        this.img = img;
        this.title = title;
        this.price = price;
        this.description = description;
    }

    public int getImg() {
        return img;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice(){return price;}

    public String getDescription(){return description;}

}
