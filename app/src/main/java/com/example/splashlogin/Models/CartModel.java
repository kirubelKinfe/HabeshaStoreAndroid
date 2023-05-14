package com.example.splashlogin.Models;


public class CartModel {
    String ID;
    String productName;
    int productPrice;
    String img;
    int quantity;
    int totalPrice;

    public CartModel() {}

    public CartModel(String ID, String productName, int productPrice, String img, int quantity,int totalPrice) {
        this.ID = ID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.img = img;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}

