package com.example.qq.fragments.home;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.qq.R;
import com.example.qq.base.BaseAdapter;

import java.util.List;

public class DongTaiImgAdapter extends BaseAdapter {
    public DongTaiImgAdapter(List mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_dongtai_img;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int positon, Object o) {
        ImageView img = (ImageView) holder.getView(R.id.dongtai_img);
        String imgpath = (String) mDatas.get(positon);

        Glide.with(mContext).load(imgpath).into(img);
    }

}
