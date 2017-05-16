package com.java.xdd.digest;

import org.junit.Test;

import java.security.MessageDigest;

public class SHA_256 {

    @Test
    public void test() throws Exception{
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        md.update("123456fsdafjsf56fs6ad".getBytes());

        byte[] digest = md.digest();

        String des = "";
        String tmp;
        for (int i = 0; i < digest.length; i++) {
            tmp = (Integer.toHexString(digest[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }

        System.out.println(des);
        System.out.println(des.length());
        //8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92
        //4343cefac7f18ec395d5ec9aea60b08c247ae2c6acfb572d0a6872f27b1840f1



    }
}
