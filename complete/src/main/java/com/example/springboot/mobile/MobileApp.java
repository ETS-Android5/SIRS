package com.example.springboot.mobile;

import com.example.springboot.KeyGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.util.ArrayList;

public class MobileApp {

    private int deviceId;
    public String publicKey;
    private String privateKey;

    KeyGenerator keyGenerator = new KeyGenerator();

    public MobileApp(int id) throws GeneralSecurityException, IOException {

        deviceId = id;
        String path = "/complete/src/main/java/com/example/springboot/mobile/keys";

        ArrayList<String> keys = keyGenerator.generateKeys(deviceId, path);

        publicKey = keys.get(0);
        privateKey = keys.get(1);
    }


}
