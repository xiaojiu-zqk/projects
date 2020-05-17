package com.example.qq.base;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.example.qq.interfaces.IBaseView;
import com.example.qq.interfaces.IPresenter;
import com.example.qq.utils.SystemUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity<V extends IBaseView,P extends IPresenter> extends AppCompatActivity implements IBaseView {

    protected Context context;
    protected P presenter;

    Unbinder unbinder;

    protected Bundle pageBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.pageBundle = savedInstanceState;
        //通过修改density实现屏幕适配  默认以宽度为准进行计算  横竖屏切换的时候可以通过 DensityUtils.setOrientation(this,DensityUtils.HEIGHT);
        /*Configuration configuration = this.getResources().getConfiguration(); //获取设备信息
        int ori = configuration.orientation; //屏幕方向
        if(ori == Configuration.ORIENTATION_LANDSCAPE){
            DensityUtils.setOrientation(this,DensityUtils.HEIGHT);
        }else{
            DensityUtils.setDefault(this);
        }*/
        setContentView(getLayout());
        if(!SystemUtils.checkNetWork()){
            //自定义布局实现无网络状态的提示
            /*View view = LayoutInflater.from(this).inflate(R.layout.layout_network_error,null);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            addContentView(view,params);*/
        }else{
            context = this;
            unbinder = ButterKnife.bind(this);
            initView();
            presenter = createPersenter();
            if (presenter != null){
                presenter.attchView(this);
                initData();
            }
        }
    }

    //获取布局
    protected abstract int getLayout();

    //初始化布局
    protected abstract void initView();

    //初始化数据
    protected abstract void initData();

    //创建P
    protected abstract P createPersenter();

    @Override
    protected void onResume() {
        super.onResume();
        if(presenter != null){
            presenter.attchView(this);
        }
       // MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
       // MobclickAgent.onPause(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError(String err) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter != null){
            presenter.detachView();
        }

        if(unbinder != null){
            unbinder.unbind();
        }
    }


}
