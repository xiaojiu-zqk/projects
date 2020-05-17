package com.example.qq.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.qq.R;
import com.example.qq.apps.GlideEngine;
import com.example.qq.base.BaseActivity;
import com.example.qq.bean.UpDateUserBean;
import com.example.qq.bean.UserBean;
import com.example.qq.common.Constant;
import com.example.qq.interfaces.IPresenter;
import com.example.qq.interfaces.user.UserConstract;
import com.example.qq.model.apis.ChatApi;
import com.example.qq.model.http.HttpManager;
import com.example.qq.presenter.UserInfoPresenter;
import com.example.qq.utils.ImgPathRecombineUtils;
import com.example.qq.utils.SexUtils;
import com.example.qq.utils.SpUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class UpdateInfoActivity extends BaseActivity<UserConstract.View, UserConstract.Presenter> implements UserConstract.View {

    @BindView(R.id.finis)
    TextView finis;
    @BindView(R.id.tv_public)
    TextView tvPublic;
    @BindView(R.id.outLogin)
    TextView outLogin;
    @BindView(R.id.iv_header)
    ImageView ivHeader;
    @BindView(R.id.go_header)
    TextView goHeader;
    @BindView(R.id.contraintlayout_header)
    ConstraintLayout contraintlayoutHeader;
    @BindView(R.id.text_nike)
    TextView textNike;
    @BindView(R.id.go_nike)
    TextView goNike;
    @BindView(R.id.contraintlayout_nike)
    ConstraintLayout contraintlayoutNike;
    @BindView(R.id.text_sex)
    TextView textSex;
    @BindView(R.id.go_sex)
    TextView goSex;
    @BindView(R.id.contraintlayout_sex)
    ConstraintLayout contraintlayoutSex;
    @BindView(R.id.text_age)
    TextView textAge;
    @BindView(R.id.go_age)
    TextView goAge;
    @BindView(R.id.contraintlayout_age)
    ConstraintLayout contraintlayoutAge;
    @BindView(R.id.text_address)
    TextView textAddress;
    @BindView(R.id.go_address)
    TextView goAddress;
    @BindView(R.id.contraintlayout_address)
    ConstraintLayout contraintlayoutAddress;
    @BindView(R.id.text_signature)
    TextView textSignature;
    @BindView(R.id.go_signature)
    TextView goSignature;
    @BindView(R.id.contraintlayout_signature)
    ConstraintLayout contraintlayoutSignature;
    private AlertDialog alertDialog;
    private Map<String,String> map;
    //网络上的图片地址
    private String imgpath;
    //选择的性别
    private String sex;
    //修改的 签名
    private String sign;
    //修改的 年龄
    private String age;
    //修改的 昵称
    private String nick;

    @Override
    protected int getLayout() {
        return R.layout.activity_update_info;
    }

    @Override
    protected void initView() {
        map = new HashMap<>();
    }

    @Override
    protected void initData() {
        presenter.getUserInfo();
    }

    @Override
    protected UserConstract.Presenter createPersenter() {
        return new UserInfoPresenter();
    }

    @OnClick({R.id.finis, R.id.outLogin, R.id.contraintlayout_header, R.id.contraintlayout_nike, R.id.contraintlayout_sex, R.id.contraintlayout_age, R.id.contraintlayout_address, R.id.contraintlayout_signature})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.finis:
                Intent intent = new Intent();
                setResult(600,intent);
                finish();
                break;
            case R.id.outLogin:
                break;
            case R.id.contraintlayout_header:
//打开相册
                openPhoto();
                break;
            case R.id.contraintlayout_nike:
//修改昵称
                showEdictSignature("nike");
                break;
            case R.id.contraintlayout_sex:
//显示修改 性别 的弹窗
                showSelectSex();
                break;
            case R.id.contraintlayout_age:
//显示修改 年龄 的弹窗
                showEdictSignature("age");
                break;
            case R.id.contraintlayout_address:
//显示修改 住址 的弹窗
                break;
            case R.id.contraintlayout_signature:
//显示输入 签名
                showEdictSignature("sign");
                break;
        }
    }
//修改签名
    private void showEdictSignature(String tx) {
        if (alertDialog == null){
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_signature, null, false);
            alertDialog = new AlertDialog.Builder(context)
                    .setView(view)
                    .setCancelable(false)
                    .create();
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            EditText et = view.findViewById(R.id.et_signature);
            Button com = view.findViewById(R.id.btn_et_commit);

            com.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = et.getText().toString();
                    if (!TextUtils.isEmpty(s)){
                        //点击 年龄
                        if (tx.equals("age")){
                            age = s;
                            map.put("age",s);

                        }
                        //点击 昵称
                        if (tx.equals("nike")){
                            nick = s;
                            map.put("nickname",s);

                        }
                        //点击 签名
                        if (tx.equals("sign")){
                            sign = s;
                            map.put("sign",s);
                        }

                        presenter.updateUserInfo(map);
                        alertDialog.dismiss();
                    }else {
                        Toast.makeText(context, "没有什么要保存的", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            alertDialog.show();

            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    alertDialog = null;
                }
            });
        }
    }

//打开相册
    private void openPhoto() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .loadImageEngine(GlideEngine.createGlideEngine())
                .maxSelectNum(1)
                .imageSpanCount(4)
                .selectionMode(PictureConfig.MULTIPLE)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }
//点击相册 图片选中之后的回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PictureConfig.CHOOSE_REQUEST:
                // onResult Callback
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if(selectList.size() == 0) return;
                String path = selectList.get(0).getPath();
                upLoad(path);
                break;
            default:
                break;
        }
    }

    private void upLoad(String path) {
        String img_format = "image/jpg";
        String key = "xts";
        //sd卡图片文件
        File file = new File(path);
        if(file.exists()){
            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(Constant.BASE_UPLOAD_URL)
                    .build();

            MediaType type = MediaType.parse("application/octet-stream");
            //上传后文件在服务器保存的路径
            RequestBody body = RequestBody.create(type, "nihao");
            RequestBody fileBody = RequestBody.create(type, file);

            //要上传的文件封装之后的MultipartBody.Part
            MultipartBody.Part filePart =
                    MultipartBody.Part.createFormData("file", file.getName(), fileBody);
            retrofit.create(ChatApi.class)
                    .uploadFile(body, filePart)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }
                        @Override
                        public void onNext(ResponseBody responseBody) {
                            try {
                                String  s = responseBody.string();
                                int start = s.indexOf("http");
                                imgpath = s.substring(start, s.length() - 3);
                                map.clear();
                                map.put("avater", imgpath);
                                presenter.updateUserInfo(map);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("TAG", "onError: " + e.toString());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }else{
            Toast.makeText(this,"找不到本地文件",Toast.LENGTH_SHORT).show();
        }
    }


    private void showSelectSex() {

        if (alertDialog == null){
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_sex, null, false);
            alertDialog = new AlertDialog.Builder(context)
                    .setView(view)
                    .setCancelable(false)
                    .create();
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            TextView sex_0 = view.findViewById(R.id.sex_0);
            TextView sex_1 = view.findViewById(R.id.sex_1);
            TextView sex_2 = view.findViewById(R.id.sex_2);
//设置监听
            onclick(sex_0);
            onclick(sex_1);
            onclick(sex_2);

            alertDialog.show();

            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    alertDialog = null;
                }
            });
        }

    }
//弹窗的点击监听
    private void onclick(TextView textsex) {
        textsex.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.sex_0:
                        sex = "无";
                        //网络修改服务器
                        map.clear();
                        map.put("sex","0");
                        presenter.updateUserInfo(map);
                        alertDialog.dismiss();
                        break;
                    case R.id.sex_1:
                        sex = "男";
                        //网络修改服务器
                        map.clear();
                        map.put("sex","1");
                        presenter.updateUserInfo(map);
                        alertDialog.dismiss();
                        break;
                    case R.id.sex_2:
                        sex = "女";
                        //网络修改服务器
                        map.clear();
                        map.put("sex","2");
                        presenter.updateUserInfo(map);
                        alertDialog.dismiss();
                        break;
                }
            }
        });
    }


    @Override
    public void UserInfoReturn(UserBean registerBean) {
        UserBean.DataBean data = registerBean.getData();

        String avater = data.getAvater();
        Integer level = (Integer) data.getLevel();
        String nickname = (String) data.getNickname();
        Integer sex = data.getSex();
        String sign = (String) data.getSign();

        if (!TextUtils.isEmpty(avater)){
//调用工具类 重新组装 头像的 url
            String s = ImgPathRecombineUtils.splitString(avater);

            Glide.with(context).applyDefaultRequestOptions(new RequestOptions().circleCrop()).load(s).into(ivHeader);
        }
        if (!TextUtils.isEmpty(nickname)){
            textNike.setText(nickname);
        }
        if (!TextUtils.isEmpty(sign)){
            textSignature.setText(sign);
            SpUtils.getInstance().setValue("sign",sign);
        }
        if (sex != null){
            textSex.setText(SexUtils.getSex(sex));
        }
    }



    @Override
    public void UpdateUserInfoReturn(UpDateUserBean bean) {
        int err = bean.getErr();
        if (err == 200){
            if (imgpath != null){
                Toast.makeText(context, "修改头像成功", Toast.LENGTH_SHORT).show();
                SpUtils.getInstance().setValue("avater",imgpath);
                Glide.with(context).load(imgpath).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).into(ivHeader);
                imgpath = null;
            }
            if (sex != null){
                Toast.makeText(context, "修改性别成功", Toast.LENGTH_SHORT).show();
                textSex.setText(sex);
                sex = null;
            }
            if (sign != null){
                Toast.makeText(context, "修改签名成功", Toast.LENGTH_SHORT).show();
                textSignature.setText(sign);
//保存签名
                SpUtils.getInstance().setValue("sign",sign);
                sign = null;
            }
            if (age != null){
                Toast.makeText(context, "修改年龄成功", Toast.LENGTH_SHORT).show();
                textAge.setText(age);
                age = null;
            }
            if (nick != null){
                Toast.makeText(context, "修改昵称成功", Toast.LENGTH_SHORT).show();
                textNike.setText(nick);
                nick = null;
            }
        }else {
            Toast.makeText(context, "修改失败", Toast.LENGTH_SHORT).show();
        }
    }
}
