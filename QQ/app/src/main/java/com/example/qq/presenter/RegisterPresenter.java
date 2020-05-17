package com.example.qq.presenter;

import com.example.qq.base.BasePersenter;
import com.example.qq.bean.RegisterBean;
import com.example.qq.interfaces.register.RegisterConstract;
import com.example.qq.model.CommonSubscriber;
import com.example.qq.model.http.HttpManager;
import com.example.qq.utils.RxUtils;

public class RegisterPresenter extends BasePersenter<RegisterConstract.View> implements RegisterConstract.Presenter {
    @Override
    public void getCode(String name,String pass) {
        addSubscribe(HttpManager.getShopApi().getCode(name,pass)
                .compose(RxUtils.<RegisterBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<RegisterBean>(mView) {
                    @Override
                    public void onNext(RegisterBean registerBean) {
                        mView.RegisterCodeReturn(registerBean);
                    }
                }));
    }
}
