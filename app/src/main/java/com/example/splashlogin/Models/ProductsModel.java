package com.example.splashlogin.Models;

import com.google.firebase.database.Exclude;


public class ProductsModel {
    String productName;
    int productPrice;
    String img;
    int quantity;
    String category;
    String type;
    String mKey;
    String storageKey;

    public ProductsModel() {

    }

    public ProductsModel(String productName, int productPrice, String img, int quantity, String category, String type, String storageKey) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.img = img;
        this.quantity = quantity;
        this.category = category;
        this.type = type;
        this.storageKey = storageKey;
    }

    public String getStorageKey() {
        return storageKey;
    }

    public void setStorageKey(String storageKey) {
        this.storageKey = storageKey;
    }

    @Exclude
    public String getmKey() {
        return mKey;
    }

    @Exclude
    public void setmKey(String mKey) {
        this.mKey = mKey;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
