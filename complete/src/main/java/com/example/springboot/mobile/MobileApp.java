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
<<<<<<< HEAD
        /*String path = "/complete/src/main/java/com/example/springboot/mobile/keys";
=======
        String path = "/src/main/java/com/example/springboot/mobile/keys";
>>>>>>> f80a2f598dc76f6e815db6622a9379ea300a138f

        ArrayList<String> keys = keyGenerator.generateKeys(deviceId, path);

        publicKey = keys.get(0);
<<<<<<< HEAD
        privateKey = keys.get(1);*/
=======
        secretKey = keys.get(1);

>>>>>>> f80a2f598dc76f6e815db6622a9379ea300a138f
    }



}
