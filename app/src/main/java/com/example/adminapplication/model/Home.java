package com.example.adminapplication.model;

public class Home {

    private String title;
    private Boolean haveBranch=true;

    public Home(String title, Boolean haveBranch) {
        this.title = title;
        this.haveBranch = haveBranch;

    }


    public Boolean getHaveBranch() {
        return haveBranch;
    }

    public String getTitle() {
        return title;
    }
}
