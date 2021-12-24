package com.example.adminapplication.model;

public class Admin {
    private int id;
    private String name;
    private String balance;
    private String image;
    private String date;

    public Admin(int id, String name, String balance, String image, String date) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.image = image;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBalance() {
        return balance;
    }

    public String getImage() {
        return image;
    }

    public String getDate() {
        return date;
    }
}

