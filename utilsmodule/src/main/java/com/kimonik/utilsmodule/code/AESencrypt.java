package com.kimonik.utilsmodule.code;

import android.os.Build.VERSION;
import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * * ===============================================================
 * name:             AESencrypt
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2018/1/23
 * description：
 * history：
 * *==================================================================
 */


public class AESencrypt {
    private static final String HEX = "0123456789ABCDEF";

    public AESencrypt() {
    }

    public static String encrypt(String seed, String cleartext) throws Exception {
        byte[] rawKey = getRawKey(seed.getBytes());
        byte[] result = encrypt(rawKey, cleartext.getBytes());
        return toHex(result);
    }

    public static String decrypt(String seed, String encrypted) throws Exception {
        byte[] rawKey = getRawKey(seed.getBytes());
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(rawKey, enc);
        return new String(result);
    }

    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = null;
        if(VERSION.SDK_INT >= 17) {
            sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        } else {
            sr = SecureRandom.getInstance("SHA1PRNG");
        }

        sr.setSeed(seed);
        kgen.init(128, sr);
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(1, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(2, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    public static String toHex(String txt) {
        return toHex(txt.getBytes());
    }

    public static String fromHex(String hex) {
        return new String(toByte(hex));
    }

    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];

        for(int i = 0; i < len; ++i) {
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        }

        return result;
    }

    public static String toHex(byte[] buf) {
        if(buf == null) {
            return "";
        } else {
            StringBuffer result = new StringBuffer(2 * buf.length);

            for(int i = 0; i < buf.length; ++i) {
                appendHex(result, buf[i]);
            }

            return result.toString();
        }
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append("0123456789ABCDEF".charAt(b >> 4 & 15)).append("0123456789ABCDEF".charAt(b & 15));
    }

    public static String encrypt2PHP(String key, String input) {
        byte[] crypted = null;
        Log.e("TAG", "encrypt2PHP: --1111111111key1111---"+key);
        Log.e("TAG", "encrypt2PHP: --111111111key.getBytes()11111---"+key.getBytes());

        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Log.e("TAG", "encrypt2PHP: --222222222---");
            Cipher cipher = Cipher.getInstance("AES");
            Log.e("TAG", "encrypt2PHP: --33333---");
            cipher.init(1, skey);
            crypted = cipher.doFinal(input.getBytes("UTF-8"));
        } catch (Exception var5) {
            System.out.println(var5.toString());
        }
        Log.e("TAG", "encrypt2PHP: -----"+new String(Base64.encode(crypted, 0)));


        return new String(Base64.encode(crypted, 0));
    }

    public static String decrypt2PHP(String key, String input) throws UnsupportedEncodingException {
//        byte[] output = null;//源代码
        String ot = null;

        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, skey);
            byte[] output = cipher.doFinal(Base64.decode(input, 0));
            ot = new String(output, "UTF-8");
        } catch (Exception var6) {

        }

        return ot;
    }
}

