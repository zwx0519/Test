package com.example.test.ui.shop;

import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.test.R;
import com.example.test.adapter.shop.ShopAdapter;
import com.example.test.ui.shop.home.HomeFragment;
import com.example.test.ui.shop.me.MeFragment;
import com.example.test.ui.shop.shoppingcart.Shopping_CartFragment;
import com.example.test.ui.shop.special.SpecialFragment;
import com.example.test.ui.shop.type.TypeFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ShopActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.mRl_shop)
    RelativeLayout mRl;
    private TabLayout mTab;
    private FragmentManager mFm;
    //private CustomViewPager mVp;
    private HomeFragment homeFragment;
    private SpecialFragment specialFragment;
    private TypeFragment typeFragment;
    private Shopping_CartFragment shopping_cartFragment;
    private MeFragment meFragment;
    private Unbinder bind;
    //private int pos;
    private String[] str = {"首页", "专题", "分类", "购物车", "我的"};
    private ArrayList<Fragment> list;

    private Disposable disposable;
    private PopupWindow window;
    private TextView tv_time;
    private ViewPager mVp;
    private boolean aBoolean=true;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;

    //onCreat开始获取视图
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        bind = ButterKnife.bind(this);

    }

    private void initView() {
        homeFragment = new HomeFragment();
        specialFragment = new SpecialFragment();
        typeFragment = new TypeFragment();
        shopping_cartFragment = new Shopping_CartFragment();
        meFragment = new MeFragment();

        mTab = (TabLayout) findViewById(R.id.mTab_shop);
        //mVp = (CustomViewPager) findViewById(R.id.mVp_shop);
        //给左边添加图片
        toolbar.setNavigationIcon(R.mipmap.b2);
        //Toolbar和Activity结合
        setSupportActionBar(toolbar);
        //给图片设置监听
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                finishAndRemoveTask();
            }
        });

        //禁止滑动
        //mVp.setScanScroll(false);


    }

    private void initFragment() {
        //得到fragment管理器
        mFm = getSupportFragmentManager();
        //放入布局管理器
        mFm.beginTransaction()
                .add(R.id.mRl_shop, homeFragment)//添加
                .add(R.id.mRl_shop, specialFragment)
                .add(R.id.mRl_shop, typeFragment)
                .add(R.id.mRl_shop, shopping_cartFragment)
                .add(R.id.mRl_shop, meFragment)
                .hide(specialFragment)//隐藏
                .hide(typeFragment)
                .hide(shopping_cartFragment)
                .hide(meFragment)
                .commit();//提交事物
    }

    private void initTab() {
        //添加Tab页
        mTab.addTab(mTab.newTab().setText("首页").setIcon(R.drawable.home_selector));
        mTab.addTab(mTab.newTab().setText("专题").setIcon(R.drawable.special_selector));
        mTab.addTab(mTab.newTab().setText("分类").setIcon(R.drawable.type_selector));
        mTab.addTab(mTab.newTab().setText("购物车").setIcon(R.drawable.shoppingcart_selector));
        mTab.addTab(mTab.newTab().setText("我的").setIcon(R.drawable.me_selector));

        //通过tablayout的监听器，实现和fragment的联动
        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //开启事物
                FragmentTransaction t1 = mFm.beginTransaction();
                if (tab.getPosition() == 0) {
                    t1.show(homeFragment)
                            .hide(specialFragment)
                            .hide(typeFragment)
                            .hide(shopping_cartFragment)
                            .hide(meFragment);
                } else if (tab.getPosition() == 1) {
                    t1.show(specialFragment)
                            .hide(homeFragment)
                            .hide(typeFragment)
                            .hide(shopping_cartFragment)
                            .hide(meFragment);
                } else if (tab.getPosition() == 2) {
                    t1.show(typeFragment)
                            .hide(homeFragment)
                            .hide(specialFragment)
                            .hide(shopping_cartFragment)
                            .hide(meFragment);
                } else if (tab.getPosition() == 3) {
                    t1.show(shopping_cartFragment)
                            .hide(homeFragment)
                            .hide(specialFragment)
                            .hide(typeFragment)
                            .hide(meFragment);
                } else {
                    t1.show(meFragment)
                            .hide(homeFragment)
                            .hide(specialFragment)
                            .hide(typeFragment)
                            .hide(shopping_cartFragment);
                }
                t1.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    //onWindowFocusChanged 已经获取到视图 展示视图内容
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (aBoolean){
            initPop();//先弹出来popu
            initView();
            initFragment();//碎片
            initTab();//Tab添加
            aBoolean=false;
        }
        //initVp();
    }

    private void initPop() {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(R.mipmap.h);
        integerList.add(R.mipmap.j);
        integerList.add(R.mipmap.p);

        View view = View.inflate(this,R.layout.layout_shop_popu,null);
        window = new PopupWindow(view, -1,-1);
        tv_time = view.findViewById(R.id.tv_dao);
        img1 = view.findViewById(R.id.img_1);
        img2 = view.findViewById(R.id.img_2);
        img3 = view.findViewById(R.id.img_3);

        mVp = view.findViewById(R.id.mVp_shop);
        ShopAdapter vpAdapter = new ShopAdapter(this,integerList, window);
        mVp.setAdapter(vpAdapter);
        //展示完Popu
        popupVpCli();
        //进行显示
        window.showAsDropDown(this.findViewById(R.id.mRl_shop));
    }

    //TODO 页码的点击监听
    private void popupVpCli() {
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    img1.setImageResource(R.mipmap.icon_select);
                    img2.setImageResource(R.mipmap.icon_noselect);
                    img3.setImageResource(R.mipmap.icon_noselect);
                }
                if (position == 1) {
                    img1.setImageResource(R.mipmap.icon_noselect);
                    img2.setImageResource(R.mipmap.icon_select);
                    img3.setImageResource(R.mipmap.icon_noselect);
                }
                if(position == 2){//在最后一页执行倒计时
                    tv_time.setVisibility(View.VISIBLE);
                    //TODO       Interval操作符(有范围)：创建一个按照固定时间发射整数序列的Observable
                    disposable = Observable.intervalRange(0, 4, 0, 1, TimeUnit.SECONDS) //起始值，发送总数量，初始延迟，固定延迟
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Long>() {
                                @Override
                                public void accept(Long aLong) throws Exception {
                                    long time = 3 - aLong;
                                    tv_time.setText(time + "s");
                                    if (time == 0) {
                                        window.dismiss();
                                    }
                                }
                            });
                    img1.setImageResource(R.mipmap.icon_noselect);
                    img2.setImageResource(R.mipmap.icon_noselect);
                    img3.setImageResource(R.mipmap.icon_select);

                }else{
                    tv_time.setVisibility(View.GONE);//隐藏视图
                    cancelCallback();//取消订阅的方法
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    //TODO 取消订阅的方法
    private void cancelCallback() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

    private void initVp() {
        //准备fragment

        list = new ArrayList<>();
        list.add(homeFragment);
        list.add(specialFragment);
        list.add(typeFragment);
        list.add(shopping_cartFragment);
        list.add(meFragment);


        //VpAdapter vpAdapter = new VpAdapter(getSupportFragmentManager());
        //mVp.setAdapter(vpAdapter);
        // mTab.setupWithViewPager(mVp);

        mTab.getTabAt(0).setIcon(R.drawable.home_selector);
        mTab.getTabAt(1).setIcon(R.drawable.special_selector);
        mTab.getTabAt(2).setIcon(R.drawable.type_selector);
        mTab.getTabAt(3).setIcon(R.drawable.shoppingcart_selector);
        mTab.getTabAt(4).setIcon(R.drawable.me_selector);


//        Intent intent = getIntent();
//        pos = intent.getIntExtra("pos", 0);
//        mVp.setCurrentItem(pos);
    }

    //TODO Vp内部适配器
    class VpAdapter extends FragmentStatePagerAdapter {
        public VpAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return str[position];
        }
    }
}