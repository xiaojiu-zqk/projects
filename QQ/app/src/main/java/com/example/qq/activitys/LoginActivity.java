package com.example.qq.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.qq.R;
import com.example.qq.base.BaseActivity;
import com.example.qq.bean.LoginBean;
import com.example.qq.interfaces.login.LoginConstract;
import com.example.qq.presenter.LoginPresenter;
import com.example.qq.utils.SecretUtils;
import com.example.qq.utils.SpUtils;
import com.google.android.material.tabs.TabLayout;
import com.luck.picture.lib.camera.listener.ClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginConstract.View, LoginConstract.Presenter> implements LoginConstract.View {
// lizhijun1   123321
    @BindView(R.id.et_us_account)
    EditText etUsAccount;
    @BindView(R.id.et_us_password)
    EditText etUsPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    TextView btnRegister;
    @BindView(R.id.xian)
    View xian;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.cb)
    CheckBox cb;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        tab.addTab(tab.newTab().setIcon(R.mipmap.qaq));
        tab.addTab(tab.newTab().setIcon(R.mipmap.vx));

        SpannableStringBuilder ss = new SpannableStringBuilder();
        String str1 = "已阅读并同意";
        String str2 = "《用户服务协议》";
        String str3 = "和";
        String str4 = "《隐私政策》";

        ss.append(str1);

        SpannableString span2 = new SpannableString(str2);
        span2.setSpan(new ClickListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("url", "https://www.baidu.com");
                startActivity(intent);
            }
        },0,str2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span2.setSpan(new ForegroundColorSpan(Color.BLUE),0,str2.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.append(span2);
        ss.append(str3);
        SpannableString span4 = new SpannableString(str4);
        span4.setSpan(new ClickListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra("url", "https://www.baidu.com");
                startActivity(intent);
            }
        },0,str4.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span4.setSpan(new ForegroundColorSpan(Color.BLUE),0,str4.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.append(span4);
        cb.setText(ss);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected LoginConstract.Presenter createPersenter() {
        return new LoginPresenter();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError(String err) {

    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String account = etUsAccount.getText().toString().trim();
                String password = etUsPassword.getText().toString().trim();
                if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
                    Toast.makeText(context, "你是傻子嘛！账号密码都不输", Toast.LENGTH_SHORT).show();
                } else {
                    String password2 = SecretUtils.getSha1(password);
                    if (cb.isChecked()){
                        presenter.goLogin(account, password2);
                    }else {
                        Toast.makeText(context, "不同意我的条款，还想用我的软件", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btn_register:
                Intent intent = new Intent(context, RegisterActivity.class);
                startActivityForResult(intent, 100);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 200) {
            String account = data.getStringExtra("account");
            String password = data.getStringExtra("password");
            etUsAccount.setText(account);
            etUsPassword.setText(password);
        }
    }

    @Override
    public void TokenReturn(LoginBean bean) {
        if (bean.getErr() == 10005) {
            Toast.makeText(context, "账号密码不正确", Toast.LENGTH_SHORT).show();
        } else {
            SpUtils.getInstance().setValue("token", bean.getData().getToken());
            SpUtils.getInstance().setValue("username", bean.getData().getUsername());
            SpUtils.getInstance().setValue("avater", bean.getData().getAvater());
            SpUtils.getInstance().setValue("phone", bean.getData().getPhone());
            Intent intent = new Intent();
            setResult(400, intent);
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
