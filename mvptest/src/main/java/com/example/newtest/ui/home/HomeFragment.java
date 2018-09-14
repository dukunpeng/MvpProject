package com.example.newtest.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.newtest.R;
import com.example.newtest.base.BaseFragment;
import com.example.newtest.base.MvpBaseFragment;

import java.util.Date;

/**
 * Created by Mark on 2018/7/13.
 */

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,null);
        TextView textView =  view.findViewById(R.id.text);
        textView.setText(""+System.currentTimeMillis());
        return view;
    }
    /* *//**
     * 设置页面资源
     *
     * @return 资源ID
     *//*
    @Override
    public int getContentLayoutResourceId() {
        return R.layout.fragment_home;
    }

    @Override
    public void loadData() {

    }

    @Override*/
/*    public void initViews() {

     TextView textView =  findViewById(R.id.text);
     textView.setText(""+new Date().getTime());
    }*/
}
