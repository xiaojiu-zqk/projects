package com.minsu.intenfaces.test;


import com.minsu.intenfaces.IBasePersenter;
import com.minsu.intenfaces.IBaseView;
import com.minsu.models.beans.ChaptersBean;

public interface TestConstract {

    interface View extends IBaseView {
        void getChaptersReturn(ChaptersBean result);
    }

    interface Persenter extends IBasePersenter<View> {
        void getChapters();
    }

}
