package com.example.smartphoneapplication.totp;

import java.util.Arrays;

import org.jetbrains.annotations.NotNull;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;

public final class TOTPSecretKey {

    public TOTPSecretKey(byte[] value) {
        this.value = value;
    }

    @NotNull
    public byte[] value;

    public enum KeyRepresentation { BASE32, BASE64 }

    public final String to(KeyRepresentation representation) {
        if (representation == KeyRepresentation.BASE32) {
            return new Base32().encodeToString(value);
        }
        else {
            return new Base64().encodeToString(value);
        }
    }

    public String toString() {
        return this.to(KeyRepresentation.BASE32);
    }

    public boolean equals(Object other) {
        if ((TOTPSecretKey)this != other || this.getClass() != other.getClass()) {
            return false;
        } else if (other == null) {
            throw new NullPointerException("null cannot be cast to non-null type totp.TOTPSecretKey");
        } else {
            return Arrays.equals(this.value, ((TOTPSecretKey)other).value);
        }
    }

    public int hashCode() {
        return Arrays.hashCode(this.value);
    }

    public final byte[] getValue() {
        return this.value;
    }

}
