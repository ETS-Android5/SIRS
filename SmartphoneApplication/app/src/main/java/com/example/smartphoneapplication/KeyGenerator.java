package com.example.smartphoneapplication;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;

public class KeyGenerator {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static ArrayList<String> generateCodes() throws NoSuchAlgorithmException, NoSuchProviderException {



        ArrayList<String> codes = new ArrayList<>();

        String chrs = "0123456789abcdefghijklmnopqrstuvwxyz-_ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        SecureRandom secureRandomSharedSecret = SecureRandom.getInstanceStrong();
        // 9 is the length of the string you want B@26a7b76d
        String sharedSecret = secureRandomSharedSecret.ints(100, 0, chrs.length()).mapToObj(i -> chrs.charAt(i))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();

        codes.add(sharedSecret);

        SecureRandom secureRandomPassCode = SecureRandom.getInstanceStrong();
        // 9 is the length of the string you want B@26a7b76d

        String passCode = secureRandomPassCode.ints(10, 0, chrs.length()).mapToObj(i -> chrs.charAt(i))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();

        codes.add(passCode);

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
