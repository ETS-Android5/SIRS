package com.example.springboot.helpers;

import java.io.IOException;
import java.security.*;
import java.util.ArrayList;

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

            //Get random integer in range
            //alpha and q are public
            secureRandomGenerator = SecureRandom.getInstance("SHA1PRNG", "SUN");
            int code = secureRandomGenerator.nextInt(999999);

            codes.add(sharedSecret);
            codes.add(Integer.toString(code));

            return codes;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return keys;
    }

}
