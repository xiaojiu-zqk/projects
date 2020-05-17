package com.example.qq.interfaces;

public interface IPresenter<V extends IBaseView> {

    void attchView(V view);

    void detachView();

}
