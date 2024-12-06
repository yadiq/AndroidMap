package com.hqumath.map.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * ****************************************************************
 * 文件名称: BaseFragment
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:12
 * 文件描述:
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public abstract class BaseFragment extends Fragment {
    protected Activity mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (Activity)context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = initContentView(inflater, container, savedInstanceState);
        //事件监听
        initListener();
        //初始化数据
        initData();
        return rootView;
    }

    protected abstract View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void initListener();

    protected abstract void initData();
}