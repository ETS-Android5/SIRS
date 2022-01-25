package com.example.springboot.user;

public class User {

    private String username;
    private int password;

    public User(String name, int pass) {
        this.username = name;
        this.password = pass;
    }

    public String getUsername() {
        return this.username;
    }

    public int getPassword() {
        return this.password;
    }

}

