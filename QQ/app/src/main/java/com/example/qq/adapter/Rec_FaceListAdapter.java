package com.example.qq.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qq.R;
import com.example.qq.common.Constant;
import com.example.qq.face.FaceListItemVo;
import com.example.qq.utils.DpTools;
import com.example.qq.utils.SmileyParser;

import java.io.InputStream;
import java.util.List;

public class Rec_FaceListAdapter extends RecyclerView.Adapter {

    private final List<FaceListItemVo> list;
    private Context context;
    private ListClick listClick;

    public Rec_FaceListAdapter(List<FaceListItemVo> faceList,Context context) {
        list = faceList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.face_item, parent,false);
        Vh vh = new Vh(inflate);
        ViewGroup.LayoutParams params = vh.imgIcon.getLayoutParams();
        if (params == null){
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        if (viewType == SmileyParser.FACE_TYPE_1){
            vh.txtName.setVisibility(View.GONE);
            params.width = DpTools.dp2px(context, Constant.FACE_SMALL_W);
            params.height = DpTools.dp2px(context,Constant.FACE_SMALL_H);
        }else {
            vh.txtName.setVisibility(View.VISIBLE);
            params.width = DpTools.dp2px(context, Constant.FACE_BIG_W);
            params.height = DpTools.dp2px(context,Constant.FACE_BIG_H);
        }
        vh.imgIcon.setLayoutParams(params);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Vh listVH = (Vh) holder;
        FaceListItemVo itemVo = list.get(position);
        InputStream inputStream = context.getResources().openRawResource(itemVo.getFaceId());
        BitmapDrawable drawable = new BitmapDrawable(inputStream);
        if(itemVo.getFaceType() == SmileyParser.FACE_TYPE_1){
            drawable.setBounds(0,0,40,20);
        }else{
            drawable.setBounds(0,0,60,60);
        }
        listVH.imgIcon.setImageDrawable(drawable);
        listVH.imgIcon.setTag(itemVo);
        listVH.imgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FaceListItemVo tmp = (FaceListItemVo) v.getTag();
                if(listClick != null){
                    listClick.onListClick(itemVo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Vh extends RecyclerView.ViewHolder {
        ImageView imgIcon;
        TextView txtName;
        public Vh(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.img_face);
            txtName = itemView.findViewById(R.id.txt_facename);
        }
    }

    public void addTabOnClickListener(ListClick tabClick){
        this.listClick = tabClick;
    }

    public interface ListClick{
        void onListClick(FaceListItemVo itemVo);
    }

}
