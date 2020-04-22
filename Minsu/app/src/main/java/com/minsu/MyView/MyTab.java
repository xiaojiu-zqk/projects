package com.minsu.MyView;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.minsu.R;

public class MyTab extends ConstraintLayout implements View.OnClickListener {

    private TypedArray typedArray;
    private String txtleftstr;
    private String txtrightstr;
    private TextView txtright;
    private TextView txtleft;
    private Context context;
    public Addonclick addonclick;

    public void setAddonclick(Addonclick addonclick) {
        this.addonclick = addonclick;
    }

    public MyTab(Context context) {
        super(context);
        this.context = context;
    }

    public MyTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTab);
        txtleftstr = typedArray.getString(R.styleable.MyTab_txt_left);
        txtrightstr = typedArray.getString(R.styleable.MyTab_txt_right);
        //调用recycle()使typearray进行回收 以便下次使用
        typedArray.recycle();
    }

    public MyTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTab);
        txtleftstr = typedArray.getString(R.styleable.MyTab_txt_left);
        txtrightstr = typedArray.getString(R.styleable.MyTab_txt_right);
        //调用recycle()使typearray进行回收 以便下次使用
        typedArray.recycle();
    }

    //初始化控件
    public void initview() {
        txtleft = findViewById(R.id.txt_left);
        txtright = findViewById(R.id.txt_right);
        if (!TextUtils.isEmpty(txtleftstr)) txtleft.setText(txtleftstr);
        if (!TextUtils.isEmpty(txtrightstr)) txtright.setText(txtrightstr);
        txtleft.setOnClickListener(this);
        txtright.setOnClickListener(this);
    }

    //初始化控件设置
    public void initviewtype() {
        txtleft.setBackgroundResource(R.drawable.tab_default_left);
        txtright.setBackgroundResource(R.drawable.tab_default_right);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_left:
                addonclick.onclick(0);
                initviewtype();
                txtleft.setBackgroundResource(R.drawable.tab_select_left);
                break;
            case R.id.txt_right:
                addonclick.onclick(1);
                initviewtype();
                txtright.setBackgroundResource(R.drawable.tab_select_right);
                break;
        }
    }

//接口回调
    public interface Addonclick {
        void onclick(int msg);
    }
}
