package com.example.qq.interfaces.register;

import com.example.qq.bean.RegisterBean;
import com.example.qq.interfaces.IBaseView;
import com.example.qq.interfaces.IPresenter;

public interface RegisterConstract {

    interface View extends IBaseView{
        void RegisterCodeReturn(RegisterBean registerBean);
    }

    interface Presenter extends IPresenter<View>{
        void getCode(String name,String pass);
    }
}
