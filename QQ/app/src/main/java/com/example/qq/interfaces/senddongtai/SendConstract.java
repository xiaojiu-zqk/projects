package com.example.qq.interfaces.senddongtai;

import com.example.qq.bean.SendDongTaiBean;
import com.example.qq.interfaces.IBaseView;
import com.example.qq.interfaces.IPresenter;

public interface SendConstract {

    interface View extends IBaseView{
        void SendCodeReturn(SendDongTaiBean registerBean);
    }

    interface Presenter extends IPresenter<View>{
        void getSendCode(String content,String resources);
    }
}
