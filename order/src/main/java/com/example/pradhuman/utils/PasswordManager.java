package com.example.pradhuman.utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordManager {

        static final String algorithm = "AES/ECB/PKCS5Padding";
        static final String secret_key = "4gGNHy1n9Xc5GibNT1pnpA==";


    public static String encrypt(String password, SecretKey key) throws GeneralSecurityException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherText = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);

    }

    public static String decrypt(String cipherText, SecretKey key)
            throws GeneralSecurityException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(plainText);

    }

    /**
     *  Sample test encrypt decrypt password
     *

    public static void main(String[] args) {
        try {
            String cypher = encrypt("1234",getSecretKey());
            System.out.println(cypher + "    This is cyper old 1nxB05NrGkHwqMbA8xizfw==");
            System.out.println(decrypt(cypher,getSecretKey()));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

     */

    /**
     * method to generate new secret key if isDestroyed is true
     */
    public static String generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey key = keyGenerator.generateKey();

        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public static SecretKey getSecretKey(){
        byte[] decodedKey = Base64.getDecoder().decode(secret_key);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

}
