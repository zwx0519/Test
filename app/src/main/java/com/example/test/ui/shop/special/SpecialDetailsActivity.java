package com.example.test.ui.shop.special;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.adapter.shop.special.SpecialDetailsAdapter;
import com.example.test.adapter.shop.special.SpecialDetailsButtomAdapter;
import com.example.test.adapter.shop.special.SpecialDetailsCommentAdapter;
import com.example.test.app.MyApp;
import com.example.test.base.BaseActivity;
import com.example.test.base.BaseAdapter;
import com.example.test.model.bean.shop.special.SpecialDetailsBean;
import com.example.test.model.bean.shop.special.SpecialDetailsButtomBean;
import com.example.test.model.bean.shop.special.SpecialDetailsCommentBean;
import com.example.test.presenter.shop.special.SpecialDetailsPresenter;
import com.example.test.utils.ToastUtils;
import com.example.test.view.shop.special.ISpecialDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpecialDetailsActivity extends BaseActivity<ISpecialDetails.Presenter> implements ISpecialDetails.View {

    @BindView(R.id.mRlv_special_details_img)
    RecyclerView mRlv_Img;
    @BindView(R.id.iv_special_details_leave_img)
    ImageView iv_Leave_Img;
    @BindView(R.id.mCl_special_details)
    ConstraintLayout mCl_Details;
    @BindView(R.id.mRlv_special_details_leave)
    RecyclerView mRlv_Leave;
    @BindView(R.id.btn_special_details_move)
    Button btn_Move;
    @BindView(R.id.mRlv_special_details_list)
    RecyclerView mRlv_List;

    private SpecialDetailsAdapter specialDetailsAdapter;
    private int id;

    @Override
    protected int getLayout() {
        return R.layout.activity_special_details;
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
        persenter = new SpecialDetailsPresenter(this);

        id = (int) MyApp.getMap().get("specialId");
        persenter.getSpecialDetails(id);
        persenter.getSpecialDetailsButtom(id);
        persenter.getSpecialDetailsComment(getMap());

    }

    private HashMap<String, String> getMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("valueId",String.valueOf(id));
        map.put("typeId",String.valueOf(1));
        map.put("size",String.valueOf(5));
        return map;
    }

    //TODO  专题详情数据
    @Override
    public void getSpecialDetailsReturn(SpecialDetailsBean result) {
        SpecialDetailsBean.DataBean data = result.getData();
        initgetImage(data.getContent());//H5分割图片
    }

    //TODO 专题详情页评论评论数据
    @Override
    public void getSpecialDetailsCommentReturn(SpecialDetailsCommentBean result) {
        List<SpecialDetailsCommentBean.DataBeanX.DataBean> data = result.getData().getData();

        if(result.getData().getData()!=null  && result.getData().getData().size()> 0){
            mRlv_Leave.setLayoutManager(new LinearLayoutManager(this));
            mRlv_Leave.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
            SpecialDetailsCommentAdapter specialDetailsCommentAdapter = new SpecialDetailsCommentAdapter(this, data);
            mRlv_Leave.setAdapter(specialDetailsCommentAdapter);
            specialDetailsCommentAdapter.notifyDataSetChanged();

            btn_Move.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SpecialDetailsActivity.this, SpecialCommentActivity.class);
                    startActivity(intent);
                }
            });
        }else {
            btn_Move.setVisibility(View.GONE);
        }


    }

    //TODO H5分割图片
    private void initgetImage(String content) {
        String img = "<img[\\s\\S]*?>";
        Pattern pattern = Pattern.compile(img);
        Matcher matcher = pattern.matcher(content);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            String word = matcher.group();
            int start = word.indexOf("\"") + 1;
            int end = word.indexOf(".jpg");
            //判断图片的格式
            if (end > 0) {
                String url = word.substring(start, end);
                if (url != null) {
                    url = "http:" + url + ".jpg";
                    list.add(url);
                } else {
                    return;
                }
            } else {
                int end1 = word.indexOf(".png");
                String url = word.substring(start, end1);
                if (url != null) {
                    url = url + ".png";
                    list.add(url);
                } else {
                    return;
                }
            }
        }
        mRlv_Img.setLayoutManager(new LinearLayoutManager(this));
        specialDetailsAdapter = new SpecialDetailsAdapter(this, list);
        mRlv_Img.setAdapter(specialDetailsAdapter);
        specialDetailsAdapter.notifyDataSetChanged();

    }

    //TODO 专题底部列表
    @Override
    public void getSpecialDetailsButtomReturn(SpecialDetailsButtomBean result) {
        List<SpecialDetailsButtomBean.DataBean> data = result.getData();

        mRlv_List.setLayoutManager(new LinearLayoutManager(this));
        SpecialDetailsButtomAdapter specialDetailsButtomAdapter = new SpecialDetailsButtomAdapter(this, data);
        mRlv_List.setAdapter(specialDetailsButtomAdapter);
        specialDetailsButtomAdapter.notifyDataSetChanged();

        specialDetailsButtomAdapter.addListClick(new BaseAdapter.IListClick() {
            @Override
            public void itemClick(int pos) {
                int id = data.get(pos).getId();
                Intent intent = new Intent(SpecialDetailsActivity.this,SpecialDetailsActivity.class);
                persenter.getSpecialDetails(id);
                persenter.getSpecialDetailsButtom(id);
                HashMap<String, String> map = new HashMap<>();
                map.put("valueId",String.valueOf(id));
                map.put("typeId",String.valueOf(1));
                map.put("size",String.valueOf(5));
                persenter.getSpecialDetailsComment(map);
                startActivity(intent);
            }
        });
    }

}