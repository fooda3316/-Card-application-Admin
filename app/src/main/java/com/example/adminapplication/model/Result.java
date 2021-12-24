package com.example.adminapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Result {
    @SerializedName("error")
    private Boolean error;
    @SerializedName("ime")
    private Boolean ime;
    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private User user;
    @SerializedName("pass")
    private String pass;
    @SerializedName("imes")
    private List<Ime> imes;

    public Result(Boolean error, String message, User user, String pass) {
        this.error = error;
        this.message = message;
        this.user = user;
        this.pass = pass;
    }
    public Result(Boolean error, String message) {
        this.error = error;
        this.message = message;

    }

    public String getPass() {
        return pass;
    }

    public Boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public List<Ime> getImes() {
        return imes;
    }

    public Boolean getIme() {
        return ime;
    }
}
