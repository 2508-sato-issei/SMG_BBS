package com.example.SMG_BBS.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CipherUtil {

    /*
     * SHA-256で暗号化し、バイト配列をBase64エンコーディングします。
     * @param target 暗号化対象の文字列
     * @return 暗号化された文字列
     */
    public static String encrypt(String target) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(target.getBytes());
            return Base64.encodeBase64URLSafeString(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}