package com.example.springboot.user;

public class User {

    private String username;
    private String password;

    public User(int mobile, String name, String pass) {
        this.username = name;
        this.password = pass;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

}

