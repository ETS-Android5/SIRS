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

    @PostMapping(value="/RegisterUser")
    public String Register(@RequestBody User userRegistration) throws GeneralSecurityException, IOException, SQLException, ClassNotFoundException {
        return AppService.RegisterUser(userRegistration);
    }
}