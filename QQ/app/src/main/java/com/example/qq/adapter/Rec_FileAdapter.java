package com.example.qq.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qq.R;
import com.example.qq.base.BaseAdapter;

import java.util.List;

public class Rec_FileAdapter extends BaseAdapter {

    public Rec_FileAdapter(List mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_file;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int positon, Object o) {
        ImageView img = (ImageView) holder.getView(R.id.file_img);
        Integer path = (Integer) mDatas.get(positon);
        img.setBackgroundResource(path);
    }

}
