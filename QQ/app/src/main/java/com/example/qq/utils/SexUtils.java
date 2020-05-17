package com.example.qq.utils;

public class SexUtils {

    public static String getSex(int sex){
        if (sex == 1){
            return "男";
        }else if (sex == 2){
            return "女";
        }else {
            return "无";
        }
    }

}
