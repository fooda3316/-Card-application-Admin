package com.example.adminapplication.model;

public class Card {
    private int id;
    private String title;
    private int price;
    private String image;
    private String subName;
    private String branch;

    public Card(int id, String title, int price, String image, String subName, String branch) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.image = image;
        this.subName = subName;
        this.branch = branch;
    }

    public String getSubName() {
        return subName;
    }

    public String getBranch() {
        return branch;
    }

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
