package com.example.qq.interfaces.login;

import com.example.qq.bean.LoginBean;
import com.example.qq.interfaces.IBaseView;
import com.example.qq.interfaces.IPresenter;

public interface LoginConstract {
    interface View extends IBaseView{
        void TokenReturn(LoginBean bean);
    }

    interface Presenter extends IPresenter<View> {
        void goLogin(String name,String pass);
    }
}
