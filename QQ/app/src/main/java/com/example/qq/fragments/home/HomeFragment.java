package com.example.qq.fragments.home;

import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qq.R;
import com.example.qq.activitys.WebActivity;
import com.example.qq.base.BaseFragment;
import com.example.qq.bean.DongTaiBean;
import com.example.qq.interfaces.dongtai.DongTaiConstract;
import com.example.qq.presenter.DongTaiPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseFragment<DongTaiConstract.View, DongTaiConstract.Presenter> implements DongTaiConstract.View {

    AlertDialog alertDialog;
    @BindView(R.id.home_rec)
    RecyclerView homeRec;
    @BindView(R.id.sr)
    SmartRefreshLayout sr;
    private int page = 0;
    private int size = 5;
    private int trendsid = 0;


    private AlertDialog.Builder builder;
    private ArrayList<DongTaiBean.DataBean> dates;
    private HomeRecAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        //showDialog();
        homeRec.setLayoutManager(new LinearLayoutManager(context));
        dates = new ArrayList<>();
        adapter = new HomeRecAdapter(dates);
        homeRec.setAdapter(adapter);
        sr.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                trendsid = dates.size()-1;
                presenter.getDongTaiDate(page,size,trendsid);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                presenter.getDongTaiDate(page,size,trendsid);
            }
        });
    }

    @Override
    protected void initData() {
        presenter.getDongTaiDate(page, size, trendsid);
    }

    @Override
    protected DongTaiConstract.Presenter createPresenter() {
        return new DongTaiPresenter();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError(String err) {

    }

    private void showDialog() {

        if (alertDialog == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.pop_one, null);
            alertDialog = new AlertDialog.Builder(context)
                    .setView(view)
                    .setCancelable(false)
                    .create();
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            TextView txtWord = view.findViewById(R.id.dialog_text);
            TextView noshow = view.findViewById(R.id.notshow);
            Button agree = view.findViewById(R.id.agree);
            noshow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();

                    alertDialog = null;
                }
            });
            agree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    alertDialog = null;
                }
            });

            String str1 = "欢迎您使用虎牙直播！\n我们将通过";
            String str2 = "《虎牙直播App隐私政策》";
            String str3 = "帮助您了解我们收集、使用、存储和共享个人信息的情况，以及您所享有的相关去权利。";
            SpannableStringBuilder builder = new SpannableStringBuilder();
            builder.append(str1);
            SpannableString spannableString = new SpannableString(str2);
            //设置str2的点击事件
            spannableString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    //Toast.makeText(IndexActivity.this, "智汇平台使用协议", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("url", "https://www.baidu.com");
                    startActivity(intent);
                }
            }, 0, str2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //设置str2的文字颜色
            spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), 0, str2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(spannableString);
            SpannableString spannableString3 = new SpannableString(str3);
            builder.append(spannableString3);
            txtWord.setText(builder);
            txtWord.setMovementMethod(LinkMovementMethod.getInstance());

            alertDialog.show();
        }
    }

    @Override
    public void DongTaiDateReturn(DongTaiBean registerBean) {
        List<DongTaiBean.DataBean> data = registerBean.getData();
        adapter.upData(data);
        sr.finishLoadMore();
        sr.finishRefresh();
    }
}
