package com.example.qq.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.qq.Fragment_face;

import java.util.ArrayList;

public class FaceVPAdpater extends FragmentPagerAdapter {

    private final ArrayList<Fragment_face> fragment_faces;

    public FaceVPAdpater(FragmentManager fm, ArrayList<Fragment_face> fragment_faces) {
        super(fm);
        this.fragment_faces = fragment_faces;
    }

    @Override
    public Fragment getItem(int position) {
        return fragment_faces.get(position);
    }

    @Override
    public int getCount() {
        return fragment_faces.size();
    }
}
