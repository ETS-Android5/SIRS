package com.example.springboot;

import com.example.springboot.helpers.DBHelper;
import com.example.springboot.helpers.totp.TOTPAuthenticator;
import com.example.springboot.helpers.totp.TOTPSecretKey;
import com.example.springboot.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;

@Service
public class ApplicationService {

    public boolean registryOK = false;

    public String RegisterMobile(String code, String sharedSecret) throws SQLException, ClassNotFoundException, NoSuchProviderException, NoSuchAlgorithmException {
        //code is generated in the frontend
        System.out.println(code);
        DBHelper.insertRegistrationCode(code, sharedSecret);

        long start = System.currentTimeMillis();
        long end = start + 300*1000;

        while (!registryOK && System.currentTimeMillis() < end) continue;
        return "OK";
    }

    public ResponseEntity<String> RegisterUser(User user, String code) throws SQLException, ClassNotFoundException {
        ResponseEntity<String> response = DBHelper.insertUser(user.getUsername(), user.getPassword(), code);
        if (response.getStatusCode() == HttpStatus.OK) {
            registryOK = true;
        }
        return response;
    }

    public ResponseEntity<String> Login(String username, int passcode) throws SQLException, ClassNotFoundException, NoSuchProviderException, NoSuchAlgorithmException {
        //TOPT
        String sharedSecret = DBHelper.getSharedSecret(username);

        byte[] byteSecret = sharedSecret.getBytes();
        
        try{
            if (sharedSecret != null) {
            
                TOTPSecretKey totpSecretKey = new TOTPSecretKey(byteSecret);
                TOTPAuthenticator totpAuthenticator = new TOTPAuthenticator();
                Instant instant = Instant.now();
                
                if (totpAuthenticator.authorize(totpSecretKey, passcode, instant)) {
                    DBHelper.Login(username, sharedSecret);
                    return new ResponseEntity<String>("Login done", HttpStatus.OK);
                }
                return new ResponseEntity<String>("Login done", HttpStatus.BAD_REQUEST);

            }
        }  catch (Throwable throwable) {
            System.out.println("Entrou no catch");
            throwable.printStackTrace();
        }
        
        System.out.println("DEU COCO");
        return new ResponseEntity<String>("Login Error", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> Logout(String username) throws SQLException, ClassNotFoundException, NoSuchProviderException, NoSuchAlgorithmException {
        String sharedSecret = DBHelper.getSharedSecret(username);
        return DBHelper.Logout(username , sharedSecret);
        
    }
     /*public String RegisterWorker(User user) throws SQLException, ClassNotFoundException {
        //create a userId
        //it is generated a secret key

        String generatedKey ="";// = KeyGenerator.generateKeys();

        DBHelper.insertWorker(user.getUsername(), user.getPassword(), generatedKey);

        return "olá!";
    }*/

}