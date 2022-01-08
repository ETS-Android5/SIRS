package com.example.springboot.desktop;
import com.example.springboot.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
public class DesktopAppController {

    @Autowired
    private DesktopAppService desktopAppService;

    @PostMapping(value="/LoginDesktop")
    public String Register(@RequestBody User userRegistration) throws GeneralSecurityException, IOException {
        return desktopAppService.RegisterDesktop(userRegistration);
    }
}
