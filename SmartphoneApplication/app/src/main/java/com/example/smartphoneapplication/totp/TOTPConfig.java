package com.example.smartphoneapplication.totp;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Duration;
import org.jetbrains.annotations.NotNull;

/*
 * Configuration class for Google OTP Authenticator.
 *
 * @param timeStepSize time step size as specified by RFC 6238. The default value is 30.000.
 * @param windowSize value representing the number of windows of size timeStepSize that are checked during the
 * validation process, to account for differences between the server and the client clocks. The bigger the window,
 * the more tolerant the library code is about clock skews.
 * @param codeDigits number of digits in the generated code.
 * @param keyModulus key module.
 */
public final class TOTPConfig {
    @NotNull
    private final Duration timeStepSize;
    private final int windowSize;
    private final int codeDigits;
    private final long keyModulus;

    public final Duration getTimeStepSize() {
        return this.timeStepSize;
    }

    public final int getWindowSize() {
        return this.windowSize;
    }

    public final int getCodeDigits() {
        return this.codeDigits;
    }

    public final long getKeyModulus() {
        return this.keyModulus;
    }

    public TOTPConfig() {
        this.timeStepSize = Duration.ofSeconds(30);
        this.codeDigits = 7;
        this.keyModulus = 0L;
        this.windowSize = 15;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TOTPConfig(@NotNull Duration timeStepSize, int windowSize, int codeDigits, long keyModulus) throws Throwable {
        //Intrinsics.checkNotNullParameter(timeStepSize, "timeStepSize");

        this.timeStepSize = timeStepSize;
        this.windowSize = windowSize;
        this.codeDigits = codeDigits;
        this.keyModulus = keyModulus;
        if (this.windowSize <= 0) {
            throw (Throwable)(new IllegalArgumentException("Window number must be positive."));
        } else {
            if (this.codeDigits < 6) {
                throw (Throwable)(new IllegalArgumentException("The minimum number of digits is 6."));
            } else {
                if (this.codeDigits > 8) {
                    throw (Throwable)(new IllegalArgumentException("The maximum number of digits is 8."));
                } else {
                    if (!(!this.timeStepSize.isNegative() && !this.timeStepSize.isZero())) {
                        throw (Throwable)(new IllegalArgumentException("Time step size must be positive."));
                    }
                }
            }
        }
    }
}
