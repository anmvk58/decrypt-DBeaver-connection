package com.anmv.service;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class DecryptDBeaver {

    private static final byte[] LOCAL_KEY_CACHE = new byte[] {
            -70, -69, 74, -97, 119, 74, -72, 83, -55, 108, 45, 101, 61, -2, 84, 74
    };

    public static String decrypt(byte[] contents) throws InvalidAlgorithmParameterException,
            InvalidKeyException,
            IOException,
            NoSuchPaddingException,
            NoSuchAlgorithmException {
        try (InputStream byteStream = new ByteArrayInputStream(contents)) {
            byte[] buffer = new byte[16];
            byteStream.read(buffer);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKey aes = new SecretKeySpec(LOCAL_KEY_CACHE, "AES");
            cipher.init(Cipher.DECRYPT_MODE, aes, new IvParameterSpec(buffer));

            try (CipherInputStream cipherIn = new CipherInputStream(byteStream, cipher);
                    Scanner s = new Scanner(cipherIn).useDelimiter("\\A")) {
                return s.hasNext() ? s.next() : "";
            }
        }
    }

}
