package com.example.test.ui.shop.special;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.adapter.shop.special.SpecialDetailsCommentAdapter;
import com.example.test.app.MyApp;
import com.example.test.base.BaseActivity;
import com.example.test.model.bean.shop.special.SpecialDetailsBean;
import com.example.test.model.bean.shop.special.SpecialDetailsButtomBean;
import com.example.test.model.bean.shop.special.SpecialDetailsCommentBean;
import com.example.test.presenter.shop.special.SpecialDetailsPresenter;
import com.example.test.view.shop.special.ISpecialDetails;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SpecialCommentActivity extends BaseActivity<ISpecialDetails.Presenter> implements ISpecialDetails.View {

    @BindView(R.id.mRlv_special_comment)
    RecyclerView mRlv;
    private int id;

    @Override
    protected int getLayout() {
        return R.layout.activity_special_comment;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected ISpecialDetails.Presenter createPersenter() {
        return new SpecialDetailsPresenter(this);
    }

    @Override
    protected void initData() {
        id = (int) MyApp.getMap().get("specialId");

        persenter.getSpecialDetailsComment(getMap());
    }

    private HashMap<String, String> getMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("valueId",String.valueOf(id));
        map.put("typeId",String.valueOf(1));
        map.put("size","");
        return map;
    }

    @Override
    public void getSpecialDetailsReturn(SpecialDetailsBean result) {

    }

    @Override
    public void getSpecialDetailsCommentReturn(SpecialDetailsCommentBean result) {
        List<SpecialDetailsCommentBean.DataBeanX.DataBean> data = result.getData().getData();
        mRlv.setLayoutManager(new LinearLayoutManager(this));
        SpecialDetailsCommentAdapter specialDetailsCommentAdapter = new SpecialDetailsCommentAdapter(this, data);
        mRlv.setAdapter(specialDetailsCommentAdapter);
        specialDetailsCommentAdapter.notifyDataSetChanged();
    }

    @Override
    public void getSpecialDetailsButtomReturn(SpecialDetailsButtomBean result) {

    }
}