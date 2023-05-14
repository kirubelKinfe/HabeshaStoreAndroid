package com.example.splashlogin.Models;

public class UserModel {
    String fullName = "";
    String username = "";
    String email = "";
    int phoneNumber = 0;
    String password = "";
    String privilege = "";

    public UserModel() {}

    public UserModel(String email, String password,String privilege) {
        this.email = email;
        this.password = password;
        this.privilege = privilege;
    }

    public UserModel(String fullName, String username, String email, int phoneNumber, String password, String privilege) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.privilege = privilege;
    }


    @Override
    public String toString() {
        return "UserModel{" +
                "fullname='" + fullName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", password='" + password + '\'' +
                '}';
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }
}
