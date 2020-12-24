package com.example.test.ui.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.example.test.R;
import com.example.test.ui.shop.home.HomeFragment;
import com.example.test.ui.shop.home.category.CategoryActivity;
import com.example.test.ui.shop.me.MeFragment;
import com.example.test.ui.shop.shoppingcart.Shopping_CartFragment;
import com.example.test.ui.shop.special.SpecialFragment;
import com.example.test.ui.shop.type.TypeFragment;
import com.example.test.utils.CustomViewPager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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
    private int pos;
    private String[] str = {"首页", "专题", "分类", "购物车", "我的"};
    private ArrayList<Fragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        bind = ButterKnife.bind(this);
        initView();
        //initVp();
        initFragment();//碎片
        initTab();//Tab添加
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
            @Override
            public void onClick(View v) {
                finishAndRemoveTask();
            }
        });

        //禁止滑动
        //mVp.setScanScroll(false);
    }

    private void initVp() {
        //准备fragment

        list = new ArrayList<>();
        list.add(homeFragment);
        list.add(specialFragment);
        list.add(typeFragment);
        list.add(shopping_cartFragment);
        list.add(meFragment);


        VpAdapter vpAdapter = new VpAdapter(getSupportFragmentManager());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //回调打开购物车
        if (resultCode == CategoryActivity.RECOMMEND_CAR) {
            mTab.getTabAt(3).select();
            //mVp.setCurrentItem(3);
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}