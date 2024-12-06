package com.hqumath.map.ui.repos;

import com.hqumath.map.base.BasePresenter;
import com.hqumath.map.bean.ReposEntity;
import com.hqumath.map.net.HttpListener;
import com.hqumath.map.repository.MyModel;

import java.util.ArrayList;
import java.util.List;

/**
 * ****************************************************************
 * 文件名称: LoginPresenter
 * 作    者: Created by gyd
 * 创建时间: 2019/1/21 15:12
 * 文件描述: 业务逻辑层
 * 注意事项:
 * 版权声明:
 * ****************************************************************
 */
public class MyReposPresenter extends BasePresenter<MyReposPresenter.Contract> {

    public interface Contract {
        void onGetListSuccess(boolean isRefresh, boolean isNewDataEmpty);

        void onGetListError(String errorMsg, String code, boolean isRefresh);
    }

    private final static int pageSize = 10;//分页
    private long pageIndex;//索引
    public List<ReposEntity> mData = new ArrayList<>();//列表数据


    public MyReposPresenter() {
        mModel = new MyModel();
    }

    /**
     * 获取列表
     *
     * @param isRefresh true 下拉刷新；false 上拉加载
     */
    public void getMyRepos(boolean isRefresh) {
        if (mView == null) return;
        if (isRefresh) {
            pageIndex = 1;
        }
        String userName = "yadiq";
        ((MyModel) mModel).getMyRepos(userName, pageSize, pageIndex, new HttpListener() {
            @Override
            public void onSuccess(Object object) {
                if (mView == null) return;
                List<ReposEntity> list = (List<ReposEntity>) object;
                pageIndex++;//偏移量+1
                if (isRefresh) //下拉覆盖，上拉增量
                    mData.clear();
                if (!list.isEmpty())
                    mData.addAll(list);
                mView.onGetListSuccess(isRefresh, list.isEmpty());
            }

            @Override
            public void onError(String errorMsg, String code) {
                if (mView == null) return;
                mView.onGetListError(errorMsg, code, isRefresh);
            }
        });
    }
}
