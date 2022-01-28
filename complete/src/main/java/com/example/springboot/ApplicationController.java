package com.example.springboot;

import com.example.springboot.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;
import java.util.Map;

@CrossOrigin( origins = {  "http://127.0.0.1:3000", "http://localhost:3000" })
@RestController
public class ApplicationController {

    @Autowired
    private ApplicationService AppService;

    @PostMapping(value="/RegisterMobile", consumes = "application/json")
    public String RegisterMobile(@RequestBody Map<String, Object> body) throws SQLException, ClassNotFoundException, NoSuchProviderException, NoSuchAlgorithmException {
        //return value is code to insert in
        String randomCode = body.get("randomCode").toString();
        String sharedSecret = body.get("sharedSecret").toString();
        return AppService.RegisterMobile(randomCode, sharedSecret);
    }

    @PostMapping(value="/RegisterUser")
    public ResponseEntity<String> RegisterUser(@RequestBody Map<String , Object> payload) throws SQLException, ClassNotFoundException {
        //return value is code to insert in
        String username = payload.get("var1").toString();
        int passwordHash = payload.get("var2").toString().hashCode();
        String code = payload.get("var3").toString();

        System.out.println(username);
        System.out.println(passwordHash);
        System.out.println(code);
        User userRegistration = new User(username, passwordHash);

        System.out.println(userRegistration.getUsername());
        System.out.println(userRegistration.getPassword());
        
        return AppService.RegisterUser(userRegistration, code);
    }

    @PostMapping(value="/LogIn")
    public ResponseEntity<String> UserLogIn(@RequestBody Map<String , Object> payload) throws SQLException, ClassNotFoundException, NoSuchProviderException, NoSuchAlgorithmException {
        
        String username = payload.get("var1").toString() ;
        int passwordHash = payload.get("var2").toString().hashCode();
        int code = Integer.parseInt( payload.get("var3").toString() );

        System.out.println(username);
        System.out.println(code);
        
        
        return AppService.Login(username, passwordHash ,code);
    }

    @PostMapping(value="/LogOut")
    public ResponseEntity<String> UserLogOut(@RequestBody Map<String , Object> payload) throws SQLException, ClassNotFoundException, NoSuchProviderException, NoSuchAlgorithmException {
        
        String username = payload.get("var1").toString();
        System.out.println(username);

        return AppService.Logout(username);
    }

    @PostMapping(value="/RefreshPurchase")
    public String RefreshPurchase(@RequestBody Map<String, Object> body) throws SQLException {
        String username = body.get("username").toString();
        Integer totp = Integer.parseInt(body.get("totp").toString());
        return AppService.RefreshPurchase(username, totp);
    }

    @PostMapping(value="/ConfirmPurchase")
    public String ConfirmPurchase(@RequestBody Map<String, Object> body) throws SQLException {
        Boolean authorization = Boolean.parseBoolean(body.get("authorization").toString());
        String username = body.get("username").toString();
        return AppService.ConfirmPurchase(authorization, username );
    }

    @PostMapping(value="/PurchaseRequest")
    public ResponseEntity<String> PurchaseRequest(@RequestBody Map<String , Object> info) {

        String username = info.get("var1").toString();
        String product = info.get("var2").toString();
        String price = info.get("var3").toString();
        long expiration = Long.parseLong(info.get("var4").toString());

        System.out.println(username);
        System.out.println(product);
        System.out.println(price);
        System.out.println(expiration);

        return AppService.PurchaseRequest(username, product, price, expiration);
    }

}