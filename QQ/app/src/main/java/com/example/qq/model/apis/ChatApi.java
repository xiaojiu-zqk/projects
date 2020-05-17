package com.example.qq.model.apis;


import com.example.qq.bean.DongTaiBean;
import com.example.qq.bean.LoginBean;
import com.example.qq.bean.RegisterBean;
import com.example.qq.bean.SendDongTaiBean;
import com.example.qq.bean.UpDateUserBean;
import com.example.qq.bean.UserBean;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ChatApi {

    //注册
    @POST("user/register")
    @FormUrlEncoded
    Flowable<RegisterBean> getCode(@Field("username") String name, @Field("password") String pass);

    //登录
    @POST("user/login")
    @FormUrlEncoded
    Flowable<LoginBean> getToken(@Field("username") String name, @Field("password") String pass);

       //获取用户信息
    @GET("user/details")
    Flowable<UserBean> getUserInfo();

    //修改用户信息
    @POST("user/updateinfo")
    @FormUrlEncoded
    Flowable<UpDateUserBean> getCode(@FieldMap Map<String,String> map);

    //头像上传
    @POST("public/file_upload.php")
    @Multipart
    Observable<ResponseBody> uploadFile(@Part("key")RequestBody key, @Part MultipartBody.Part file);

    //获取动态数据
    @GET("trends/queryTrends")
    Flowable<DongTaiBean> getDongTai(@Query("page") int page, @Query("size") int size, @Query("trendsid") int trendsid);

    //发动态
    @POST("trends/sendTrends")
    @FormUrlEncoded
    Flowable<SendDongTaiBean> getDongTaiCode(@Field("content") String content, @Field("resources") String resources);
}
