package com.example.smartphoneapplication;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    @JsonProperty("ID")
    private int userId;
    @JsonProperty("Username")
    private String username;
    @JsonProperty("Password")
    private String password;

    public User(int id, String name, String pass) {
        this.userId = id;
        this.username = name;
        this.password = pass;
    }

    public int getId() {
        return this.userId;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}


