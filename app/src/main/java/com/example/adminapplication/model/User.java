package com.example.adminapplication.model;

/**
 * Created by Belal on 14/04/17.
 */

public class User {

    private int id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String image;
    private String phone;
    private int balance;

    public User(String name,  String email, String password, String image, String phone, int balance) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.image = image;
        this.phone = phone;
        this.balance = balance;
    }

    public String getLastName() {
        return lastName;
    }

    public String getImage() {
        return image;
    }

    public String getPhone() {
        return phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword(){
        return password;
    }

    public int getBalance() {
        return balance;
    }
}
