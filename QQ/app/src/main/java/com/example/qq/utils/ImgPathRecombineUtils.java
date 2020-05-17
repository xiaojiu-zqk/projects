package com.example.qq.utils;

public class ImgPathRecombineUtils {
    public static String splitString(String avater) {

        char aa = '\\';

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < avater.length(); i++) {
            if (avater.charAt(i)  != aa){
                sb.append(avater.charAt(i));
            }
        }
        return sb.toString();
    }
}
