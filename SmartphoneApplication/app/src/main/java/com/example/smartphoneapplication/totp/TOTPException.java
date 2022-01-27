package com.example.smartphoneapplication.totp;

import org.jetbrains.annotations.NotNull;

public final class TOTPException extends Exception {
    public TOTPException(@NotNull String message, @NotNull Exception cause) {
        super(message, (Throwable)cause);
    }
}
