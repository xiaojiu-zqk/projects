package com.example.qq.presenter;

import com.example.qq.base.BasePersenter;
import com.example.qq.bean.DongTaiBean;
import com.example.qq.bean.LoginBean;
import com.example.qq.interfaces.dongtai.DongTaiConstract;
import com.example.qq.model.CommonSubscriber;
import com.example.qq.model.http.HttpManager;
import com.example.qq.utils.RxUtils;

public class DongTaiPresenter extends BasePersenter<DongTaiConstract.View> implements DongTaiConstract.Presenter {

    @Override
    public void getDongTaiDate(int page, int size, int trendsid) {
        addSubscribe(HttpManager.getShopApi().getDongTai(page,size,trendsid)
                .compose(RxUtils.<DongTaiBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<DongTaiBean>(mView) {
                    @Override
                    public void onNext(DongTaiBean registerBean) {
                        mView.DongTaiDateReturn(registerBean);
                    }
                }));
    }
}
