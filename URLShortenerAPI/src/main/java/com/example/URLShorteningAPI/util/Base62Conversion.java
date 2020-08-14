package com.example.URLShorteningAPI.util;

import org.springframework.stereotype.Component;
import java.util.Random;

@Component
public class Base62Conversion {
    private String baseChars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private Random rand = new Random();
    private static final int FIXED_LENGTH = 6;

    public String encode(){
        StringBuilder sb = new StringBuilder();
        for(int i =0; i<FIXED_LENGTH; i++){
            sb.append(baseChars.charAt(rand.nextInt(baseChars.length())));
        }
        return sb.toString();
    }
}
