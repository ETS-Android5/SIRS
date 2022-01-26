package com.example.springboot.helpers;

import java.io.IOException;
import java.security.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import de.taimos.totp.TOTP;

public class KeyGenerator {


    public static ArrayList<String> generateCodes() throws NoSuchAlgorithmException, NoSuchProviderException {

        ArrayList<String> codes = new ArrayList<String>();
        SecureRandom secureRandomGenerator = null;

        try {
            String chrs = "0123456789abcdefghijklmnopqrstuvwxyz-_ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();
            // 9 is the length of the string you want
            String sharedSecret = secureRandom.ints(20, 0, chrs.length()).mapToObj(i -> chrs.charAt(i))
                    .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();

            // Get 1024 random bytes
            byte[] code = new byte[1024];
            secureRandomGenerator.nextBytes(code);

            codes.add(sharedSecret);
            codes.add(Arrays.toString(code));

            return codes;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return codes;
    }

    /*public static String getTOTPCode(String secretKey) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);
        return TOTP.getOTP(hexKey);
    }

    public static void infinityGeneratingCodes(String sharedSecret) {
        String lastCode = null;
        while (true) {
            String code = getTOTPCode(sharedSecret);
            if (!code.equals(lastCode)) {
                System.out.println(code);
            }
            lastCode = code;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {};
        }
    }*/


}
