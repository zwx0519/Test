package com.example.test.ui.shop.home.brand;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.adapter.shop.home.brand.BrandNameAdapter;
import com.example.test.app.MyApp;
import com.example.test.base.BaseAdapter;
import com.example.test.model.bean.shop.home.brand.BrandNameBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 品牌制造商列表展示
 */
public class BrandNameActivity extends AppCompatActivity {

    @BindView(R.id.mRlv_Brand_name)
    RecyclerView mRlv;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_name);
        bind = ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        List<BrandNameBean.DataBeanX.DataBean> list = (List<BrandNameBean.DataBeanX.DataBean>) MyApp.getMap().get("bean");
        mRlv.setLayoutManager(new LinearLayoutManager(this));
        //自定义分割线    白色
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.shape_rlv_line));
        mRlv.addItemDecoration(dividerItemDecoration);

        BrandNameAdapter brandAdapter = new BrandNameAdapter(this, list);
        mRlv.setAdapter(brandAdapter);

        brandAdapter.addListClick(new BaseAdapter.IListClick() {
            @Override
            public void itemClick(int pos) {
                Intent intent = new Intent(BrandNameActivity.this, BrandNameDetailActivity.class);
                int id = list.get(pos).getId();
                intent.putExtra("id",String.valueOf(id));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}