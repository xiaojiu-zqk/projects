package com.example.qq.presenter;

import com.example.qq.base.BasePersenter;
import com.example.qq.bean.LoginBean;
import com.example.qq.bean.UpDateUserBean;
import com.example.qq.bean.UserBean;
import com.example.qq.interfaces.user.UserConstract;
import com.example.qq.model.CommonSubscriber;
import com.example.qq.model.http.HttpManager;
import com.example.qq.utils.RxUtils;

import java.util.Map;

public class UserInfoPresenter extends BasePersenter<UserConstract.View> implements UserConstract.Presenter {

    @Override
    public void getUserInfo() {
        addSubscribe(HttpManager.getShopApi().getUserInfo()
                .compose(RxUtils.<UserBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<UserBean>(mView) {
                    @Override
                    public void onNext(UserBean registerBean) {
                        mView.UserInfoReturn(registerBean);
                    }
                }));
    }

    @Override
    public void updateUserInfo(Map<String, String> map) {
        addSubscribe(HttpManager.getShopApi().getCode(map)
                .compose(RxUtils.<UpDateUserBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<UpDateUserBean>(mView) {
                    @Override
                    public void onNext(UpDateUserBean registerBean) {
                        mView.UpdateUserInfoReturn(registerBean);
                    }
                }));
    }
}
