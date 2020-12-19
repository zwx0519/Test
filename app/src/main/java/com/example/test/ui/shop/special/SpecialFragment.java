package com.example.test.ui.shop.special;

import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
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
import butterknife.OnClick;

//TODO 专题内容
public class SpecialFragment extends BaseFragment<ISpecial.Presenter> implements ISpecial.View {
    @BindView(R.id.mRlv_special)
    RecyclerView mRlv;
    @BindView(R.id.iv_special_all)
    ImageView iv_All;
    @BindView(R.id.tv_special_oading)
    TextView tv_lOading;
    @BindView(R.id.btn_previous_page)
    RadioButton btn_PreviousPage;
    @BindView(R.id.btn_next_page)
    RadioButton btn_NextPage;
    @BindView(R.id.mNsv_special)
    NestedScrollView mNsv_Special;
    private ArrayList<SpecialBean.DataBeanX.DataBean> list;
    private SpecialAdapter specialAdapter;
    private int ONE = 1;
    private int TWO = 2;
    private int page = ONE;

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
        //清空集合
        list.clear();
        List<SpecialBean.DataBeanX.DataBean> data = result.getData().getData();
        list.addAll(data);
        specialAdapter.notifyDataSetChanged();

        //隐藏加载中...
        iv_All.setVisibility(View.GONE);
        tv_lOading.setVisibility(View.GONE);
    }

//    @OnClick({R.id.btn_previous_page, R.id.btn_next_page})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.btn_previous_page:
//                //更换page页 默认第一页
//                page = ONE;
//                //显示加载中...      白版
//                iv_All.setVisibility(View.VISIBLE);
//                tv_lOading.setVisibility(View.VISIBLE);
//
//                //请求数据
//                presenter.getSpecial(page);
//
//                // 返回顶部 自动去最上面
//                mNsv_Special.fullScroll(ScrollView.FOCUS_UP);
//                break;
//            case R.id.btn_next_page:
//                //更换page页
//                page = TWO;
//
//                //显示加载中...  白板
//                iv_All.setVisibility(View.VISIBLE);
//                tv_lOading.setVisibility(View.VISIBLE);
//                //请求数据
//                presenter.getSpecial(page);
//                // 返回顶部
//                mNsv_Special.fullScroll(ScrollView.FOCUS_UP);
//                break;
//        }
//    }
}
