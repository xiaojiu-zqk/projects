package com.minsu.persenters.test;


import com.minsu.base.BasePersenter;
import com.minsu.common.CommonSubscriber;
import com.minsu.intenfaces.test.TestConstract;
import com.minsu.models.HttpManager;
import com.minsu.models.beans.ChaptersBean;
import com.minsu.utils.RxUtils;

public class TestPersenter extends BasePersenter<TestConstract.View> implements TestConstract.Persenter {

    @Override
    public void getChapters() {
        addSubscribe(HttpManager.getInstance().getWanApi().getChapters()
        .compose(RxUtils.<ChaptersBean>rxScheduler())
        .subscribeWith(new CommonSubscriber<ChaptersBean>(mView){

            @Override
            public void onNext(ChaptersBean chaptersBean) {
                mView.getChaptersReturn(chaptersBean);
            }
        }));

        //网络请求不用背压式
        /*HttpManager.getInstance().getWanApi().getChapters()
                .compose(RxUtils.<ChaptersBean>rxScheduler())
                .subscribeWith(new ResourceSubscriber<ChaptersBean>() {
                    @Override
                    public void onNext(ChaptersBean chaptersBean) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });*/
    }
}
