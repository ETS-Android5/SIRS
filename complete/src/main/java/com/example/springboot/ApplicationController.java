package com.example.springboot;

import com.example.springboot.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;
import java.util.ArrayList;


@RestController
public class ApplicationController {

    @Autowired
    private ApplicationService AppService;

    @PostMapping(value="/RegisterMobile")
    public ResponseEntity<String> RegisterMobile(@RequestBody String code, @RequestBody String sharedSecret) throws SQLException, ClassNotFoundException, NoSuchProviderException, NoSuchAlgorithmException {
        //return value is code to insert in
        return AppService.RegisterMobile(code, sharedSecret);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value="/RegisterUser")
    /*public ResponseEntity<String> RegisterUser(@RequestBody User userRegistration, String code) throws SQLException, ClassNotFoundException {
        //return value is code to insert in
        System.out.println("no register");
        return AppService.RegisterUser(userRegistration, code);
    }*/

    public String RegisterUser(@RequestBody User user, String code) {
        System.out.println("no register");
        return "code recebido";
    }

    public void LoginMobile(@RequestBody int mobile, @RequestBody String code) throws SQLException, ClassNotFoundException, NoSuchProviderException, NoSuchAlgorithmException {
        //return value is code to insert in
        //return AppService.LoginMobile(mobile, code);
    }

    /*@PostMapping(value="/RegisterWorker")
    public String RegisterWorker(@RequestBody User userRegistration, @RequestBody String code ) throws SQLException, ClassNotFoundException {
        return AppService.LoginWorker(userRegistration, code);
    }*/
}