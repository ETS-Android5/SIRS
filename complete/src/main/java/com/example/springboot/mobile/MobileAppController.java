package com.example.springboot.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;

@RestController
public class MobileAppController {

    @Autowired
    private MobileAppService mobileAppService;

<<<<<<< HEAD
    @RequestMapping(value="/Login")
    public String Register() throws GeneralSecurityException, IOException {
        System.out.println("entrei");
=======
    @RequestMapping(value="/RegisterMobile")
    public String Register() throws GeneralSecurityException, IOException, SQLException {
>>>>>>> f80a2f598dc76f6e815db6622a9379ea300a138f
        return mobileAppService.RegisterMobile();
    }
}
