package com.example.test.ui.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.example.test.MainActivity;
import com.example.test.R;
import com.example.test.adapter.shop.JumpVpAdapter;
import com.example.test.base.BaseActivity;
import com.example.test.base.IBasePersenter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import io.reactivex.functions.Consumer;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class JumpActivity extends BaseActivity {

    @BindView(R.id.mVp_jump)
    ViewPager mVp;
    private ArrayList<View> list;
    private TextView tv_timer;
    Disposable disposable;

    @Override
    protected int getLayout() {
        return R.layout.activity_jump;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected IBasePersenter createPersenter() {
        return null;
    }

    @Override
    protected void initData() {
        View p1 = LayoutInflater.from(this).inflate(R.layout.layout_jump_page, null);
        View p2 = LayoutInflater.from(this).inflate(R.layout.layout_jump_page1, null);
        View p3 = LayoutInflater.from(this).inflate(R.layout.layout_jump_page2, null);

        list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);

        JumpVpAdapter vpAdapter = new JumpVpAdapter(this,list);
        mVp.setAdapter(vpAdapter);

        tv_timer = p3.findViewById(R.id.tv_timer);
        //点按钮跳转
        TextView jump = p3.findViewById(R.id.jump);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JumpActivity.this, ShopActivity.class));
                cancelCallback();
            }
        });

        //页码的点击监听
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 2){//在最后一页执行倒计时
                    //TODO            Interval操作符(有范围)：创建一个按照固定时间发射整数序列的Observable
                    disposable = Observable.intervalRange(0, 4, 0, 1, TimeUnit.SECONDS) //起始值，发送总数量，初始延迟，固定延迟
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            //两种写法    1. lambda表达式写法：
//                            .subscribe(time -> tv_timer.setText((10 - time) + "s"),
//                                    Throwable::printStackTrace,
//                                    () -> {
//                                        startActivity(new Intent(SplashActivity.this,MainActivity.class));
//                                    }
//                            );

                            .subscribe(new Consumer<Long>() {
                                @Override
                                public void accept(Long aLong) throws Exception {
                                    long time = 3 - aLong;
                                    tv_timer.setText(time+"s");
                                    if(time == 0){//倒计时结束自动跳转
                                        startActivity(new Intent(JumpActivity.this, ShopActivity.class));
                                    }
                                }
                            });
                }else{
                    cancelCallback();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //取消订阅的方法
    private void cancelCallback() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

}