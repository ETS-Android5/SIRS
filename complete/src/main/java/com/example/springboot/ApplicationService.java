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

    public ResponseEntity<ArrayList<Integer>> RegisterMobile(int mobile, String code) throws SQLException, ClassNotFoundException, NoSuchProviderException, NoSuchAlgorithmException {
        //code is generated in the frontend
        ArrayList<Integer> keys = new ArrayList<Integer>();

        if (DBHelper.insertRegistrationCode(mobile, code).getStatusCode() == HttpStatus.OK) {
            try {
            keys.addAll(prepareDHAlgorithm());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<>(keys, HttpStatus.OK);
        }
        return new ResponseEntity<>(keys, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> RegisterUser(User user, String code) throws SQLException, ClassNotFoundException {
        //Check if this user is already sign
        ResponseEntity<String> response = DBHelper.confirmCode(user.getMobile());
        if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
            return response;
        }

        //Starts the DH algorithm
        try {
            prepareDHAlgorithm();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        //insert user no fim do algoritmo
        //DBHelper.insertUser(user.getMobile(), user.getUsername(), user.getPassword(), generatedKey);

        return response;
    }

    public ResponseEntity<String> SuccessRegisterMobile(int mobile, int key) throws SQLException, ClassNotFoundException {
        return DBHelper.SuccessRegisterMobile(mobile, key);
    }

    public String RegisterWorker(User user) throws SQLException, ClassNotFoundException {
        //create a userId
        //it is generated a secret key

        String generatedKey ="";// = KeyGenerator.generateKeys();

        DBHelper.insertWorker(user.getMobile(), user.getUsername(), user.getPassword(), generatedKey);

        return "olá!";
    }
}