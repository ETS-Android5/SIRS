package com.example.springboot.helpers.totp;

import java.util.List;
import org.jetbrains.annotations.NotNull;

public final class TOTPCredentials {
    @NotNull
    private final TOTPSecretKey secretKey;
    private int verificationCode = 0;

    @NotNull
    private final List scratchCodes;

    @NotNull
    public final TOTPSecretKey getSecretKey() {
        return this.secretKey;
    }

    public final int getVerificationCode() {
        return this.verificationCode;
    }

    @NotNull
    public final List getScratchCodes() {
        return this.scratchCodes;
    }

    public TOTPCredentials(@NotNull TOTPSecretKey secretKey, int verificationCode, @NotNull List scratchCodes) {
        this.secretKey = secretKey;
        this.verificationCode = verificationCode;
        this.scratchCodes = scratchCodes;
    }

}
