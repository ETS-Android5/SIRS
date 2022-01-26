package com.example.springboot.helpers.totp;

import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicInteger;

import org.jetbrains.annotations.NotNull;

public final class ReseedingSecureRandom {
    private final AtomicInteger count = new AtomicInteger(0);
    private SecureRandom secureRandom = this.createSecureRandom();
    private static final int MAX_OPERATIONS = 1000000;

    public final void nextBytes(@NotNull byte[] bytes) {
        if (this.count.incrementAndGet() > 1000000) {
            synchronized(this) {
                if (this.count.get() > 1000000) {
                    this.secureRandom = this.createSecureRandom();
                    this.count.set(0);
                }

            }
        }

        this.secureRandom.nextBytes(bytes);
    }

    private final SecureRandom createSecureRandom() {
        return new SecureRandom();
    }

}
