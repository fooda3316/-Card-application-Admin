package com.example.adminapplication.model;

public class AdminCard {
    private String title;
    private String subName;
    private String branch;
    private String serialnumber;
    private int id;
   // private int value;


    public AdminCard(String title, String subName, String branch, String serialnumber) {
        this.title = title;
        this.subName = subName;
        this.branch = branch;
        this.serialnumber = serialnumber;
    }

    public AdminCard( int id,String serialnumber) {
        this.serialnumber = serialnumber;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getSubName() {
        return subName;
    }

    public String getBranch() {
        return branch;
    }

    public String getTitle() {
        return title;
    }

//    public int getValue() {
//        return value;
//    }

    public String getSerialnumber() {
        return serialnumber;
    }
}
