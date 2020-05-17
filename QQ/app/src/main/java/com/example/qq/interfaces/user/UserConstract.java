package com.example.qq.interfaces.user;

import com.example.qq.bean.UpDateUserBean;
import com.example.qq.bean.UserBean;
import com.example.qq.interfaces.IBaseView;
import com.example.qq.interfaces.IPresenter;

import java.util.Map;

public interface UserConstract {

    interface View extends IBaseView{
        void UserInfoReturn(UserBean registerBean);
        void UpdateUserInfoReturn(UpDateUserBean registerBean);
    }

    interface Presenter extends IPresenter<View>{
        void getUserInfo();
        void updateUserInfo(Map<String,String> map);
    }
}
