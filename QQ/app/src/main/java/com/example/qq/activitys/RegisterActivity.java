package com.example.qq.activitys;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qq.R;
import com.example.qq.base.BaseActivity;
import com.example.qq.bean.RegisterBean;
import com.example.qq.interfaces.register.RegisterConstract;
import com.example.qq.presenter.RegisterPresenter;
import com.example.qq.utils.SecretUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<RegisterConstract.View, RegisterConstract.Presenter> implements RegisterConstract.View {

    @BindView(R.id.et_us_register_account)
    EditText etUsRegisterAccount;
    @BindView(R.id.et_us_register_password1)
    EditText etUsRegisterPassword1;
    @BindView(R.id.et_us_register_password2)
    EditText etUsRegisterPassword2;
    @BindView(R.id.ok_register)
    Button okRegister;
    private String account;
    private String password1;
    private String password2;

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected RegisterPresenter createPersenter() {
        return new RegisterPresenter();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError(String err) {

    }

    @OnClick(R.id.ok_register)
    public void onViewClicked() {
        account = etUsRegisterAccount.getText().toString().trim();
        password1 = etUsRegisterPassword1.getText().toString().trim();
        password2 = etUsRegisterPassword2.getText().toString().trim();
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password1) || TextUtils.isEmpty(password2)){
            Toast.makeText(context, "账号密码不可以为空", Toast.LENGTH_SHORT).show();
        }else {
            if (password1.equals(password2)){
                password1 = SecretUtils.getSha1(password1);
                presenter.getCode(account, password1);
            }else {
                Toast.makeText(context, "两次密码不一致哦~傻子", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void RegisterCodeReturn(RegisterBean bean) {
        if (bean.getCode()==10000){
            Intent intent = new Intent();
            intent.putExtra("account",account);
            intent.putExtra("password",password2);
            setResult(200,intent);
            finish();

        }else {
            Toast.makeText(context, "注册失败了，再来一遍吧", Toast.LENGTH_SHORT).show();
        }
    }
}
