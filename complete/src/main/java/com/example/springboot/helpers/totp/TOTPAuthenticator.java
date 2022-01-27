package com.example.springboot.helpers.totp;

import org.jetbrains.annotations.NotNull;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.Duration;

public final class TOTPAuthenticator {
    private final ReseedingSecureRandom secureRandom;
    private final TOTPConfig config  ;

    
    private static final int SECRET_BITS = 80;
    private static final int SCRATCH_CODES = 5;
    private static final int SCRATCH_CODE_LENGTH = 8;
    private static final int SCRATCH_CODE_MODULUS = (int)Math.pow(10.0D, (double)8);
    private static final int SCRATCH_CODE_INVALID = -1;
    private static final int BYTES_PER_SCRATCH_CODE = 4;
    private static final String HMAC_HASH_FUNCTION = "HmacSHA1";
    
    public TOTPAuthenticator() throws Throwable {
        
            secureRandom = new ReseedingSecureRandom();
            config = new TOTPConfig(Duration.ofSeconds(30), 15, SCRATCH_CODE_LENGTH, SCRATCH_CODE_MODULUS);
        
    }


    @NotNull
    public final TOTPCredentials createCredentials() throws Throwable {
        // Allocating a buffer sufficiently large to hold the bytes required by the secret key and the scratch codes.
        byte[] buffer = new byte[30];   //SECRET_BITS / 8 + SCRATCH_CODES * BYTES_PER_SCRATCH_CODE
        this.secureRandom.nextBytes(buffer);
        
        byte[] key = Arrays.copyOf(buffer, 10);    //SECRET_BITS / 8
        int validationCode = this.calculateValidationCode(key);
        List scratchCodes = this.calculateScratchCodes(buffer);
        return new TOTPCredentials(new TOTPSecretKey(key), validationCode, scratchCodes);
    }

    @NotNull
    public final TOTPSecretKey createSecretKey() throws Throwable {
        return (new TOTPAuthenticator()).createCredentials().getSecretKey();
    }

    public final boolean authorize(@NotNull TOTPSecretKey secretKey, int totp, @NotNull Instant time) {
        // Checking if the verification code is between the legal bounds.

        if (totp <= 0 || totp >= config.getKeyModulus()) {
            System.out.println(SCRATCH_CODE_MODULUS);
            System.out.println("Ja?");
            return false;
        }
        return checkCode(secretKey.getValue(), totp, time, this.config.getWindowSize());
    }

    public final int createOneTimePassword(@NotNull TOTPSecretKey secretKey, @NotNull Instant time) throws Throwable {
        return this.calculateCode(secretKey.getValue(), this.getTimeWindowFromTime(time));
    }

    /**
     * Calculates the verification code of the provided key at the specified
     * instant of time using the algorithm specified in RFC 6238.
     *
     * @param key the secret key in binary format.
     * @param timestamp the instant of time.
     * @return the validation code for the provided key at the specified instant of time.
     */

    private final int calculateCode(byte[] key, long timestamp) throws Throwable {
        // Converting the instant of time from the long representation to a  big-endian array of bytes (RFC4226, 5.2. Description).
        byte[] bigEndianTimestamp = new byte[8];
        long value = timestamp;

        for(int var7 = 8; var7-- > 0; value >>>= 8) {
            bigEndianTimestamp[var7] = (byte)((int)value);
        }

        SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");

        try {
            // Getting an HmacSHA1 algorithm implementation from the JCE.
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signKey);

            // Processing the instant of time and getting the encrypted data.
            byte[] hash = mac.doFinal(bigEndianTimestamp);

            // Building the validation code performing dynamic truncation (RFC4226, 5.3. Generating an HOTP value)
            byte offset = (byte)(hash[hash.length - 1] & 15);
            long truncatedHash = 0L;
            int i = 0;

            for(byte byteVar = 3; i <= byteVar; ++i) {
                truncatedHash <<= 8;

                // Java bytes are signed but we need an unsigned integer: cleaning off all but the LSB.
                truncatedHash |= (long)(hash[offset + i] & 255);
            }

            // Clean bits higher than the 32nd (inclusive) and calculate the module with the maximum validation code value.
            truncatedHash &= 2147483647L;
            truncatedHash %= this.config.getKeyModulus();

            return (int)truncatedHash;

        } catch (NoSuchAlgorithmException var16) {
            throw (Throwable)(new TOTPException("The operation cannot be performed now.", (Exception)var16));
        } catch (InvalidKeyException var17) {
            throw (Throwable)(new TOTPException("The operation cannot be performed now.", (Exception)var17));
        }
    }

    /**
     * This method implements the algorithm specified in RFC 6238 to check if a
     * validation code is valid in a given instant of time for the given secret key.
     *
     * @param key encoded secret key.
     * @param code the code to validate.
     * @param timestamp the instant of time to use during the validation process.
     * @param window the window size to use during the validation process.
     * @return `true` if the validation code is valid, `false` otherwise.
     */

    private final boolean checkCode(byte[] key, int code, Instant timestamp, int window) {
        // convert unix time into a 30 second "window" as specified by the
        // TOTP specification. Using Google's default interval of 30 seconds.
        long timeWindow = this.getTimeWindowFromTime(timestamp);

        // Calculating the verification code of the given key in each of the
        // time intervals and returning true if the provided code is equal to
        // one of them.
        int start = -((window - 1) / 2);
        int end = window / 2;
        //int i = start;
        //int var10 = end;
        //if (start <= end) {
            System.out.println("code-");
            System.out.println(code);
        while(start <= end) {
            int hash = 0;

            try {
                hash = this.calculateCode(key, timeWindow + (long)start);
                System.out.println("hash-");
                System.out.println(hash);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

            if (hash == code) {
                return true;
            }
            ++start;
        }
        //}

        return false;
    }

    /**
     * This method calculates the validation code at time 0.
     *
     * @param key The secret key to use.
     * @return the validation code at time 0.
     */
    private final int calculateValidationCode(byte[] key) {
        try {
            return this.calculateCode(key, 0L);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return 0;
    }

    private final long getTimeWindowFromTime(Instant time) {
        return time.toEpochMilli() / this.config.getTimeStepSize().toMillis();
    }

    private final List calculateScratchCodes(byte[] buffer) throws Throwable {
        ArrayList scratchCodes = new ArrayList<Integer>();

        while(scratchCodes.size() < 5) {
            byte[] scratchCodeBuffer = Arrays.copyOfRange(buffer, 10 + 4 * scratchCodes.size(), 10 + 4 * scratchCodes.size() + 4);
            int scratchCode = this.calculateScratchCode(scratchCodeBuffer);
            if (scratchCode != -1) {
                scratchCodes.add(scratchCode);
            } else {
                scratchCodes.add(this.generateScratchCode());
            }
        }

        return scratchCodes;
    }

    /**
     * This method creates a new random byte buffer from which a new scratch
     * code is generated. This function is invoked if a scratch code generated
     * from the main buffer is invalid because it does not satisfy the scratch
     * code restrictions.
     *
     * @return A valid scratch code.
     */

    private final int generateScratchCode() throws Throwable {
        int scratchCode;
        do {
            byte[] scratchCodeBuffer = new byte[4];
            this.secureRandom.nextBytes(scratchCodeBuffer);
            scratchCode = this.calculateScratchCode(scratchCodeBuffer);
        } while(scratchCode == -1);

        return scratchCode;
    }

    /**
     * This method calculates a scratch code from a random byte buffer of
     * suitable size `#BYTES_PER_SCRATCH_CODE`.
     *
     * @param scratchCodeBuffer a random byte buffer whose minimum size is `#BYTES_PER_SCRATCH_CODE`.
     * @return the scratch code.
     */
    private final int calculateScratchCode(byte[] scratchCodeBuffer) throws Throwable {
        if (scratchCodeBuffer.length < 4) {
            throw (Throwable)(new IllegalArgumentException("The provided random byte buffer is too small " + scratchCodeBuffer.length + '.'));
        } else {
            int scratchCode = 0;
            int i = 0;

            for(byte byteVar = 4; i < byteVar; ++i) {
                scratchCode = scratchCode << 8 + (byte)(scratchCodeBuffer[i] & (byte)255);
            }

            scratchCode = (scratchCode & Integer.MAX_VALUE) % SCRATCH_CODE_MODULUS;
            return this.validateScratchCode(scratchCode) ? scratchCode : -1;
        }
    }

    private final boolean validateScratchCode(int scratchCode) {
        return scratchCode >= SCRATCH_CODE_MODULUS / 10;
    }

}
