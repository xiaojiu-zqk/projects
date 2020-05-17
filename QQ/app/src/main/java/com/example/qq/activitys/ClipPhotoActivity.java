package com.example.qq.activitys;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.qq.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClipPhotoActivity extends AppCompatActivity {

    @BindView(R.id.img_clip)
    ImageView imgClip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_pnoto);
        ButterKnife.bind(this);
        String path = getIntent().getStringExtra("path");
        Glide.with(this).load(path).into(imgClip);
    }
}
