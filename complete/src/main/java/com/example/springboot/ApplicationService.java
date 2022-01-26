package com.example.springboot;

import com.example.springboot.helpers.DBHelper;
import com.example.springboot.helpers.totp.TOTPAuthenticator;
import com.example.springboot.helpers.totp.TOTPSecretKey;
import com.example.springboot.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;

@Service
public class ApplicationService {

    public String RegisterMobile(byte[] code, byte[] sharedSecret) throws SQLException, ClassNotFoundException, NoSuchProviderException, NoSuchAlgorithmException {
        //code is generated in the frontend
        return DBHelper.insertRegistrationCode(code, sharedSecret);
    }

    public ResponseEntity<String> RegisterUser(User user, byte[] code) throws SQLException, ClassNotFoundException {
        return DBHelper.insertUser(user.getUsername(), user.getPassword(), code);
    }

    public ResponseEntity<String> Login(String username, int passcode) throws SQLException, ClassNotFoundException, NoSuchProviderException, NoSuchAlgorithmException {
        //TOPT
        byte[] sharedSecret = DBHelper.getSharedSecret(username);

        if (sharedSecret != null) {
            TOTPSecretKey totpSecretKey = new TOTPSecretKey(sharedSecret);
            TOTPAuthenticator totpAuthenticator = new TOTPAuthenticator();
            Instant instant = Instant.now();

            if (totpAuthenticator.authorize(totpSecretKey, passcode, instant)) {
                DBHelper.Login(username, sharedSecret);
                return new ResponseEntity<String>("Login done", HttpStatus.OK);
            }
        }
        return new ResponseEntity<String>("Login Error", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> Logout(String username) throws SQLException, ClassNotFoundException, NoSuchProviderException, NoSuchAlgorithmException {
        DBHelper.Logout(username);
        return new ResponseEntity<String>("Logout Error", HttpStatus.BAD_REQUEST);
    }
     /*public String RegisterWorker(User user) throws SQLException, ClassNotFoundException {
        //create a userId
        //it is generated a secret key

        String generatedKey ="";// = KeyGenerator.generateKeys();

        DBHelper.insertWorker(user.getUsername(), user.getPassword(), generatedKey);

        return "olá!";
    }*/

}