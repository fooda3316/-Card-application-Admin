package com.example.adminapplication.model;

public class Page {
    private String image;
    private String name;
    private String uri;

    public Page(String image, String name, String uri) {
        this.image = image;
        this.name = name;
        this.uri = uri;
    }

    public Page(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public String getImage() {
        return image;
    }

}
