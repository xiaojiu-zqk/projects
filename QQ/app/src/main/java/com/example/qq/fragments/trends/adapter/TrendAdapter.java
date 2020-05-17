package com.example.qq.fragments.trends.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.qq.R;
import com.example.qq.base.BaseAdapter;
import com.example.qq.common.Constant;

import java.util.List;

public class TrendAdapter extends BaseAdapter {




    public TrendAdapter(List mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_share_img;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int positon, Object o) {
        ImageView img = (ImageView) holder.getView(R.id.item_share);
        ImageView delect = (ImageView) holder.getView(R.id.share_delete);

        String path = (String) mDatas.get(positon);

        if (mDatas.size()<=9 && path.equals(Constant.SHARE_ADD_PATH)){
            delect.setVisibility(View.GONE);
            img.setBackgroundResource(Integer.parseInt(path));
        }else {
            Glide.with(mContext).load(path).into(img);
            delect.setVisibility(View.VISIBLE);
        }


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onimgclick != null){
                    onimgclick.imgClick(positon);
                }
            }
        });



    }

    private Onimgclick onimgclick;

    public void setOnimgclick(Onimgclick onimgclick){
        this.onimgclick = onimgclick;
    }

    public interface Onimgclick{
        void imgClick(int posi);
    }

}
