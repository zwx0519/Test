package com.live.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<P extends IBasePersenter> extends Fragment implements IBaseView {

    protected P presenter;
    Unbinder binder;
    protected Context mContext;
    protected Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mContext = getContext();//获取上下文
        mActivity = getActivity();//获取activity
        int layout = getLayout();//获取布局
        View view = null;
        if(layout <= 0){
            new RuntimeException("布局非法");
        }else{
           view = inflater.inflate(layout,container,false);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binder = ButterKnife.bind(this,view);
        presenter = createPrenter();
        if(presenter != null){
            presenter.attachView(this);
        }
        initView();//视图
        initData();//数据
    }

    protected abstract int getLayout();
    protected abstract P createPrenter();
    protected abstract void initView();
    protected abstract void initData();

    @Override
    public void tips(String tip) {//错误时的view方法
        Toast.makeText(mActivity, tip, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loading(int visible) {

    }

    @Override
    public void showToast(String msg, int time) {
        Toast.makeText(mActivity, msg,time).show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(binder != null){
            binder.unbind();
        }
        if(presenter != null){
            presenter.unAttachView();
        }
        mActivity = null;
        mContext = null;
    }
}
