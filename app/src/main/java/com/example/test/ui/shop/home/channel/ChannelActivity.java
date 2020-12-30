package com.example.test.ui.shop.home.channel;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.test.R;
import com.example.test.app.MyApp;
import com.example.test.base.BaseActivity;
import com.example.test.model.bean.shop.home.channel.ChannelBean;
import com.example.test.model.bean.shop.home.channel.ChannelTypeBean;
import com.example.test.presenter.shop.home.channel.ChannelPresenter;
import com.example.test.utils.CustomViewPager;
import com.example.test.view.shop.home.channel.IChannel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

//TODO 动态栏的tab展示
public class ChannelActivity extends BaseActivity<IChannel.Presenter> implements IChannel.View {

    @BindView(R.id.mTab_channel)
    TabLayout mTab;
    @BindView(R.id.mVp_channel)
    CustomViewPager mVp;
    @BindView(R.id.iv_return)
    ImageView ivReturn;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.iv_share)
    ImageView ivShare;

    private String url;
    private List<ChannelFragment> fragments;
    private List<ChannelBean.DataBean.CategoryListBean> list;
    private String name;

    @Override
    protected int getLayout() {
        return R.layout.activity_channel;
    }

    @Override
    protected void initView() {
        url = (String) MyApp.getMap().get("url");
        name = (String) MyApp.getMap().get("name");
        Log.e("TAG", name + "");
        fragments = new ArrayList<>();
        list = new ArrayList<>();

        //禁止滑动
        mVp.setScanScroll(false);

        //图片返回
        ivReturn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                finishAndRemoveTask();//返回上一个页面
            }
        });

    }

    @Override
    protected IChannel.Presenter createPersenter() {
        return new ChannelPresenter(this);
    }

    @Override
    protected void initData() {
        persenter.getChannel(url);
    }

    @Override
    public void getChannelReturn(ChannelBean result) {
        List<ChannelBean.DataBean.CategoryListBean> brotherCategory = result.getData().getCategoryList();
        list.addAll(brotherCategory);

        for (int i = 0; i < list.size(); i++) {
            int id = list.get(i).getId();
            String name = list.get(i).getName();
            String front_desc = list.get(i).getFront_desc();
            ChannelFragment channelFragment = new ChannelFragment();
            channelFragment.getId(id + "");
            channelFragment.getName(name, front_desc);
            fragments.add(channelFragment);
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

    @Override
    public void getChannelTypeReturn(ChannelTypeBean result) {

    }

    class VpAdapter extends FragmentStatePagerAdapter {
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