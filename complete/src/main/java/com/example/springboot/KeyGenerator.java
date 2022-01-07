package com.example.springboot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.util.ArrayList;

public class KeyGenerator {

    public ArrayList<String> generateKeys(int deviceId, String path) throws GeneralSecurityException, IOException {

        ArrayList<String> keyPaths = new ArrayList<String>();

        File file = new File("");
        String directoryName = file.getAbsoluteFile().toString() + path;

        String publicKey = directoryName + "/publicKey" + deviceId + ".pub";
        String privateKey = directoryName + "/privateKey" + deviceId + ".priv";

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

        keyPaths.add(publicKey);
        keyPaths.add(privateKey);

        return keyPaths;
    }
}
