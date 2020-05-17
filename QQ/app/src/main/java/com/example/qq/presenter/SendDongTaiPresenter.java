package com.example.qq.presenter;

import com.example.qq.base.BasePersenter;
import com.example.qq.bean.SendDongTaiBean;
import com.example.qq.interfaces.senddongtai.SendConstract;
import com.example.qq.model.CommonSubscriber;
import com.example.qq.model.http.HttpManager;
import com.example.qq.utils.RxUtils;

public class SendDongTaiPresenter extends BasePersenter<SendConstract.View> implements SendConstract.Presenter {
    @Override
    public void getSendCode(String content, String resources) {
        addSubscribe(HttpManager.getShopApi().getDongTaiCode(content,resources)
                .compose(RxUtils.<SendDongTaiBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<SendDongTaiBean>(mView) {
                    @Override
                    public void onNext(SendDongTaiBean registerBean) {
                        mView.SendCodeReturn(registerBean);
                    }
                }));
    }
}
