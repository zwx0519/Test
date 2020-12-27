package com.example.test.ui.shop.home.brand;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.adapter.shop.home.brand.BrandNameDetailAdapter;
import com.example.test.app.MyApp;
import com.example.test.base.BaseActivity;
import com.example.test.base.BaseAdapter;
import com.example.test.model.bean.shop.home.brand.BrandNameDetailBean;
import com.example.test.model.bean.shop.home.brand.BrandNameDetailListBean;
import com.example.test.presenter.shop.home.brand.BrandNameDetailPresenter;
import com.example.test.ui.shop.home.category.CategoryActivity;
import com.example.test.ui.shop.home.newgoods.NewGoodsActivity;
import com.example.test.utils.ImageLoaderUtils;
import com.example.test.utils.TxtUtils;
import com.example.test.view.shop.home.brand.IBrand;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BrandNameDetailActivity extends BaseActivity<IBrand.Presenter> implements IBrand.View {

    @BindView(R.id.iv_brand_name_detail_img)
    ImageView iv_Img;
    @BindView(R.id.tv_brand_name_detail_text)
    TextView tv_Text;
    @BindView(R.id.tv_brand_name_detail_desc)
    TextView tv_Desc;
    @BindView(R.id.mRlv_Brand_name_detail)
    RecyclerView mRlv;
    private String id;
    private ArrayList<BrandNameDetailListBean.DataBeanX.DataBean> list;
    private BrandNameDetailAdapter brandNameDetailAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_brand_name_detail;
    }

    @Override
    protected void initView() {
        list = new ArrayList<>();
        mRlv.setLayoutManager(new GridLayoutManager(this,2));
        brandNameDetailAdapter = new BrandNameDetailAdapter(this, list);
        mRlv.setAdapter(brandNameDetailAdapter);

        brandNameDetailAdapter.addListClick(new BaseAdapter.IListClick() {
            @Override
            public void itemClick(int pos) {
                Intent intent = new Intent(BrandNameDetailActivity.this, CategoryActivity.class);
                MyApp.getMap().put("categoryId",String.valueOf(list.get(pos).getId()));
                startActivity(intent);
            }
        });
    }

    @Override
    protected IBrand.Presenter createPersenter() {
        return new BrandNameDetailPresenter(this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        persenter.getBrand(id);
        persenter.getBrandList(id);
    }

    @Override
    public void getBrand(BrandNameDetailBean result) {
        BrandNameDetailBean.DataBean.BrandBean data = result.getData().getBrand();
        TxtUtils.setTextView(tv_Desc,data.getSimple_desc());
        TxtUtils.setTextView(tv_Text,data.getName());
        ImageLoaderUtils.loadImage(data.getApp_list_pic_url(),iv_Img);
    }

    @Override
    public void getBrandList(BrandNameDetailListBean result) {
        List<BrandNameDetailListBean.DataBeanX.DataBean> data = result.getData().getData();
        list.addAll(data);
        brandNameDetailAdapter.notifyDataSetChanged();
    }
}