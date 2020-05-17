package com.example.qq.fragments.home;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qq.R;
import com.example.qq.base.BaseAdapter;
import com.example.qq.bean.DongTaiBean;

import java.util.List;

public class DongTaiCommentAdapter extends BaseAdapter {

    public DongTaiCommentAdapter(List mDatas) {
        super(mDatas);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_dongtai_comment;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int positon, Object o) {
//具体评论的内容 控件
        TextView comment = (TextView) holder.getView(R.id.item_text_commentContent);

        DongTaiBean.DataBean.DiscussBean bean = (DongTaiBean.DataBean.DiscussBean) mDatas.get(positon);
//判断当前是 评论 还是 回复
        if (!TextUtils.isEmpty(bean.getTargetuid())){
            /**
             * 不为空 为 回复数据   样式1 回复 2 ：...巴拉巴拉  1是回复者 2是评论者
             * discussusername 是回复者  targetusername是评论者
             *
             */
            String discussusername = bean.getDiscussusername();
            String targetusername = bean.getTargetusername();
            String conent = discussusername + "回复"+ targetusername+":"+bean.getContent();
            SpannableString spann = new SpannableString(conent);
 //计算富文本中样式和 点击范围的 开始 和结束位置
            int start = 0,end = 0;
            end = discussusername.length();
//设置 回复者 的颜色
            ForegroundColorSpan discussuserSpan = new ForegroundColorSpan(Color.parseColor("#3F739C"));
            spann.setSpan(discussuserSpan,start,end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
// 回复者名字的 点击监听
            ClickableSpan discussuserClick = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    Toast.makeText(mContext, "你点击的是"+discussusername, Toast.LENGTH_SHORT).show();
//打开用户信息的界面

                }
            };
            spann.setSpan(discussuserClick,start,end,Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//设置 评论者 的颜色
            start = end+2;
            end = start+targetusername.length()+1;
            ForegroundColorSpan targetuserSpan = new ForegroundColorSpan(Color.parseColor("#3F739C"));
            spann.setSpan(targetusername,start,end,Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
// 评论者名字的 点击监听
            ClickableSpan targetuserClick = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    Toast.makeText(mContext, "你点击的是"+targetusername, Toast.LENGTH_SHORT).show();
//打开用户信息的界面

                }
            };
            spann.setSpan(targetuserClick,start,end,Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            comment.setText(spann);


        }else {
//当前是评论数据 AA:XXX
            String discussusername = bean.getDiscussusername();
            String content = discussusername+":"+bean.getContent();
            SpannableString spannableString = new SpannableString(content);
//设置 评论者的 颜色
            ForegroundColorSpan discussuserSpan = new ForegroundColorSpan(Color.parseColor("#3F739C"));
            int start = 0,ent = 0;
            ent = discussusername.length()+1;
            spannableString.setSpan(discussuserSpan,start,ent,Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
 //设置 评论者的 点击监听
            ClickableSpan discussuserClick = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    Toast.makeText(mContext, "你点击的是"+discussusername, Toast.LENGTH_SHORT).show();
//打开用户信息的界面
                }
            };
            spannableString.setSpan(discussuserClick,start,ent,Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

            comment.setText(spannableString);
        }
//设置comment文本响应点击事件
        comment.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
