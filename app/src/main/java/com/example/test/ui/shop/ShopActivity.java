package com.example.test.ui.shop;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.test.R;
import com.example.test.ui.shop.home.HomeFragment;
import com.example.test.ui.shop.me.MeFragment;
import com.example.test.ui.shop.shoppingcart.Shopping_CartFragment;
import com.example.test.ui.shop.special.SpecialFragment;
import com.example.test.ui.shop.type.TypeFragment;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ShopActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private TabLayout mTab;
    private FragmentManager mFm;
    private HomeFragment homeFragment;
    private SpecialFragment specialFragment;
    private TypeFragment typeFragment;
    private Shopping_CartFragment shopping_cartFragment;
    private MeFragment meFragment;
    private Unbinder bind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        bind = ButterKnife.bind(this);
        initView();
        initFragment();//碎片
        initTab();//Tab添加
    }

    private void initView() {
        mTab = (TabLayout) findViewById(R.id.mTab_shop);
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
    }

    private void initFragment() {
        //准备fragment
        homeFragment = new HomeFragment();
        specialFragment = new SpecialFragment();
        typeFragment = new TypeFragment();
        shopping_cartFragment = new Shopping_CartFragment();
        meFragment = new MeFragment();

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}