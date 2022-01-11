package com.example.springboot.user;

public class User {

    private int userId;
    private String username;
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

