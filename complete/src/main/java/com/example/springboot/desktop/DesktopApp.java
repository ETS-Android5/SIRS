package com.example.springboot.desktop;

import com.example.springboot.helpers.KeyGenerator;

import java.io.IOException;
import java.security.*;
import java.util.ArrayList;

public class DesktopApp {

    private int deviceId;
    public String publicKey;
    private String privateKey;

    KeyGenerator keyGenerator = new KeyGenerator();

    public DesktopApp(int id) throws GeneralSecurityException, IOException {

        deviceId = id;
        String path = "/complete/src/main/java/com/example/springboot/desktop/keys";

        ArrayList<String> keys = keyGenerator.generateCodes();

        publicKey = keys.get(0);
        privateKey = keys.get(1);

    }



}
