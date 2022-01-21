package com.example.springboot;

import com.example.springboot.user.Association;
import com.example.springboot.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

@RestController
public class ApplicationController {

    @Autowired
    private ApplicationService AppService;

    @PostMapping(value="/test", consumes = "application/json")
    public HttpEntity RegisterMobile(@RequestBody Association association) throws SQLException, ClassNotFoundException, NoSuchProviderException, NoSuchAlgorithmException, UnsupportedEncodingException {
        System.out.println(association.getRandomCode());
        HttpEntity response = new StringEntity("olá");
        return response;
    }

    @PostMapping(value="/RegisterMobile")
    public ResponseEntity<String> RegisterMobile(@RequestBody String mobile, @RequestBody String code) throws SQLException, ClassNotFoundException, NoSuchProviderException, NoSuchAlgorithmException {
        //return value is code to insert in
        return AppService.RegisterMobile(mobile, code);
    }

    @PostMapping(value="/RegisterUser")
    public ResponseEntity<String> RegisterUser(@RequestBody User userRegistration, String code) throws SQLException, ClassNotFoundException {
        //return value is code to insert in
        return AppService.RegisterUser(userRegistration, code);
    }


    /*@PostMapping(value="/SuccessRegisterMobile")
    public ResponseEntity<String> SuccessRegisterMobile(@RequestBody int mobile, int key) throws SQLException, ClassNotFoundException, NoSuchProviderException, NoSuchAlgorithmException {
        //return value is code to insert in
        return AppService.SuccessRegisterMobile(mobile, key);
    }*/
/*
    public ResponseEntity<ArrayList<Integer>> LoginMobile(@RequestBody int mobile, @RequestBody String code) throws SQLException, ClassNotFoundException, NoSuchProviderException, NoSuchAlgorithmException {
        //return value is code to insert in
        return AppService.RegisterMobile(mobile, code);
    }*/

    /*@PostMapping(value="/RegisterWorker")
    public String RegisterWorker(@RequestBody User userRegistration, @RequestBody String code ) throws SQLException, ClassNotFoundException {
        return AppService.RegisterWorker(userRegistration, code);
    }*/
}