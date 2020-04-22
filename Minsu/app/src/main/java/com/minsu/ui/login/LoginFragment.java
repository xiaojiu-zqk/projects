package com.minsu.ui.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minsu.R;
import com.minsu.base.BaseFragment;
import com.minsu.intenfaces.IBasePersenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment {

    @Override
    protected int getLayout() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected IBasePersenter createPersenter() {
        return null;
    }
}
