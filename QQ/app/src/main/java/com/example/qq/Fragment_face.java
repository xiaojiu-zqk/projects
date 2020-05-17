package com.example.qq;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qq.adapter.Rec_FaceListAdapter;
import com.example.qq.face.FaceListItemVo;
import com.example.qq.utils.SmileyParser;

import java.util.List;


public class Fragment_face extends Fragment {
    private Rec_FaceListAdapter.ListClick listClick;

    private List<FaceListItemVo> faceList;

    public static Fragment_face getInstance(int posi, Rec_FaceListAdapter.ListClick listClick){
        Fragment_face fragment_face = new Fragment_face();
        Bundle bundle = new Bundle();
        bundle.putInt("posi",posi);
        fragment_face.listClick = listClick;
        fragment_face.setArguments(bundle);
        return fragment_face;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_face, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int posi = getArguments().getInt("posi");
        RecyclerView faceRec = view.findViewById(R.id.face_rec);

        faceList = SmileyParser.getInstance(getActivity()).getFaceItemListByPos(posi);
        if (faceList.size()>0){
            FaceListItemVo faceListItemVo = faceList.get(0);
            if (faceListItemVo.getFaceType() == SmileyParser.FACE_TYPE_1){
                faceRec.setLayoutManager(new GridLayoutManager(getActivity(),8));
            }else {
                faceRec.setLayoutManager(new GridLayoutManager(getActivity(),5));
            }
            Rec_FaceListAdapter adapter = new Rec_FaceListAdapter(faceList,getActivity());
            adapter.addTabOnClickListener(listClick);
            faceRec.setAdapter(adapter);
        }
    }
}
