package com.example.springboot.user;

import com.example.springboot.KeyGenerator;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class User {

    private int userId;

    public User(int id) {
        this.userId = id;
    }

}
