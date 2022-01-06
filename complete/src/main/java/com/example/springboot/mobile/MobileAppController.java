package com.example.springboot.mobile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MobileAppController {

    @Autowired
    private MobileAppService mobileAppService;

    @RequestMapping(value="/Login")
    public String Login() {
        return mobileAppService.RegisterMobile();
    }
}
