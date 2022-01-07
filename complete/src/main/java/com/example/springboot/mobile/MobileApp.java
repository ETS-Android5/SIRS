package com.example.springboot.mobile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;

public class MobileApp {

    private int deviceId;
    public String publicKey;
    private String privateKey;

    public MobileApp(int id) throws GeneralSecurityException, IOException {
        deviceId = id;
        this.generateKeys();
    }

    public void generateKeys() throws GeneralSecurityException, IOException {
        File file = new File("");
        String directoryName = file.getAbsoluteFile().toString() + "/complete/src/main/java/com/example/springboot/mobile/keys";

        publicKey = directoryName + "/publicKey" + deviceId + ".pub";
        privateKey = directoryName + "/privateKey" + deviceId + ".priv";

        System.out.println("Generating RSA key ..." );
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        KeyPair keys = keyGen.generateKeyPair();

        System.out.println("Private Key:");
        PrivateKey privKey = keys.getPrivate();
        byte[] privKeyEncoded = privKey.getEncoded();
        //System.out.println(printHexBinary(privKeyEncoded));
        System.out.println("Public Key:");
        PublicKey pubKey = keys.getPublic();
        byte[] pubKeyEncoded = pubKey.getEncoded();
        //System.out.println(printHexBinary(pubKeyEncoded));

        System.out.println("Writing Private key to '" + privateKey + "' ..." );
        FileOutputStream privFos = new FileOutputStream(privateKey);
        privFos.write(privKeyEncoded);
        privFos.close();
        System.out.println("Writing Pubic key to '" + publicKey + "' ..." );
        FileOutputStream pubFos = new FileOutputStream(publicKey);
        pubFos.write(pubKeyEncoded);
        pubFos.close();
    }

}
