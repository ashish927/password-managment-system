package com.assertion.passwordmanager.util;


import com.assertion.passwordmanager.model.Dictionary;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class PasswordUtil {
    private static final String key = "aesEncryptionKey";
    private static final String initVector = "encryptionIntVec";
    private static final int OFFSET = "AAAAAAA".hashCode();
    static Map<String, String> map ;
    static Set<String> keySet ;

    static Map<String,Map> storage;
    static {
        Dictionary dictionary = Dictionary.getInstance();
         map = dictionary.getDictionary();
         keySet = map.keySet();
         storage = new HashMap<>();
    }

    public static String generateCommonLangPassword() {
        String upperCaseLetters = RandomStringUtils.random(5, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(5, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(5);
        String specialChar = RandomStringUtils.random(5, 33, 47, false, false);
        String totalChars = RandomStringUtils.randomAlphanumeric(5);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
                .concat(numbers)
                .concat(specialChar)
                .concat(totalChars);
        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        String password = pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        return password;
    }

    public static String createCodedPasswordString(String input) {
        String pass = input;
        Map<Integer, String> valueTrack = new HashMap<>();
        StringBuilder sb = new StringBuilder(pass);
        for (int i = 0; i < pass.length(); i++) {
            if (keySet.contains(String.valueOf(sb.charAt(i)))) {
                char val = map.get(String.valueOf(sb.charAt(i))).charAt(0);
                valueTrack.put(i, String.valueOf(sb.charAt(i)));
                sb.setCharAt(i, val);
            } else {
                sb.setCharAt(i, sb.charAt(i));
            }
        }
        storage.put(getStringForHashCode(sb.toString().hashCode()), valueTrack);
        return sb.toString();
    }

    public static String createDecodedPasswordString(String input) {
        String pass = input;
        Map<Integer, String> valueTrack = storage.get(getStringForHashCode(pass.hashCode()));
        Set trackMapKey = valueTrack.keySet();
        StringBuilder sb = new StringBuilder(pass);
        for (int i = 0; i < pass.length(); i++) {
            if (trackMapKey.contains(i)) {
                char val = valueTrack.get(i).charAt(0);
                sb.setCharAt(i, val);
            } else {
                sb.setCharAt(i, sb.charAt(i));
            }
        }
        return sb.toString();
    }

    public static String encrypt(String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private static String getStringForHashCode(int hash) {
        hash -= OFFSET;
        long longHash = (long) hash & 0xFFFFFFFFL;

        char[] c = new char[7];
        for (int i = 0; i < 7; i++)
        {
            c[6 - i] = (char) ('A' + (longHash % 31));
            longHash /= 31;
        }
        return new String(c);
    }
}
