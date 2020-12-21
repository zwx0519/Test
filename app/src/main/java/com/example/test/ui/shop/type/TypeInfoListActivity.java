package com.example.test.ui.shop.type;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.test.R;
import com.example.test.app.MyApp;
import com.example.test.base.BaseActivity;
import com.example.test.base.IBasePersenter;
import com.example.test.model.bean.shop.type.TypeInfBean;
import com.example.test.utils.CustomViewPager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TypeInfoListActivity extends BaseActivity {

    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.mTab_channel)
    TabLayout mTab;
    @BindView(R.id.mVp_channel)
    CustomViewPager mVp;
    private List<TypeInfBean.DataBean.CurrentCategoryBean.SubCategoryListBean> list;
    private ArrayList<TypeInfoListFragment> fragments;
    private String name;


    @Override
    protected int getLayout() {
        return R.layout.activity_channel;
    }

    @Override
    protected IBasePersenter createPersenter() {
        return null;
    }
    
    @Override
    protected void initView() {
        list = (List<TypeInfBean.DataBean.CurrentCategoryBean.SubCategoryListBean>) MyApp.getMap().get("typelist");
        fragments = new ArrayList<>();

        //禁止滑动
        mVp.setScanScroll(false);
        
        //图片返回
        ivReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAndRemoveTask();//返回上一个页面
            }
        });

        name = (String) MyApp.getMap().get("typename");
    }


    @Override
    protected void initData() {
        for (int i = 0; i < this.list.size() ; i++) {
            int id = this.list.get(i).getId();
            String name = list.get(i).getName();
            String front_name = list.get(i).getFront_name();

            TypeInfoListFragment typeInfoListFragment = new TypeInfoListFragment();
            typeInfoListFragment.getId(String.valueOf(id));
            typeInfoListFragment.getName(name,front_name);

            fragments.add(typeInfoListFragment);
        }

        VpAdapter vpAdapter = new VpAdapter(getSupportFragmentManager());
        mVp.setAdapter(vpAdapter);
        mTab.setupWithViewPager(mVp);

        for (int i = 0; i < list.size(); i++) {
            if (this.name.equals(list.get(i).getName())) {
                mVp.setCurrentItem(i);
                return;
            }
        }
    }

   public class VpAdapter extends FragmentStatePagerAdapter {
        public VpAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position).getName();
        }
    }
}