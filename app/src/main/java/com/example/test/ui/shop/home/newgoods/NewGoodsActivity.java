package com.example.test.ui.shop.home.newgoods;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.adapter.shop.home.newgoods.NewGoodsListAdapter;
import com.example.test.base.BaseActivity;
import com.example.test.model.bean.shop.home.newgoods.NewGoodsBean;
import com.example.test.model.bean.shop.home.newgoods.NewGoodsListBean;
import com.example.test.presenter.shop.home.newgoods.NewGoodsPresenter;
import com.example.test.utils.ImageLoaderUtils;
import com.example.test.utils.TxtUtils;
import com.example.test.view.shop.home.newgoods.INewGoods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class NewGoodsActivity extends BaseActivity<INewGoods.Persenter> implements INewGoods.View  {

    @BindView(R.id.iv_newgoods_list_img)
    ImageView iv_Img;
    @BindView(R.id.tv_newgoods_list_text)
    TextView tv_Text;
    @BindView(R.id.tv_newgoods_list_all)
    TextView tv_All;
    @BindView(R.id.tv_newgoods_list_price)
    TextView tv_Price;
    @BindView(R.id.img_arrow_up)
    ImageView imgArrowUp;
    @BindView(R.id.img_arrow_down)
    ImageView imgArrowDown;
    @BindView(R.id.layout_price)
    LinearLayout layoutPrice;
    @BindView(R.id.tv_newgoods_list_sort)
    TextView tv_Sort;
    @BindView(R.id.mRlv_NewGoodList)
    RecyclerView mRlv;

    //是否是新品
    private int isNew = 1;
    private int page = 1;
    private int size = 1000;
    private String order;
    private String sort;
    private int categoryId;

    private static final String ASC = "asc";//升序
    private static final String DESC = "desc";//降序
    private static final String DEFAULT = "default";//默认
    private static final String PRICE = "price";//价格
    private static final String CATEGORY = "category";//类别
    private NewGoodsListAdapter newGoodsListAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_new_goods;
    }

    @Override
    protected INewGoods.Persenter createPersenter() {
        return new NewGoodsPresenter(this);
    }

    @Override
    @SuppressLint("ResourceType")
    protected void initView() {
        order = ASC;
        sort = DEFAULT;
        categoryId = 0;
        layoutPrice.setTag(0);
        //文字默认红色
        tv_All.setTextColor(Color.parseColor(NewGoodsActivity.this.getString(R.color.red)));
    }

    @Override
    protected void initData() {
        persenter=new NewGoodsPresenter(this);
        persenter.getNewGoods();
        persenter.getNewGoodsList(getParam());
    }

    @SuppressLint("ResourceType")
    @OnClick({R.id.layout_price,R.id.tv_newgoods_list_all,R.id.tv_newgoods_list_sort})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.layout_price:
                int tag = (int) layoutPrice.getTag();
                if (tag == 0) {
                    resetPriceState();//重置条件选择的所有状态
                    priceStateUp();//按价格升序排序
                    layoutPrice.setTag(1);
                    order = ASC;
                } else if (tag == 1) {
                    resetPriceState();//重置条件选择的所有状态
                    priceStateDown();//价格的降序排列
                    layoutPrice.setTag(0);
                    order = DESC;
                }
                sort = PRICE;
                persenter.getNewGoodsList(getParam());
                break;
            case R.id.tv_newgoods_list_all:
                resetPriceState();//重置条件选择的所有状态
                tv_All.setTextColor(Color.parseColor(NewGoodsActivity.this.getString(R.color.red)));
                sort = DEFAULT;//记录默认
                categoryId = 0;
                persenter.getNewGoodsList(getParam());//调用方法
                break;
            case R.id.tv_newgoods_list_sort:
                resetPriceState();
                tv_Sort.setTextColor(Color.parseColor(NewGoodsActivity.this.getString(R.color.red)));
                sort = CATEGORY;
                persenter.getNewGoodsList(getParam());//调用方法
                break;
        }
    }

    /**
     * 组装当前的接口参数
     * @return
     */
    private HashMap<String, String> getParam() {
        HashMap<String, String> map = new HashMap<>();
        map.put("isNew", String.valueOf(isNew));
        map.put("page", String.valueOf(page));
        map.put("size", String.valueOf(size));
        map.put("order", order);
        map.put("sort", sort);
        map.put("category", String.valueOf(categoryId));
        return map;
    }


    //TODO 按价格升序排序
    @SuppressLint("ResourceType")
    private void priceStateUp() {
        //上面图标变红
        imgArrowUp.setImageResource(R.mipmap.ic_arrow_up_select);
        //下面图标变黑
        imgArrowDown.setImageResource(R.mipmap.ic_arrow_down_normal);
        tv_Price.setTextColor(Color.parseColor(getString(R.color.red)));
    }

    //TODO 价格的降序排列
    @SuppressLint("ResourceType")
    private void priceStateDown() {
        //上面图标变黑
        imgArrowUp.setImageResource(R.mipmap.ic_arrow_up_normal);
        //下面图标变红
        imgArrowDown.setImageResource(R.mipmap.ic_arrow_down_select);
        //给文字颜色
        tv_Price.setTextColor(Color.parseColor(getString(R.color.red)));
    }

    //TODO 重置条件选择的所有状态
    @SuppressLint("ResourceType")
    private void resetPriceState() {
        imgArrowUp.setImageResource(R.mipmap.ic_arrow_up_normal);
        imgArrowDown.setImageResource(R.mipmap.ic_arrow_down_normal);
        tv_Price.setTextColor(Color.parseColor(getString(R.color.black)));
        tv_All.setTextColor(Color.parseColor(getString(R.color.black)));
        tv_Sort.setTextColor(Color.parseColor(getString(R.color.black)));
        layoutPrice.setTag(0);
    }


    @Override
    public void getNewGoodsReturn(NewGoodsBean result) {
        NewGoodsBean.DataBean.BannerInfoBean bannerInfo = result.getData().getBannerInfo();
        ImageLoaderUtils.loadImage(bannerInfo.getImg_url(),iv_Img);
        TxtUtils.setTextView(tv_Text,bannerInfo.getName());
    }

    @Override
    public void getNewGoodsListReturn(NewGoodsListBean result) {
        List<NewGoodsListBean.DataBeanX.DataBean> data = result.getData().getData();
        mRlv.setLayoutManager(new GridLayoutManager(this, 2));
        mRlv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        newGoodsListAdapter = new NewGoodsListAdapter(this, data);
        mRlv.setAdapter(newGoodsListAdapter);
    }
}