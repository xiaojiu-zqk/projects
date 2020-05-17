package com.example.qq;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qq.adapter.Rec_FileAdapter;
import com.example.qq.apps.GlideEngine;
import com.example.qq.base.BaseAdapter;
import com.example.qq.base.BaseFragment;
import com.example.qq.interfaces.IPresenter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Fragment_file_photo extends BaseFragment implements BaseAdapter.OnItemClickListener{


    @BindView(R.id.file_rec)
    RecyclerView file_rec;

    public static Fragment_file_photo getInstance(){
        Fragment_file_photo fragment = new Fragment_file_photo();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int getLayout() {
        return R.layout.fragment_file_photo;
    }

    @Override
    protected void initView(View view) {
        file_rec.setLayoutManager(new GridLayoutManager(context,4));
        ArrayList<Integer> imgs = new ArrayList<>();
        imgs.add(R.mipmap.phote);
        Rec_FileAdapter adapter = new Rec_FileAdapter(imgs);
        file_rec.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError(String err) {

    }

    //照片的点击监听
    @Override
    public void onItemClick(View v, int position) {
        if (position == 0){
            openPhoto();
        }
    }

    private void openPhoto() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .loadImageEngine(GlideEngine.createGlideEngine())
                .maxSelectNum(1)
                .imageSpanCount(4)
                .selectionMode(PictureConfig.MULTIPLE)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PictureConfig.CHOOSE_REQUEST:
                // onResult Callback
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if(selectList.size() == 0) return;
                if(photoSelect != null){
                    photoSelect.selected(selectList);
                }
                break;
            default:
                break;
        }
    }

    //定义接口
    PhotoSelect photoSelect;

    public void addPhotoSelectListener(PhotoSelect photoSelect){
        this.photoSelect = photoSelect;
    }

    public interface PhotoSelect{
        void selected(List<LocalMedia> list);
    }

}
