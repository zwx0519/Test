package com.example.test.ui.shop;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.test.R;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

//TODO 用来显示启动页的效果
public class JumpFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.txt_time)//跳过
    TextView txtTime;
    @BindView(R.id.txt_comein)//立即体验
    TextView txtComeIn;
    public boolean isUpdate;

    private static int time = 15; //15-10区间不能跳过 < 10 可以跳过

    private int index; //当前页面的下标
    private boolean live; //当前页面是显示状态

    //TODO 传递下标
    public static JumpFragment getInstance(int index){
        JumpFragment fragment = new JumpFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index",index);
        fragment.setArguments(bundle);
        return fragment;
    }

    //TODO 懒加载
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        live = isVisibleToUser;
    }

    //TODO 加载视图
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jump,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        initListener();
        time = 15;//时间为15秒
        index = getArguments().getInt("index");//下标
        txtComeIn.setVisibility(View.GONE);//隐藏立即体验
        int resId=0;
        if(index == 1){//第一页
            resId = R.mipmap.start_one;
        }else if(index == 2){//第二页
            resId = R.mipmap.start_two;
        }else if(index == 3){//第三页
            resId = R.mipmap.start_three;
        }
        img.setImageResource(resId);
        if(index == 3){
            txtTime.setVisibility(View.VISIBLE);
            //开启倒计时
            startTime();
        }else{
            txtTime.setVisibility(View.GONE);
        }
    }

    //TODO 开启倒计时
    private void startTime() { myHndler.postDelayed(runnable,1000); }

    //TODO 设置倒计时的显示
    private void setTime(String str){ txtTime.setText(str); }

    //TODO 监听
    private void initListener() {
        txtComeIn.setOnClickListener(this);
        txtTime.setOnClickListener(this);
    }

    //TODO 点击监听
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_comein:
                goMain();
                break;
            case R.id.txt_time:
                goMain();
                break;
        }
    }

    //TODO 跳转到主页
    private void goMain() {
        if(isUpdate) return;
        live=false;
        Intent intent = new Intent(getContext(), ShopActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    //TODO Handler
    private MyHandler myHndler = new MyHandler(this);
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.i("TAG",Thread.currentThread().getName());
            time--;
            if(time < 0){//如果倒计时进行完毕 就跳转
                goMain(); //调整到主页
            }else{
                Message msg = new Message();
                msg.what = 1;
                if(time < 10){
                    // 倒计时 跳过+time 立即体验按钮显示
                    msg.obj = "跳过"+time;
                    msg.arg1 = 10;
                }else{
                    //time
                    msg.obj = time;
                }
                myHndler.sendMessage(msg);//发送信息
                //只有页面在显示的情况下继续倒计时
                if(live){//当前页面是显示状态 postDelayed进行延迟操作
                    myHndler.postDelayed(this,1000);
                }
            }
        }
    };

    //TODO 用来解决handler引起的内存泄露问题 原因：内部类默认持有外部类的引用
    static class MyHandler extends Handler {
        //使用弱引用
        WeakReference<JumpFragment> weakReference;
        JumpFragment splaceFragment;
        public MyHandler(JumpFragment fragment){
            weakReference = new WeakReference<JumpFragment>(fragment);
            splaceFragment = weakReference.get();
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    splaceFragment.setTime(String.valueOf(msg.obj));
                    if(msg.arg1 == 10){//当等于十秒时 开启视图
                        splaceFragment.txtComeIn.setVisibility(View.VISIBLE);
                        splaceFragment.txtComeIn.setEnabled(true);
                        splaceFragment.txtTime.setEnabled(true);
                    }else{//大于十秒时，隐藏
                        splaceFragment.txtComeIn.setVisibility(View.GONE);
                        splaceFragment.txtComeIn.setEnabled(false);
                        splaceFragment.txtTime.setEnabled(false);
                    }
                    break;
            }
        }
    }
}
