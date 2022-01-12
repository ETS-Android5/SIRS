package com.example.springboot;

import com.example.springboot.desktop.DesktopAppService;
import com.example.springboot.mobile.MobileAppService;
import com.example.springboot.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;

@RestController
public class ApplicationController {

    @Autowired
    private ApplicationService AppService;

    @PostMapping(value="/RegisterMobile")
    public String RegisterMobile(@RequestBody User userRegistration) throws SQLException, ClassNotFoundException {
        //return value is code to insert in
        return AppService.RegisterMobile(userRegistration);
    }

    @PostMapping(value="/RegisterUser")
    public String RegisterUser(@RequestBody User userRegistration) throws SQLException, ClassNotFoundException {
        //return value is code to insert in
        return AppService.RegisterUser(userRegistration);
    }

    @PostMapping(value="/RegisterWorker")
    public String RegisterWorker(@RequestBody User userRegistration) throws SQLException, ClassNotFoundException {
        return AppService.RegisterWorker(userRegistration);
    }
}