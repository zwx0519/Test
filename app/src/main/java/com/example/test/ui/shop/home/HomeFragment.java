package com.example.test.ui.shop.home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.example.test.adapter.shop.home.brand.BrandAdapter;
import com.example.test.adapter.shop.home.CategoryAdapter;
import com.example.test.adapter.shop.home.HotGoodsAdapter;
import com.example.test.adapter.shop.home.newgoods.NewGoodsAdapter;
import com.example.test.adapter.shop.home.TopicAdapter;
import com.example.test.app.MyApp;
import com.example.test.base.BaseAdapter;
import com.example.test.base.BaseFragment;
import com.example.test.model.bean.shop.home.HomeBean;
import com.example.test.model.bean.shop.home.brand.BrandNameBean;
import com.example.test.presenter.shop.home.HomePresenter;
import com.example.test.ui.shop.home.brand.BrandActivity;
import com.example.test.ui.shop.home.brand.BrandNameActivity;
import com.example.test.ui.shop.home.channel.ChannelActivity;
import com.example.test.ui.shop.home.newgoods.NewGoodsActivity;
import com.example.test.utils.ImageLoaderUtils;
import com.example.test.utils.ItemDecoration;
import com.example.test.utils.TxtUtils;
import com.example.test.view.shop.home.IHome;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//TODO 主页展示
public class HomeFragment extends BaseFragment<IHome.Presenter> implements IHome.ViewI, View.OnClickListener {
    //
    @BindView(R.id.mBanner_home)
    Banner mBanner_Home;
    @BindView(R.id.mRlv_home_brand)
    RecyclerView mRlv_Brand;
    @BindView(R.id.mRlv_home_newGoods)
    RecyclerView mRlv_NewGoods;
    @BindView(R.id.mRlv_home_hotGoods)
    RecyclerView mRlv_HotGoods;
    @BindView(R.id.mRlv_home_topic)
    RecyclerView mRlv_Topic;
    @BindView(R.id.mLl_category)
    LinearLayout mLl_Category;
    @BindView(R.id.tv_home_brand_name)
    TextView Brand_Name;
    @BindView(R.id.tv_home_newGoods_name)
    TextView NewGoods_Name;
    @BindView(R.id.tv_home_hotGoods_name)
    TextView HotGoods_Name;
    @BindView(R.id.tv_home_topic_name)
    TextView Topic_Name;
    @BindView(R.id.mLl_home_channel)
    LinearLayout mLl_Home_Channel;

    private List<HomeBean.DataBean.HotGoodsListBean> hotGoodsList;
    private List<HomeBean.DataBean.TopicListBean> topicList;
    private List<HomeBean.DataBean.NewGoodsListBean> newGoodsList;
    private List<HomeBean.DataBean.BrandListBean> brandList;
    private List<BrandNameBean.DataBeanX.DataBean> brandnameList;
    private BrandAdapter brandAdapter;
    private NewGoodsAdapter newGoodsAdapter;
    private HotGoodsAdapter hotGoodsAdapter;
    private TopicAdapter topicAdaptefer;
    private CategoryAdapter categoryAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected IHome.Presenter createPrenter() {
        return new HomePresenter(this);
    }

    //TODO 初始化视图
    @Override
    protected void initView() {
        initViewBrand();//品牌制造商直供
        initViewNewGoods(); //新品首发
        initViewHotGoods();//人气推荐
        initViewTopic();//专题精选
    }


    //TODO 品牌制造商直供布局
    private void initViewBrand() {
        mRlv_Brand.setLayoutManager(new GridLayoutManager(mContext, 2));
        brandList = new ArrayList<>();
        brandAdapter = new BrandAdapter(mContext, brandList);
        mRlv_Brand.setAdapter(brandAdapter);

        brandnameList = new ArrayList<>();

        //TODO 点击名字展示品牌制造商直供列表
        Brand_Name.setOnClickListener(this);
    }

    //TODO 新品首发布局
    private void initViewNewGoods() {
        mRlv_NewGoods.setLayoutManager(new GridLayoutManager(mContext, 2));
        newGoodsList = new ArrayList<>();
        newGoodsAdapter = new NewGoodsAdapter(mContext, newGoodsList);
        mRlv_NewGoods.setAdapter(newGoodsAdapter);

        //TODO 点击名字展示新品首发列表
        NewGoods_Name.setOnClickListener(this);
    }

    //TODO 人气推荐布局
    private void initViewHotGoods() {
        mRlv_HotGoods.setLayoutManager(new LinearLayoutManager(mContext));
        mRlv_HotGoods.addItemDecoration(new ItemDecoration(getActivity()));
        hotGoodsList = new ArrayList<>();
        hotGoodsAdapter = new HotGoodsAdapter(mContext, hotGoodsList);
        mRlv_HotGoods.setAdapter(hotGoodsAdapter);
    }

    //TODO 专题精选布局
    private void initViewTopic() {
        mRlv_Topic.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        topicList = new ArrayList<>();
        topicAdaptefer = new TopicAdapter(mContext, topicList);
        mRlv_Topic.setAdapter(topicAdaptefer);
    }

    //TODO 点击名字监听事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_home_brand_name:
                MyApp.getMap().put("bean", brandnameList);
                Intent intent = new Intent(getActivity(), BrandNameActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_home_newGoods_name:
                Intent intent1 = new Intent(getActivity(), NewGoodsActivity.class);
                startActivity(intent1);
                break;
        }
    }

    //TODO 调用方法
    @Override
    protected void initData() {
        presenter.getHome();//首页方法
        presenter.getBrandName(1, 1000);//品牌制造商直供数据列表展示数据
    }

    //TODO 首页的所有数据
    @Override
    public void getHomeReturn(HomeBean result) {
        if (result.getData() != null) {
            //初始化Banner
            initBanner(result.getData().getBanner());
            //动态栏
            initChannel(result.getData().getChannel());
            //新品首发
            initNewGoods(result.getData().getNewGoodsList());
            //人气推荐
            initHotGoods(result.getData().getHotGoodsList());
            //品牌制造商直供
            initBrand(result.getData().getBrandList());
            //专题精选
            initTopic(result.getData().getTopicList());
            //居家
            initCategory(result.getData().getCategoryList());
        }
    }

    //TODO 品牌制造商直供数据列表展示数据
    @Override
    public void getBrandNameReturn(BrandNameBean result) {
        List<BrandNameBean.DataBeanX.DataBean> data = result.getData().getData();
        brandnameList.addAll(data);
    }

    //TODO 初始化居家数据
    private void initCategory(List<HomeBean.DataBean.CategoryListBean> categoryList) {
        //循环放入布局 布局嵌套布局
        for (int i = 0; i < categoryList.size(); i++) {
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home_item, null);
            TextView title = inflate.findViewById(R.id.txt_home_title);
            RecyclerView recyhome = inflate.findViewById(R.id.mRlv_home);

            title.setText(categoryList.get(i).getName());//给上面的标题赋值
            List<HomeBean.DataBean.CategoryListBean.GoodsListBean> goodsList = categoryList.get(i).getGoodsList();

            recyhome.setLayoutManager(new GridLayoutManager(mContext, 2));
            categoryAdapter = new CategoryAdapter(mContext, goodsList);
            recyhome.setAdapter(categoryAdapter);
            mLl_Category.addView(inflate);
        }
    }

    //TODO 初始化专题精选数据
    private void initTopic(List<HomeBean.DataBean.TopicListBean> topicList) {
        this.topicList.addAll(topicList);
        topicAdaptefer.notifyDataSetChanged();
    }

    //TODO 初始化人气推荐数据
    private void initHotGoods(List<HomeBean.DataBean.HotGoodsListBean> hotGoodsList) {
        this.hotGoodsList.addAll(hotGoodsList);
        hotGoodsAdapter.notifyDataSetChanged();
    }

    //TODO 初始化新品首发数据
    private void initNewGoods(List<HomeBean.DataBean.NewGoodsListBean> newGoodsList) {
        this.newGoodsList.addAll(newGoodsList);
        newGoodsAdapter.notifyDataSetChanged();
    }

    //TODO 初始化品牌制造商直供数据
    private void initBrand(List<HomeBean.DataBean.BrandListBean> brandList) {
        this.brandList.addAll(brandList);
        brandAdapter.notifyDataSetChanged();
        //点击条目进入详情页面
        brandAdapter.addListClick(new BaseAdapter.IListClick() {
            @Override
            public void itemClick(int pos) {
                Intent intent = new Intent(getContext(), BrandActivity.class);
                intent.putExtra("name", brandList.get(pos).getName());
                intent.putExtra("simple", brandList.get(pos).getSimple_desc());
                intent.putExtra("pic", brandList.get(pos).getList_pic_url());
                startActivity(intent);
            }
        });
    }

    //TODO 初始化动态栏数据
    private void initChannel(List<HomeBean.DataBean.ChannelBean> channel) {
        mLl_Home_Channel.removeAllViews();//清空所有视图
        //设置 宽 高 权重
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        for (HomeBean.DataBean.ChannelBean item : channel) {
            View channe = LayoutInflater.from(getContext()).inflate(R.layout.layout_channel_item, mLl_Home_Channel, false);
            ImageView img = channe.findViewById(R.id.iv_channel_img);
            TextView txtChannel = channe.findViewById(R.id.tv_channel_name);
            ImageLoaderUtils.loadImage(item.getIcon_url(), img);
            TxtUtils.setTextView(txtChannel, item.getName());
            txtChannel.setGravity(Gravity.CENTER);
            channe.setLayoutParams(params);
            mLl_Home_Channel.addView(channe);


            channe.setTag(item);
            channe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = ((HomeBean.DataBean.ChannelBean) v.getTag()).getIcon_url();
                    MyApp.getMap().put("url", url);

                    String name = ((HomeBean.DataBean.ChannelBean) v.getTag()).getName();
                    MyApp.getMap().put("name", name);
                    startActivity(new Intent(mActivity, ChannelActivity.class));
                }
            });
        }


    }

    //TODO 初始化Banner数据
    private void initBanner(List<HomeBean.DataBean.BannerBean> list) {
        Log.i("TAG", list.size() + "  ");
        //轮播图
        mBanner_Home.setImages(list).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                HomeBean.DataBean.BannerBean data = (HomeBean.DataBean.BannerBean) path;
                Glide.with(context).load(data.getImage_url()).into(imageView);
            }
        }).start();

    }
}
