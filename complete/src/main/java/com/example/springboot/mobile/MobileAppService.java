package com.example.springboot.mobile;

import org.springframework.stereotype.Service;

import javax.lang.model.element.NestingKind;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Dictionary;
import java.util.Hashtable;

@Service
public class MobileAppService {

    private Dictionary<Integer, MobileApp> mobileList= new Hashtable<Integer, MobileApp>();
    private int deviceId = 0;

    public String RegisterMobile() throws GeneralSecurityException, IOException {
        //create a deviceId
        //is received a public (and private key?) or generated

        MobileApp mobile = new MobileApp(deviceId++);

        mobileList.put(deviceId, mobile);

        return "olá";
    }
}
