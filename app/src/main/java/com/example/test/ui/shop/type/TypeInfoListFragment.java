package com.example.test.ui.shop.type;

import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.adapter.shop.home.channel.ChannelAdapter;
import com.example.test.base.BaseFragment;
import com.example.test.model.bean.shop.home.channel.ChannelBean;
import com.example.test.model.bean.shop.home.channel.ChannelTypeBean;
import com.example.test.presenter.shop.home.channel.ChannelPresenter;
import com.example.test.utils.TxtUtils;
import com.example.test.view.shop.home.channel.IChannel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TypeInfoListFragment extends BaseFragment<IChannel.Presenter> implements IChannel.View {

    @BindView(R.id.tv_channel1_title)
    TextView tv_Title;
    @BindView(R.id.tv_channel1_front_desc)
    TextView tv_Desc;
    @BindView(R.id.mRlv_channelType)
    RecyclerView mRlv;
    private String id;
    private String name;
    private String front_name;
    List<ChannelTypeBean.DataBeanX.GoodsListBean> list;
    private ChannelAdapter channelAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_channel;
    }

    @Override
    protected IChannel.Presenter createPrenter() {
        return new ChannelPresenter(this);
    }

    @Override
    protected void initView() {
        TxtUtils.setTextView(tv_Title,name);
        TxtUtils.setTextView(tv_Desc,front_name);

        mRlv.setLayoutManager(new GridLayoutManager(mContext, 2));
        list = new ArrayList<>();
        channelAdapter = new ChannelAdapter(mContext, list);
        mRlv.setAdapter(channelAdapter);
    }

    @Override
    protected void initData() {
        presenter.getChannelType(id);
    }

    @Override
    public void getChannelReturn(ChannelBean result) {

    }

    @Override
    public void getChannelTypeReturn(ChannelTypeBean result) {
        List<ChannelTypeBean.DataBeanX.GoodsListBean> filterCategory = result.getData().getGoodsList();
        list.addAll(filterCategory);
        channelAdapter.notifyDataSetChanged();
    }

    public void getId(String id) {
        this.id = id;
    }

    public void getName(String name, String front_name) {
        this.name = name;
        this.front_name = front_name;
    }
}
