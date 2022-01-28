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
import java.util.HashMap;
import java.util.Map;

@Service
public class ApplicationService {

    private boolean registryOK = false;
    private boolean authorized = false;
    private String userMobile;
    private HashMap<String, Map<String,Object>> purchases = new HashMap<>();

    public String RegisterMobile(String code, String sharedSecret) throws SQLException {
        //code is generated in the frontend
        System.out.println(code);

        if(DBHelper.insertRegistrationCode(code, sharedSecret) == "NOT OK")
            return "NOT OK";

        long start = System.currentTimeMillis();
        long end = start + 300*1000;

        while (!registryOK && System.currentTimeMillis() < end) continue;
        if (System.currentTimeMillis() >= end)
            return "NOT OK";
        System.out.println(registryOK);
        System.out.println("Guardou o user mobile " + userMobile);
        return userMobile;
    }

    public ResponseEntity<String> RegisterUser(User user, String code) throws SQLException  {
        ResponseEntity<String> response = DBHelper.insertUser(user.getUsername(), user.getPassword(), code);
        if (response.getStatusCode() == HttpStatus.OK) {
            registryOK = true;
            userMobile = user.getUsername();
            System.out.println("userMobile: " + userMobile);
        }
        return response;
    }

    public ResponseEntity<String> Login(String username, int passwordHash, int passcode) throws SQLException {
        //TOTP
        if (DBHelper.checkPassword(username, passwordHash)) {

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
                    return new ResponseEntity<String>("Login error", HttpStatus.BAD_REQUEST);

                }
            }  catch (Throwable throwable) {
                System.out.println("Entrou no catch");
                throwable.printStackTrace();
            }
        }

        System.out.println("DEU COCO");
        return new ResponseEntity<String>("Login Error", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> Logout(String username) throws SQLException, ClassNotFoundException, NoSuchProviderException, NoSuchAlgorithmException {
        String sharedSecret = DBHelper.getSharedSecret(username);
        return DBHelper.Logout(username , sharedSecret);
        
    }

    public String RefreshPurchase(String username, int totp) throws SQLException {

        String sharedSecret = DBHelper.getSharedSecret(username);
        System.out.println(username);

        byte[] byteSecret = sharedSecret.getBytes();
        Map<String, Object> purchase = new HashMap<>();

        try{
            if (sharedSecret != null) {

                TOTPSecretKey totpSecretKey = new TOTPSecretKey(byteSecret);
                TOTPAuthenticator totpAuthenticator = new TOTPAuthenticator();
                Instant instant = Instant.now();

                if (totpAuthenticator.createOneTimePassword(totpSecretKey, instant) == totp) {
                    if(purchases.containsKey(username)) {
                        System.out.println(purchases.get(username).get("product") + " - " + purchases.get(username).get("price") + " - " + purchases.get(username).get("expiration").toString());
                        return purchases.get(username).get("product") + " - " + purchases.get(username).get("price") + " - " + purchases.get(username).get("expiration").toString();
                    }
                    else
                        return null;
                }
            }
        }  catch (Throwable throwable) {
            System.out.println("Entrou no catch");
            throwable.printStackTrace();
        }
        return null;
    }

    public String ConfirmPurchase(Boolean authorization, String username) throws SQLException {
        purchases.remove(username);
        if(authorization) {
            authorized = true;
            return "OK";
        }
        return "NOT OK";
    }

    public ResponseEntity<String> PurchaseRequest(String username, String product, String price, long expiration) {

        Map<String, Object> info = new HashMap<>();

        info.put("product", product);
        info.put("price", price);
        info.put("expiration", expiration);

        purchases.put(username, info);

        long start = System.currentTimeMillis();
        long end = start + 1800*1000;

        while (!authorized && System.currentTimeMillis() < end) continue;
        if(System.currentTimeMillis() >= end)
            return new ResponseEntity<String>("Purchase not authorized", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<String>("Purchase authorized", HttpStatus.OK);
    }
}