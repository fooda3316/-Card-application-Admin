package com.example.adminapplication.model;

public class Request {

    private int id;
    private String title;
    private String subName;
    private String branch;
    private String date;

    public Request(int id, String title, String subName, String branch, String date) {
        this.id = id;
        this.title = title;
        this.subName = subName;
        this.branch = branch;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSubName() {
        return subName;
    }

    public String getBranch() {
        return branch;
    }

    public String getDate() {
        return date;
    }
}
