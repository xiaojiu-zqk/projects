package com.example.qq.presenter;

import com.example.qq.base.BasePersenter;
import com.example.qq.bean.LoginBean;
import com.example.qq.interfaces.login.LoginConstract;
import com.example.qq.model.CommonSubscriber;
import com.example.qq.model.http.HttpManager;
import com.example.qq.utils.RxUtils;

public class LoginPresenter extends BasePersenter<LoginConstract.View> implements LoginConstract.Presenter {
    @Override
    public void goLogin(String name, String pass) {
        addSubscribe(HttpManager.getShopApi().getToken(name,pass)
                .compose(RxUtils.<LoginBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<LoginBean>(mView) {
                    @Override
                    public void onNext(LoginBean registerBean) {
                        mView.TokenReturn(registerBean);
                    }
                }));
    }
}
