package com.example.splashlogin.Models;

import com.google.firebase.database.Exclude;

import java.util.List;

public class OrdersModel {
    private String phoneNum;
    private String userEmail;
    private String fullName;
    private String city;
    private String street;
    private List<CartModel> orders;
    private String status;
    private String mKey;
    private int totalPrice;

    public OrdersModel() {}

    public OrdersModel(String phoneNum, String fullName, String userEmail, String city, String street, List<CartModel> orders, int totalPrice, String status) {
        this.phoneNum = phoneNum;
        this.fullName = fullName;
        this.userEmail = userEmail;
        this.city = city;
        this.street = street;
        this.orders = orders;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Exclude
    public String getmKey() {
        return mKey;
    }

    @Exclude
    public void setmKey(String mKey) {
        this.mKey = mKey;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public List<CartModel> getOrders() {
        return orders;
    }

    public void setOrders(List<CartModel> orders) {
        this.orders = orders;
    }
}
