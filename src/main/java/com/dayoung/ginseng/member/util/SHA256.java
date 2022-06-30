package com.dayoung.ginseng.member.util;

import com.dayoung.ginseng.member.exception.EncryptAlgorithmFailException;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class SHA256 implements EncryptAlgorithm {
    public String encrypt(String text) {
        MessageDigest md;
        try{
            md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());
        } catch(NoSuchAlgorithmException e){
            throw new EncryptAlgorithmFailException(e);
        }

        return bytesToHex(md.digest());
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
