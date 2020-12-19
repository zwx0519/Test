package com.example.test.ui.shop.special;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.adapter.shop.special.SpecialAdapter;
import com.example.test.base.BaseFragment;
import com.example.test.model.bean.shop.special.SpecialBean;
import com.example.test.presenter.shop.special.SpecialPresenter;
import com.example.test.utils.ItemDecoration;
import com.example.test.view.shop.special.ISpecial;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
//TODO 专题内容
public class SpecialFragment extends BaseFragment<ISpecial.Presenter> implements ISpecial.View {
    @BindView(R.id.mRlv_special)
    RecyclerView mRlv;
    @BindView(R.id.btn_previous_page)
    Button btn_Previous_Page;
    @BindView(R.id.btn_next_page)
    Button btn_Next_Page;
    private ArrayList<SpecialBean.DataBeanX.DataBean> list;
    private SpecialAdapter specialAdapter;
    private int page=1;

    @Override
    protected int getLayout() {
        return R.layout.fragment_special;
    }

    @Override
    protected ISpecial.Presenter createPrenter() {
        return new SpecialPresenter(this);
    }

    @Override
    protected void initView() {
        btn_Previous_Page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

        mRlv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRlv.addItemDecoration(new ItemDecoration(getContext()));
        list = new ArrayList<>();
        specialAdapter = new SpecialAdapter(mContext, list);
        mRlv.setAdapter(specialAdapter);
    }

    @Override
    protected void initData() {
        presenter.getSpecial(page);
    }

    @Override
    public void getSpecialReturn(SpecialBean result) {
        List<SpecialBean.DataBeanX.DataBean> data = result.getData().getData();
        list.addAll(data);
        specialAdapter.notifyDataSetChanged();
    }
}
