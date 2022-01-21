package com.example.springboot;

import com.example.springboot.helpers.DBHelper;
import com.example.springboot.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.example.springboot.helpers.KeyGenerator.prepareDHAlgorithm;

@Service
public class ApplicationService {

    public ResponseEntity<String> RegisterMobile(String code, String sharedSecret) throws SQLException, ClassNotFoundException, NoSuchProviderException, NoSuchAlgorithmException {
        //code is generated in the frontend
        return DBHelper.insertRegistrationCode(code, sharedSecret);
    }

    public ResponseEntity<String> RegisterUser(User user, String code) throws SQLException, ClassNotFoundException {
        return DBHelper.insertUser(user.getUsername(), user.getPassword(), code);
    }

    public ResponseEntity<String> LoginMobile(String username, String passcode) throws SQLException, ClassNotFoundException, NoSuchProviderException, NoSuchAlgorithmException {
        //TOPT converter passcode no shared secret
        return DBHelper.Login(username, passcode);
    }

     /*public String RegisterWorker(User user) throws SQLException, ClassNotFoundException {
        //create a userId
        //it is generated a secret key

        String generatedKey ="";// = KeyGenerator.generateKeys();

        DBHelper.insertWorker(user.getUsername(), user.getPassword(), generatedKey);

        return "olá!";
    }*/

}