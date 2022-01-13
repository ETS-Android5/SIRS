package com.example.springboot;

import com.example.springboot.helpers.DBHelper;
import com.example.springboot.helpers.KeyGenerator;
import com.example.springboot.user.User;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;

@Service
public class ApplicationService {

    Dictionary codeList = new Hashtable();

    public String RegisterUser(User user, String code) throws SQLException, ClassNotFoundException {
        //create a userId
        //it is generated a secret key

        //

        String generatedKey ="";// = KeyGenerator.generateKeys();

        DBHelper.insertUser(user.getId(), user.getUsername(), user.getPassword(), generatedKey);

        return "olá!";
    }

    public String RegisterWorker(User user) throws SQLException, ClassNotFoundException {
        //create a userId
        //it is generated a secret key

        String generatedKey ="";// = KeyGenerator.generateKeys();

        DBHelper.insertWorker(user.getId(), user.getUsername(), user.getPassword(), generatedKey);

        return "olá!";
    }

    public String RegisterMobile(String code) throws SQLException, ClassNotFoundException {
        //code is generated in the frontend

        return "olá!";
    }
}