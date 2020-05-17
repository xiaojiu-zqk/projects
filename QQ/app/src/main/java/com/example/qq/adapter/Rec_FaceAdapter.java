package com.example.qq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.qq.R;
import com.example.qq.face.FaceTabVo;

import java.util.List;

public class Rec_FaceAdapter extends RecyclerView.Adapter {
    private final List<FaceTabVo> tabList;
    private Context context;

    public Rec_FaceAdapter(List<FaceTabVo> tabList,Context context) {
        this.tabList = tabList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_face, parent,false);
        return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Vh vh = (Vh) holder;
        vh.img.setBackgroundResource(tabList.get(position).getFaceId());
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.Onclick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tabList.size();
    }

    public class Vh extends RecyclerView.ViewHolder {

        private final ImageView img;

        public Vh(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.faceItem_img);
        }
    }

    public OnFaceClick click;

    public void setOnFaceClick(OnFaceClick click){
        this.click = click;

    }


    public interface OnFaceClick{
        void Onclick(int position);
    }

}
