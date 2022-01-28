package com.example.springboot;

import com.example.springboot.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;
import java.util.Map;

@CrossOrigin( origins = {  "http://192.168.37.4:3000", "http://localhost:3000" })
@RestController
public class ApplicationController {

    @Autowired
    private ApplicationService AppService;

    @PostMapping(value="/RegisterMobile", consumes = "application/json")
    public String RegisterMobile(@RequestBody Map<String, Object> body) throws SQLException, ClassNotFoundException, NoSuchProviderException, NoSuchAlgorithmException {
        //return value is code to insert in
        String randomCode = body.get("randomCode").toString();
        String sharedSecret = body.get("sharedSecret").toString();

        System.out.println("Recieved Accossiation request From Mobile with");
        System.out.println("Random Code- " + randomCode);
        System.out.println("Shared Secret- " +sharedSecret);
        System.out.println("\n");

        return AppService.RegisterMobile(randomCode, sharedSecret);
    }

    @PostMapping(value="/RegisterUser")
    public ResponseEntity<String> RegisterUser(@RequestBody Map<String , Object> payload) throws SQLException, ClassNotFoundException {
        //return value is code to insert in
        String username = payload.get("var1").toString();
        int passwordHash = payload.get("var2").toString().hashCode();
        String code = payload.get("var3").toString();
        
        System.out.println("Recieved Registration From Desktop with");
        System.out.println("User- " + username);
        System.out.println("Password- " + payload.get("var2").toString() );
        System.out.println("Code- " +code);
        System.out.println("\n");
        
        User userRegistration = new User(username, passwordHash);
 
        return AppService.RegisterUser(userRegistration, code);
    }

    @PostMapping(value="/LogIn")
    public ResponseEntity<String> UserLogIn(@RequestBody Map<String , Object> payload) throws SQLException, ClassNotFoundException, NoSuchProviderException, NoSuchAlgorithmException {
        
        String username = payload.get("var1").toString() ;
        int passwordHash = payload.get("var2").toString().hashCode();
        int code = Integer.parseInt( payload.get("var3").toString() );

        System.out.println("Recieved Login From Desktop with");
        System.out.println("User- " + username);
        System.out.println("Password- " + payload.get("var2").toString() );
        System.out.println("Code- " +code);
        System.out.println("\n");
        
        
        return AppService.Login(username, passwordHash ,code);
    }

    @PostMapping(value="/LogOut")
    public ResponseEntity<String> UserLogOut(@RequestBody Map<String , Object> payload) throws SQLException, ClassNotFoundException, NoSuchProviderException, NoSuchAlgorithmException {
        
        String username = payload.get("var1").toString();
        
        System.out.println("Recieved LogOut From Desktop with");
        System.out.println("User- " + username);
        System.out.println("\n");

        return AppService.Logout(username);
    }

    @PostMapping(value="/RefreshPurchase")
    public String RefreshPurchase(@RequestBody Map<String, Object> body) throws SQLException {
        String username = body.get("username").toString();
        Integer totp = Integer.parseInt(body.get("totp").toString());
        
        System.out.println("Recieved Refresh Purchase request From Mobile with");
        System.out.println("username- " + username);
        System.out.println("TOTP- " + totp);
        System.out.println("\n");

        return AppService.RefreshPurchase(username, totp);
    }

    @PostMapping(value="/ConfirmPurchase")
    public String ConfirmPurchase(@RequestBody Map<String, Object> body) throws SQLException {
        Boolean authorization = Boolean.parseBoolean(body.get("authorization").toString());
        String username = body.get("username").toString();

        System.out.println("Recieved Confirm Purchase request From Mobile with");
        System.out.println("Authorization- " + authorization);
        System.out.println("username- " + username );
        System.out.println("\n");

        return AppService.ConfirmPurchase(authorization, username );
    }

    @PostMapping(value="/PurchaseRequest")
    public ResponseEntity<String> PurchaseRequest(@RequestBody Map<String , Object> info) {

        String username = info.get("var1").toString();
        String product = info.get("var2").toString();
        String price = info.get("var3").toString();
        long expiration = Long.parseLong(info.get("var4").toString());

        System.out.println("Recieved LogOut From Desktop with");
        System.out.println("User- " + username);
        System.out.println("Product- " +product);
        System.out.println("Price- " +price);
        System.out.println("Expiration- " +expiration);
        System.out.println("\n");

        return AppService.PurchaseRequest(username, product, price, expiration);
    }

}