package com.example.springboot.helpers;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public class KeyGenerator {


    public static ArrayList<byte[]> generateCodes() throws NoSuchAlgorithmException, NoSuchProviderException {

        ArrayList<byte[]> codes = new ArrayList<>();

        String chrs = "0123456789abcdefghijklmnopqrstuvwxyz-_ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        SecureRandom secureRandomSharedSecret = SecureRandom.getInstanceStrong();
        // 9 is the length of the string you want B@26a7b76d
        String SharedSecret = secureRandomSharedSecret.ints(100, 0, chrs.length()).mapToObj(i -> chrs.charAt(i))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();

        codes.add(SharedSecret.getBytes());

        SecureRandom secureRandomPassCode = SecureRandom.getInstanceStrong();
        // 9 is the length of the string you want B@26a7b76d
        String passCode = secureRandomPassCode.ints(100, 0, chrs.length()).mapToObj(i -> chrs.charAt(i))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();

        codes.add(passCode.getBytes(Charset.forName("UTF-8")));

        return codes;
    }

}
