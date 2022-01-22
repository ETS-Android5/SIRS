package com.example.springboot.helpers;

import java.io.IOException;
import java.security.*;
import java.util.ArrayList;

public class KeyGenerator {


    public static ArrayList<String> generateCodes() throws NoSuchAlgorithmException, NoSuchProviderException {

        ArrayList<String> keys = new ArrayList<String>();
        SecureRandom secureRandomGenerator = null;

        try {

            String chrs = "0123456789abcdefghijklmnopqrstuvwxyz-_ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();
            // 9 is the length of the string you want
            String sharedSecret = secureRandom.ints(20, 0, chrs.length()).mapToObj(i -> chrs.charAt(i))
                    .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();

            //Get random integer in range
            //alpha and q are public
            secureRandomGenerator = SecureRandom.getInstance("SHA1PRNG", "SUN");
            int code = secureRandomGenerator.nextInt(999999);

            /*// Get 128 random bytes
            byte[] randomBytes = new byte[128];
            secureRandomGenerator.nextBytes(randomBytes);

            //Get random integer
            int r = secureRandomGenerator.nextInt();*/

            keys.add(sharedSecret);
            keys.add(Integer.toString(code));

            return keys;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return keys;
    }

    public static ArrayList<String> generateKeys(int deviceId, String path) throws GeneralSecurityException, IOException {

        ArrayList<String> keyPaths = new ArrayList<String>();

        /*File file = new File("");
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
        keyPaths.add(privateKey);*/

        return keyPaths;
    }



}
