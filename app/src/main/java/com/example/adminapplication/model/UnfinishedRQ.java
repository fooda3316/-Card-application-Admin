package com.example.adminapplication.model;

public class UnfinishedRQ {
    private int id;
    private int userId;
    private String name;
    private String image;
    private String operation;

    public UnfinishedRQ(int id, int userId, String name, String image) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.image = image;
    }

    public String getOperation() {
        return operation;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
