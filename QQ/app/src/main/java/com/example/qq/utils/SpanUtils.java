package com.example.qq.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;

import com.example.qq.R;

public class SpanUtils {
    public static SpannableString getStringSpan(Context context,String word){

        if (TextUtils.isEmpty(word)) return null;

        SpannableString spanString = new SpannableString(word);
        Drawable drawable = context.getResources().getDrawable(R.drawable.b8_);
        drawable.setBounds(0,0,50,50);
        //显示图标ImageSpan
        ImageSpan img = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
        int start = word.indexOf("[");
        int end = word.indexOf("]");
        spanString.setSpan(img,start,end+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanString;
    }

    public static SpannableString getUrlSpan(Context context,String word){

        if (TextUtils.isEmpty(word)) return null;

        int start = word.indexOf("<");
        int end = word.indexOf(">")+1;
        String str = word.substring(start,end);
        String newStr = word.substring(end+1,word.length());

        SpannableString spanString = new SpannableString(newStr);
        URLSpan urlSpan = new URLSpan("https://www.baidu.com");
        spanString.setSpan(urlSpan,start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spanString;
    }
}
