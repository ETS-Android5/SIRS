package com.example.springboot;

import com.example.springboot.user.User;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.json.JSONObject;

@RestController
public class ApplicationController {

    @Autowired
    private ApplicationService AppService;

    @PostMapping(value="/test")
    public HttpEntity RegisterMobile(@RequestBody JSONObject object) throws SQLException, ClassNotFoundException, NoSuchProviderException, NoSuchAlgorithmException {
        //System.out.println(body.getJSONArray("sharedSecret"));
        return new HttpEntity() {
        };
    }

    @PostMapping(value="/RegisterMobile")
    public ResponseEntity<ArrayList<Integer>> RegisterMobile(@RequestBody int mobile, @RequestBody String code) throws SQLException, ClassNotFoundException, NoSuchProviderException, NoSuchAlgorithmException {
        //return value is code to insert in
        return AppService.RegisterMobile(mobile, code);
    }

    @PostMapping(value="/RegisterUser")
    public ResponseEntity<String> RegisterUser(@RequestBody User userRegistration, String code) throws SQLException, ClassNotFoundException {
        //return value is code to insert in
        return AppService.RegisterUser(userRegistration, code);
    }


    @PostMapping(value="/SuccessRegisterMobile")
    public ResponseEntity<String> SuccessRegisterMobile(@RequestBody int mobile, int key) throws SQLException, ClassNotFoundException, NoSuchProviderException, NoSuchAlgorithmException {
        //return value is code to insert in
        return AppService.SuccessRegisterMobile(mobile, key);
    }
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