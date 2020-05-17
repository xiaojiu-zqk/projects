package com.example.qq.common;

import com.example.qq.R;
import com.example.qq.apps.MyApp;

import java.io.File;

public class Constant {

    public static final int FACE_SMALL_W = 40; //dp为单位
    public static final int FACE_SMALL_H = 40;

    public static final int FACE_BIG_W = 80;
    public static final int FACE_BIG_H = 80;


    public static final String self_avater = "https://dss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3620016507,562397260&fm=111&gp=0.jpg";

    public static final String other_avater = "https://dss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1728577482,2585850803&fm=26&gp=0.jpg";

    public static final String Base_Home_url = "https://cdwan.cn/api/";

    //聊天服务的基础地址
    public static final String BASE_CHAT_URL = "http://cdwan.cn:9001/";

    //头像上传基础地址
    public static final String BASE_UPLOAD_URL = "http://yun918.cn/study/";

    //加号的 路径
    public static final String SHARE_ADD_PATH = R.drawable.add+"";

    //网络缓存的地址
    public static final String PATH_DATA = MyApp.myApp.getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/ShiXun";

    public static final int CLICK_TIME = 2000;

    public static int uid;


}
