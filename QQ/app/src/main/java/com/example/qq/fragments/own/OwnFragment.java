package com.example.qq.fragments.own;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.qq.R;
import com.example.qq.activitys.LoginActivity;
import com.example.qq.activitys.UpdateInfoActivity;
import com.example.qq.base.BaseFragment;
import com.example.qq.bean.UserBean;
import com.example.qq.interfaces.IPresenter;
import com.example.qq.interfaces.user.UserConstract;
import com.example.qq.presenter.UserInfoPresenter;
import com.example.qq.utils.SpUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class OwnFragment extends BaseFragment {
    @BindView(R.id.img_us_header)
    ImageView imgUsHeader;
    @BindView(R.id.text_us_nikename)
    TextView textUsNikename;
    @BindView(R.id.go_login)
    Button goLogin;
    @BindView(R.id.signature)
    TextView signature;
    @BindView(R.id.text_us_updateinfo)
    TextView textUsUpdateinfo;

    @Override
    protected int getLayout() {
        return R.layout.fragment_own;
    }

    @Override
    protected void initView(View view) {
        String token = SpUtils.getInstance().getString("token");
        if (TextUtils.isEmpty(token)){
            goLogin.setVisibility(View.VISIBLE);
        }else {
            goLogin.setVisibility(View.GONE);
            String username = SpUtils.getInstance().getString("username");
            String avater = SpUtils.getInstance().getString("avater");
            if (!TextUtils.isEmpty(username)){
                textUsNikename.setText(username);
            }
            if (!TextUtils.isEmpty(avater)){
                Glide.with(context).load(avater).into(imgUsHeader);
            }




        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//说明是 从 登录界面返回的
        if (requestCode == 300 && resultCode == 400) {
            goLogin.setVisibility(View.GONE);
            signature.setVisibility(View.VISIBLE);
            signature.setText("没有签名");
            String username = SpUtils.getInstance().getString("username");
            String avater = SpUtils.getInstance().getString("avater");
            textUsNikename.setText(username);
            Glide.with(context).load(avater).into(imgUsHeader);
        }
//说明是从修改用户信息界面返回的
        if (requestCode == 500 && resultCode == 600){
            String sign = SpUtils.getInstance().getString("sign");
            String username = SpUtils.getInstance().getString("username");
            String avater = SpUtils.getInstance().getString("avater");

            if (!TextUtils.isEmpty(sign)){
                signature.setVisibility(View.VISIBLE);
                signature.setText(sign);
            }
            if (!TextUtils.isEmpty(username)){
                textUsNikename.setText(username);
            }
            if (!TextUtils.isEmpty(avater)){
//重新组装字符串
                String s = splitString(avater);
                Glide.with(context).applyDefaultRequestOptions(new RequestOptions().circleCrop()).load(s).into(imgUsHeader);
            }

        }
    }
    private String splitString(String avater) {

        char aa = '\\';

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < avater.length(); i++) {
            if (avater.charAt(i)  != aa){
                sb.append(avater.charAt(i));
            }
        }
        return sb.toString();
    }

    @OnClick({R.id.text_us_updateinfo, R.id.go_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_us_updateinfo:
                startActivityForResult(new Intent(context, UpdateInfoActivity.class),500);
                break;
            case R.id.go_login:
                startActivityForResult(new Intent(context, LoginActivity.class), 300);
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError(String err) {

    }
}
