package com.example.adminapplication.model;

public class Branch {
    private int src;
    private String image;
    private String branch;

    public Branch(String image, String branch) {
        this.image = image;
        this.branch = branch;
    }

    public Branch(int src, String branch) {
        this.src = src;
        this.branch = branch;
    }

    public String getImage() {
        return image;
    }

    public int getSrc() {
        return src;
    }

    public String getBranch() {
        return branch;
    }
}
