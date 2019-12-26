package com.assertion.passwordmanager.model;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class Dictionary {
    Map<String,String> dictionary = new HashMap<>();

    private static Dictionary instance;
    private Dictionary(){
        Random random=new Random();
        for (char ch = 'A'; ch <= 'Z'; ++ch)
            dictionary.put(String.valueOf(ch), String.valueOf(random.nextInt(9)));
        for (char ch = 'a'; ch <= 'z'; ++ch)
            dictionary.put(String.valueOf(ch), String.valueOf(random.nextInt(9)));
        for (int ch = 0; ch <= 9; ++ch)
            dictionary.put(String.valueOf(ch), String.valueOf((char)(random.nextInt('z' - 'a') + 'a')));
    }

    public Map<String, String> getDictionary() {
        return dictionary;
    }

    public static synchronized Dictionary getInstance() {
        if(instance == null) {
            instance = new Dictionary();
        }
        return instance;
    }

}
