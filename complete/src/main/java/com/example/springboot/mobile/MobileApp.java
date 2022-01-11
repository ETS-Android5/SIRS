package com.example.springboot.mobile;

import com.example.springboot.helpers.KeyGenerator;

import java.io.IOException;
import java.security.*;
import java.util.ArrayList;

public class MobileApp {

    private int deviceId;
    public String publicKey;
    public String secretKey;

    KeyGenerator keyGenerator = new KeyGenerator(); //key generator needs to create a DH key

    public MobileApp(int id) throws GeneralSecurityException, IOException {

        deviceId = id;
        String path = "/src/main/java/com/example/springboot/mobile/keys";

        ArrayList<String> keys = keyGenerator.generateKeys(deviceId, path);

        publicKey = keys.get(0);
        secretKey = keys.get(1);

    }



}
