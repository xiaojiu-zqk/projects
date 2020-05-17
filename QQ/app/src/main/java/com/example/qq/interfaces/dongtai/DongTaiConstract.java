package com.example.qq.interfaces.dongtai;

import com.example.qq.bean.DongTaiBean;
import com.example.qq.interfaces.IBaseView;
import com.example.qq.interfaces.IPresenter;

public interface DongTaiConstract {

    interface View extends IBaseView{
        void DongTaiDateReturn(DongTaiBean registerBean);
    }

    interface Presenter extends IPresenter<View>{
        void getDongTaiDate(int page, int size, int trendsid);
    }
}
