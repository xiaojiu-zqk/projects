package com.example.qq.fragments.home;

import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.qq.R;
import com.example.qq.base.BaseAdapter;
import com.example.qq.bean.DongTaiBean;

import java.util.ArrayList;
import java.util.List;

public class HomeRecAdapter extends BaseAdapter {
    public HomeRecAdapter(List mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_home_rec;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int positon, Object o) {
//用户头像控件
        ImageView imgHeader = (ImageView) holder.getView(R.id.item_userHeader);
//动态时间控件
        TextView textTime = (TextView) holder.getView(R.id.item_userTime);
//用户发表的文字动态
        TextView textContent = (TextView) holder.getView(R.id.item_userContent);
//用户名控件
        TextView textNike = (TextView) holder.getView(R.id.item_usernike);
//点赞控件
        TextView textPraise = (TextView) holder.getView(R.id.item_text_praise);
//所有点赞的人 控件
        TextView textPraiseAll = (TextView) holder.getView(R.id.item_text_praiseAll);

        Drawable drawableP = mContext.getResources().getDrawable(R.mipmap.icon_praise);
        drawableP.setBounds(0,0,50,50);
        textPraise.setCompoundDrawables(drawableP,null,null,null);
//评论控件
        TextView textComment = (TextView) holder.getView(R.id.item_text_comment);

        Drawable drawableC = mContext.getResources().getDrawable(R.mipmap.icon_comment);
        drawableC.setBounds(0,0,50,50);
        textComment.setCompoundDrawables(drawableC,null,null,null);
//用户发表的动态 图片 控件
        RecyclerView rec = (RecyclerView) holder.getView(R.id.item_userImgRec);

        rec.setLayoutManager(new GridLayoutManager(mContext,3));
//其他用户评论的rec 控件
        RecyclerView recPraise = (RecyclerView) holder.getView(R.id.item_userpraiseRec);

        recPraise.setLayoutManager(new LinearLayoutManager(mContext));

//---------------------------------以上都是初始化操作------------------------------------------------------------------
        DongTaiBean.DataBean bean = (DongTaiBean.DataBean) mDatas.get(positon);
//对点赞所有人这个控件的设置   textPraiseAll



        Glide.with(mContext).applyDefaultRequestOptions(new RequestOptions().circleCrop()).load(bean.getAvater()).into(imgHeader);
        textNike.setText(bean.getUsername());
//缺少对时间的处理
        textTime.setText(bean.getTime()+"");
        textContent.setText(bean.getContent());

        String resources = bean.getResources();
        ArrayList<String> strings = new ArrayList<>();
        strings.add(resources);
        if (!TextUtils.isEmpty(resources)){

            for (int i = 0; i < resources.length(); i++) {
                char c = resources.charAt(i);
                if (c == '$'){
                    int a = resources.indexOf("$");
                    String substring = resources.substring(0, a);
                    strings.add(substring);
                }

            }
        }

        DongTaiImgAdapter adapter = new DongTaiImgAdapter(strings);
        rec.setAdapter(adapter);

//对评论内容的处理
        List<DongTaiBean.DataBean.DiscussBean> discuss = bean.getDiscuss();

        DongTaiCommentAdapter dongTaiCommentAdapter = new DongTaiCommentAdapter(discuss);
        recPraise.setAdapter(dongTaiCommentAdapter);
    }

}
