package com.example.springboot.desktop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
public class DesktopAppController {

    @Autowired
    private DesktopAppService desktopAppService;

    @RequestMapping(value="/LoginDesktop")
    public String Register() throws GeneralSecurityException, IOException {
        return desktopAppService.RegisterDesktop();
    }
}
