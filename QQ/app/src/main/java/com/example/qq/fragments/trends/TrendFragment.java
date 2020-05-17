package com.example.qq.fragments.trends;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qq.R;
import com.example.qq.apps.GlideEngine;
import com.example.qq.base.BaseFragment;
import com.example.qq.bean.SendDongTaiBean;
import com.example.qq.common.Constant;
import com.example.qq.fragments.trends.adapter.TrendAdapter;
import com.example.qq.interfaces.IPresenter;
import com.example.qq.interfaces.senddongtai.SendConstract;
import com.example.qq.model.apis.ChatApi;
import com.example.qq.presenter.SendDongTaiPresenter;
import com.example.qq.utils.ImgPathRecombineUtils;
import com.example.qq.utils.SpUtils;
import com.google.gson.JsonObject;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class TrendFragment extends BaseFragment<SendConstract.View, SendConstract.Presenter> implements SendConstract.View {
    @BindView(R.id.userphone)
    TextView userphone;
    @BindView(R.id.text_share_send)
    TextView textShareSend;
    @BindView(R.id.et_share)
    EditText etShare;
    @BindView(R.id.img_share_rec)
    RecyclerView imgShareRec;
    @BindView(R.id.text_share_cancel)
    TextView textCancel;
    private ArrayList<String> imgpaths;
    private TrendAdapter adapter;
    private List<LocalMedia> selectList;
    //图片上传后的 网络地址
    private StringBuilder sb;
    private HashMap<String, String> upLoadImgs;

    @Override
    protected int getLayout() {
        return R.layout.fragment_trend;
    }

    @Override
    protected void initView(View view) {

        GridLayoutManager manager = new GridLayoutManager(context, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        imgpaths = new ArrayList<>();
 /*       String weibodongtai = SpUtils.getInstance().getString("weibodongtai");
//说明没有保存过用户的 草稿
        if (TextUtils.isEmpty(weibodongtai)){
            imgpaths.add(Constant.SHARE_ADD_PATH);
        }else {
            try {
                JSONObject jo = new JSONObject(weibodongtai);
                String user = (String) jo.get("user");
                JSONArray imgs = jo.getJSONArray("imgs");
                JSONObject jo2 = (JSONObject) imgs.get(0);
                String et = (String) jo2.get("et");
                for (int i = 0; i < imgs.length(); i++) {


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }*/
        imgpaths.add(Constant.SHARE_ADD_PATH);
        imgShareRec.setLayoutManager(manager);

        adapter = new TrendAdapter(imgpaths);
        imgShareRec.setAdapter(adapter);
        adapter.setOnimgclick(new TrendAdapter.Onimgclick() {
            @Override
            public void imgClick(int posi) {
                if (imgpaths.get(posi).equals(Constant.SHARE_ADD_PATH)){
                    openPhoto();
                }else {
                    Toast.makeText(context, "点击放大", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openPhoto() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .loadImageEngine(GlideEngine.createGlideEngine())
                .maxSelectNum(9)
                .imageSpanCount(4)
                .enableCrop(true)
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                //.showCropFrame(true)      // 是否显示裁剪矩形边框
                //.showCropGrid(true)           // 是否显示裁剪矩形网格
                .selectionMode(PictureConfig.MULTIPLE)
                .selectionMedia(selectList)       // 是否传入已选图片 List<LocalMedia> list
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PictureConfig.CHOOSE_REQUEST:
                // onResult Callback
                selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList.size() == 0) return;
                imgpaths.clear();
                int size = selectList.size();
                if (size >= 9) {
                    for (int i = 0; i < size; i++) {
                        String path = selectList.get(i).getPath();
                        imgpaths.add(path);
                    }
                } else {
                    for (int i = 0; i < size; i++) {
                        String path = selectList.get(i).getPath();
                        imgpaths.add(path);
                    }
                    imgpaths.add(Constant.SHARE_ADD_PATH);
                }
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }


    @Override
    protected void initData() {

    }

    @Override
    protected SendConstract.Presenter createPresenter() {
        return new SendDongTaiPresenter();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError(String err) {

    }

    @OnClick({R.id.text_share_cancel, R.id.text_share_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_share_cancel:
                showDialog();
                break;
            case R.id.text_share_send:
                checkDongTai();
                break;
        }
    }

    private void checkDongTai() {
        String etshare = etShare.getText().toString();
        if (!TextUtils.isEmpty(etshare) && imgpaths != null && imgpaths.size()>0){

            upLoadImgs = new HashMap<String,String>();
            String imgPath = imgpaths.get(0);
            upLoad( imgPath);
            for (int i = 0; i < imgpaths.size(); i++) {

                String currentThreadId = "";
                int finalI = i;
                String finalCurrentThreadId = currentThreadId;  //奇怪的操作


//多线程上传
              /*  Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        upLoad(finalCurrentThreadId, imgPath);
                    }
                });
                currentThreadId = String.valueOf(thread.getId());
                thread.start();*/

            }

        }else {
            Toast.makeText(context, "都不写，发啥子的动态", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialog() {

        AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle("是否保存")

                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                })
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
 //保存输入的 草稿 和 图片的 本地地址
                        commit();
                    }

                })
                .create();
        alertDialog.show();
    }
    private void commit() {
        String s = etShare.getText().toString();
        if (!TextUtils.isEmpty(s) && imgpaths != null && imgpaths.size()>0){
            JSONObject jo = new JSONObject();
            try {
                jo.put("user", SpUtils.getInstance().getString("token"));
                JSONArray array = new JSONArray();
                //添加 草稿
                JSONObject et = new JSONObject();
                et.put("et",s);
                array.put(et);
                //添加 图片
                int size = imgpaths.size();
                for (int i = 0; i < size; i++) {
                    JSONObject img = new JSONObject();
                    img.put("imgpath",imgpaths.get(i));
                    array.put(img);
                }
                jo.put("imgs",array);
//保存草稿
                SpUtils.getInstance().setValue("weibodongtai",jo.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (imgpaths != null && imgpaths.size()>0){

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
                                String  josn = responseBody.string();
                                JSONObject jsonObject = new JSONObject(josn);
                                String imgUrl = jsonObject.getJSONObject("data").getString("url");
                                presenter.getSendCode(etShare.getText().toString(),imgUrl);
                               // upLoadImgs.put(threadid,imgUrl);
//判断上传是否完成
                                //checkUpLoadOver();
                            } catch (IOException | JSONException e) {
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
            Toast.makeText(context,"找不到本地文件",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 判断上传是否完成
     */
    private void checkUpLoadOver(){
        if(upLoadImgs.size() == imgpaths.size()-1){
            sendTrends();
        }
    }

    /**
     * 发送数据
     */
    private void sendTrends(){
        String content = etShare.getText().toString();
        StringBuilder stringBuilder = new StringBuilder();
        for(String value : upLoadImgs.values()){
            stringBuilder.append(value);
            stringBuilder.append("$");
        }
        //删除尾部多余的$
        if(stringBuilder.length() > 0) stringBuilder.deleteCharAt(stringBuilder.length()-1);
        presenter.getSendCode(content,stringBuilder.toString());
    }

    @Override
    public void SendCodeReturn(SendDongTaiBean registerBean) {
        if (registerBean.getErr() == 30000){
            Toast.makeText(context, "发布动态ok", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "发布动态失败", Toast.LENGTH_SHORT).show();
        }
    }
}
