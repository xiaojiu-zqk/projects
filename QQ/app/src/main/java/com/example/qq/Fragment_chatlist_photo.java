package com.example.qq;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.qq.base.BaseFragment;
import com.example.qq.interfaces.IPresenter;

import butterknife.BindView;

public class Fragment_chatlist_photo extends BaseFragment {

    @BindView(R.id.iv_chatlist)
    ImageView ivChatlist;

    public static Fragment_chatlist_photo getInstance(String photo) {
        Fragment_chatlist_photo fragment_chatlist_photo = new Fragment_chatlist_photo();
        Bundle bundle = new Bundle();
        bundle.putString("photo", photo);
        fragment_chatlist_photo.setArguments(bundle);
        return fragment_chatlist_photo;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_chatlist_photo;
    }

    @Override
    protected void initView(View view) {
        String photo = getArguments().getString("photo");
        Glide.with(context).load(photo).into(ivChatlist);
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
}
