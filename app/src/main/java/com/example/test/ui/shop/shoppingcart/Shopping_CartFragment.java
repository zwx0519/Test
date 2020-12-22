package com.example.test.ui.shop.shoppingcart;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.adapter.shop.shoppingcar.ShoppingAdapter;
import com.example.test.base.BaseFragment;
import com.example.test.model.bean.shop.shoppingcar.ShoppingCarBean;
import com.example.test.presenter.shop.shoppingcar.ShoppingCarPresenter;
import com.example.test.ui.shop.login.LoginActivity;
import com.example.test.utils.ActivityManagerUtils;
import com.example.test.utils.SpUtils;
import com.example.test.view.shop.shoppingcar.IShoppingCar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//TODO 购物车的展示
public class Shopping_CartFragment extends BaseFragment<IShoppingCar.Presenter> implements IShoppingCar.View, View.OnClickListener {
    @BindView(R.id.mRlv_Shopping_Car)
    RecyclerView mRlv_ShoppingCar;//集合
    @BindView(R.id.cb_Shopping_car_all)
    CheckBox cb_All;//全选
    @BindView(R.id.tv_Shopping_Car_totalPrice)
    TextView tv_Price;//价格
    @BindView(R.id.tv_Shopping_Car_edit)
    TextView tv_Edit;//编辑
    @BindView(R.id.tv_Shopping_Car_submit)
    TextView tv_Submit;//下单
    private boolean isEdit; //是否是编辑状态
    private ShoppingCarBean shoppingCarBean;
    private ShoppingAdapter shoppingAdapter;
    private ArrayList<ShoppingCarBean.DataBean.CartListBean> list;

    @Override
    protected int getLayout() {
        return R.layout.fragment_shopping_car;
    }

    @Override
    protected IShoppingCar.Presenter createPrenter() {
        return new ShoppingCarPresenter(this);
    }

    @Override
    protected void initView() {
        initShopping();
        //获取列表

        cb_All.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isEdit){
                    updateGoodSelectStateEdit(isChecked);
                }else{
                    updateGoodSelectStateOrder(isChecked);
                }
            }
        });

        tv_Edit.setOnClickListener(this);
        tv_Submit.setOnClickListener(this);
        cb_All.setOnClickListener(this);
    }

    private void initShopping() {
        list=new ArrayList<>();
        mRlv_ShoppingCar.setLayoutManager(new LinearLayoutManager(getActivity()));
        shoppingAdapter = new ShoppingAdapter(getContext(),list);
        mRlv_ShoppingCar.setAdapter(shoppingAdapter);
    }


    @Override
    protected void initData() {
        String token = SpUtils.getInstance().getString("token");
        if(!TextUtils.isEmpty(token)){
            presenter.getShoppingCar();
        }else{
            ActivityManagerUtils.startFragmentForResult(this,100, LoginActivity.class);
        }
    }


    @Override
    public void getShoppingCarReturn(ShoppingCarBean shoppingCarBean) {
        this.shoppingCarBean=shoppingCarBean;
        List<ShoppingCarBean.DataBean.CartListBean> cartList = shoppingCarBean.getData().getCartList();
        list.addAll(cartList);
        shoppingAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_Shopping_Car_edit://点击编辑
                changeEdit();
                break;
            case R.id.tv_Shopping_Car_submit://点击下单
                submit();
                break;
            case R.id.cb_Shopping_car_all:
                if (cb_All.isSelected()) {
                    shoppingAdapter.setChecked(true);
                    shoppingAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.getShoppingCar();
    }

    //TODO 下单状态的数据刷新
    private void updateGoodSelectStateOrder(boolean isChecked) {
        for(ShoppingCarBean.DataBean.CartListBean item:shoppingCarBean.getData().getCartList()){
            item.selectOrder = isChecked;
        }
        totalSelectOrder();
    }

    //TODO 编辑状态下的数据刷新
    private void updateGoodSelectStateEdit(boolean isChecked) {
        for(ShoppingCarBean.DataBean.CartListBean item:shoppingCarBean.getData().getCartList()){
            item.selectEdit = isChecked;
        }
        totalSelectOrder();
    }

    //TODO 下单状态下的总数和价格的计算
    private void totalSelectOrder() {
        int num = 0;
        int totalPrice = 0;
        boolean isSelectAll = true;
        for(ShoppingCarBean.DataBean.CartListBean item:shoppingCarBean.getData().getCartList()){
            if(item.selectOrder){
                num += item.getNumber();
                totalPrice += item.getNumber()*item.getRetail_price();
            }else{
                if(isSelectAll){
                    isSelectAll = false;
                }
            }
        }
        String strAll = "全选($)";
        strAll = strAll.replace("$",String.valueOf(num));
        cb_All.setText(strAll);
        tv_Price.setText("￥"+totalPrice);
        cb_All.setChecked(isSelectAll);
    }

    //TODO 修改编辑和完成的状态
    private void changeEdit() {
        if("编辑".equals(tv_Edit.getText().toString())){
            tv_Edit.setText("完成");
            tv_Submit.setText("删除所选");

            shoppingAdapter.setEditState(true);//删除
            shoppingAdapter.notifyDataSetChanged();
        }else if("完成".equals(tv_Edit.getText().toString())){
            tv_Edit.setText("编辑");
            tv_Submit.setText("下单");

            shoppingAdapter.setEditState(false);//删除
            shoppingAdapter.notifyDataSetChanged();
        }
    }

    //TODO 下单 提交
    private void submit() {
        if("下单".equals(tv_Submit.getText().toString())){
            //下单
        }else if("删除所选".equals(tv_Submit.getText().toString())){
            //删除购物车所选数据
        }
    }
}
