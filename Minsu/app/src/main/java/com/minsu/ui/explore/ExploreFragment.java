package com.minsu.ui.explore;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.minsu.MyView.MyTab;
import com.minsu.R;
import com.minsu.base.BaseFragment;
import com.minsu.intenfaces.IBasePersenter;
import com.minsu.utils.GpsUtils;
import com.minsu.utils.PermissionUtils;
import com.youth.banner.Banner;

import java.util.List;

import butterknife.BindView;


public class ExploreFragment extends BaseFragment implements PermissionUtils.PermissionCallbacks,MyTab.Addonclick{

    @BindView(R.id.banner_explore)
    Banner bannerExplore;
    @BindView(R.id.txt_left)
    TextView txtLeft;
    @BindView(R.id.txt_right)
    TextView txtRight;
    @BindView(R.id.mytab)
    MyTab mytab;
    private String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @Override
    protected int getLayout() {
        return R.layout.fragment_explore;
    }

    @Override
    protected void initView() {
        mytab.initview();
        mytab.setAddonclick(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GpsUtils.unregister();
    }

    //实现定位效果
    public void doclick() {
        GpsUtils.register(context, 0, 0, new GpsUtils.OnLocationChangeListener() {
            @Override
            public void getLastKnownLocation(Location location) {
                Log.e("xyh", "onLocationChanged: " + location.getLatitude());
            }

            @Override
            public void onLocationChanged(Location location) {
                //位置信息变化时触发
                Log.e("xyh", "定位方式：" + location.getProvider());
                Log.e("xyh", "纬度：" + location.getLatitude());
                Log.e("xyh", "经度：" + location.getLongitude());
                Log.e("xyh", "海拔：" + location.getAltitude());
                Log.e("xyh", "时间：" + location.getTime());
                Log.e("xyh", "国家：" + GpsUtils.getCountryName(context, location.getLatitude(), location.getLongitude()));
                Log.e("xyh", "获取地理位置：" + GpsUtils.getAddress(context, location.getLatitude(), location.getLongitude()));
                Log.e("xyh", "所在地：" + GpsUtils.getLocality(context, location.getLatitude(), location.getLongitude()));
                Log.e("xyh", "所在街道：" + GpsUtils.getStreet(context, location.getLatitude(), location.getLongitude()));

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
        });
    }

    @Override
    protected IBasePersenter createPersenter() {
        return null;
    }

    @Override
    public void onPermissionsAllGranted(int requestCode, List<String> perms, boolean isAllGranted) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void onclick(int msg) {

    }
}
