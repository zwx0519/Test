package com.example.test.ui.shop.shoppingcart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.adapter.shop.shoppingcar.ShoppingAdapter;
import com.example.test.app.MyApp;
import com.example.test.base.BaseAdapter;
import com.example.test.base.BaseFragment;
import com.example.test.model.bean.shop.shoppingcar.AddShoppingCarBean;
import com.example.test.model.bean.shop.shoppingcar.DeleteShoppingCarBean;
import com.example.test.model.bean.shop.shoppingcar.ShoppingCarBean;
import com.example.test.model.bean.shop.shoppingcar.UpdateShoppingCarBean;
import com.example.test.presenter.shop.shoppingcar.ShoppingCarPresenter;
import com.example.test.ui.shop.login.LoginActivity;
import com.example.test.utils.ActivityManagerUtils;
import com.example.test.utils.SpUtils;
import com.example.test.view.shop.shoppingcar.IShoppingCar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private ArrayList<ShoppingCarBean.DataBean.CartListBean> list1;

    @Override
    protected int getLayout() {
        return R.layout.fragment_shopping_car;
    }

    @Override
    protected IShoppingCar.Presenter createPrenter() {
        return new ShoppingCarPresenter(this);
    }

    //懒加载
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            list.clear();
            initData();
            shoppingAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initView() {
        tv_Edit.setOnClickListener(this);
        tv_Submit.setOnClickListener(this);
        cb_All.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        initShopping();
        String token = SpUtils.getInstance().getString("token");
        if (!TextUtils.isEmpty(token)) {
            presenter.getShoppingCar();//展示列表

            //取出数值
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("shu");
            mContext.registerReceiver(receiver, intentFilter);//广播
        } else {
            ActivityManagerUtils.startFragmentForResult(this, 100, LoginActivity.class);
        }
    }

    //TODO 初始化布局
    private void initShopping() {
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        mRlv_ShoppingCar.setLayoutManager(new LinearLayoutManager(getActivity()));
        shoppingAdapter = new ShoppingAdapter(getContext(), list);
        mRlv_ShoppingCar.setAdapter(shoppingAdapter);

        //监听条目元素点击的时候的接口回调
        shoppingAdapter.addItemViewClick(new BaseAdapter.IItemViewClick() {
            @Override
            public void itemViewClick(int viewid, Object data) {
                for (ShoppingCarBean.DataBean.CartListBean item : shoppingCarBean.getData().getCartList()) {
                    if (item.getId() == viewid) {
                        if (!isEdit) {
                            item.selectOrder = (boolean) data;
                        } else {
                            item.selectEdit = (boolean) data;
                        }
                        break;
                    }
                }
                boolean isSelectAll;

                if (!isEdit) {
                    isSelectAll = totalSelectOrder();
                } else {
                    isSelectAll = totalSelectEdit();
                }
                cb_All.setChecked(isSelectAll);
            }
        });

        // 监听编辑状态下item的数据变化 点击条目
        shoppingAdapter.setUpdateItem(new ShoppingAdapter.UpdateItem() {
            @Override
            public void updateItemDate(ShoppingCarBean.DataBean.CartListBean data) {
                Map<String, String> map = new HashMap<>();
                map.put("goodsId", String.valueOf(data.getGoods_id()));
                map.put("productId", String.valueOf(data.getProduct_id()));
                map.put("id", String.valueOf(data.getId()));
                map.put("number", String.valueOf(data.getNumber()));

                //调用修改条目的方法
                presenter.postUpdateShoppingCar(map);
                //编辑状态下的总数和价格的计算
                totalSelectEdit();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.getShoppingCar();
    }

    //TODO 广播添加进入购物车
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //goodsId
            int goodsId = intent.getIntExtra("goodsId", 0);
            //number
            String number = intent.getStringExtra("number");
            //productId
            int productId = intent.getIntExtra("productId", 0);

            Log.e("TAG", "onReceive:goodsId " + goodsId);
            Log.e("TAG", "onReceive:number " + number);
            Log.e("TAG", "onReceive:productId " + productId);
            //请求数据
            if (goodsId > 0 && number != null && productId > 0) {   //不为空
                HashMap<String, String> map = new HashMap<>();
                map.put("goodsId", String.valueOf(goodsId));
                map.put("number", number);
                map.put("productId", String.valueOf(productId));

                presenter.AddShoppingCar(map);//添加购物车

                //刷新适配器
                shoppingAdapter.notifyDataSetChanged();
            } else {  //为空
                Log.e("TAG", "onReceive: 无");
            }
        }
    };

    @Override
    public void getShoppingCarReturn(ShoppingCarBean shoppingCarBean) {
        this.shoppingCarBean = shoppingCarBean;
        list.clear();//清空一下集合
        list.addAll(shoppingCarBean.getData().getCartList());
        shoppingAdapter.notifyDataSetChanged();
    }

    //TODO 修改购物车的返回
    @Override
    public void postUpdateShoppingCarReturn(UpdateShoppingCarBean updateShoppingCarBean) {
        Log.i("TAG", updateShoppingCarBean.toString());

        for (UpdateShoppingCarBean.DataBean.CartListBean item : updateShoppingCarBean.getData().getCartList()) {
            updateCartListBeanNumberById(item.getId(), item.getNumber());
        }

        //更新商品的总数和总价
        shoppingCarBean.getData().getCartTotal().setGoodsCount(updateShoppingCarBean.getData().getCartTotal().getGoodsCount());
        shoppingCarBean.getData().getCartTotal().setGoodsAmount(updateShoppingCarBean.getData().getCartTotal().getGoodsAmount());
        shoppingAdapter.notifyDataSetChanged();
        totalSelectEdit();
    }

    //TODO 刷新购物车列表的数据
    private void updateCartListBeanNumberById(int carId, int number) {
        for (ShoppingCarBean.DataBean.CartListBean item : list) {
            //找到修改的ID和集合中的ID
            if (item.getId() == carId) {
                //修改数量
                item.setNumber(number);
                break;
            }
        }
    }

    //TODO 删除购物车数据
    @Override
    public void postDeleteShoppingCarReturn(DeleteShoppingCarBean deleteShoppingCarBean) {
        Log.i("TAG", "deleteCar:" + deleteShoppingCarBean.toString());
        //通过购物车返回的最新数据，同步本地列表中的数据
        int index;
        int lg = list.size();

        //通过index向集合长度判断
        for (index = 0; index < lg; index++) {
            ShoppingCarBean.DataBean.CartListBean item = list.get(index);
            boolean bool = deleteCarListById(deleteShoppingCarBean.getData().getCartList(), item.getId());
            Log.i("TAG", "delete bool:" + bool + " item:" + item.getId());
            if (bool) {
                list.remove(index);//删除集合
                index--;//对应的下标减1
                lg--;//集合长度减-1
            }
        }
        shoppingAdapter.notifyDataSetChanged();
        totalSelectEdit();//编辑状态下的总数和价格的计算
    }

    //TODO 判断当前的本地列表的购物车列表数据是否在返回的最新列表中存在
    private boolean deleteCarListById(List<DeleteShoppingCarBean.DataBean.CartListBean> list, int carId) {
        for (DeleteShoppingCarBean.DataBean.CartListBean item : list) {
            if (item.getId() == carId) {//如果有 删除失败
                return false;
            }
        }
        return true;
    }

    //TODO 添加购物车
    @Override
    public void postAddShoppingCarReturn(AddShoppingCarBean rsult) {

    }

    //TODO 点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_Shopping_Car_edit://点击编辑
                changeEdit();
                break;

            case R.id.tv_Shopping_Car_submit://点击下单
                submit();
                break;

            case R.id.cb_Shopping_car_all:
                Log.i("TAG", "checkboxall");
                boolean bool = cb_All.isChecked();
                if (isEdit) {
                    updateGoodSelectStateEdit(!bool);
                } else {
                    updateGoodSelectStateOrder(!bool);
                }
                break;
        }
    }


    //TODO 下单状态的数据刷新
    private void updateGoodSelectStateOrder(boolean isChecked) {
        for (ShoppingCarBean.DataBean.CartListBean item : shoppingCarBean.getData().getCartList()) {
            item.selectOrder = isChecked;
        }
        totalSelectOrder();
        // 更新列表条目的选中状态
        shoppingAdapter.notifyDataSetChanged();
    }

    //TODO 编辑状态下的数据刷新
    private void updateGoodSelectStateEdit(boolean isChecked) {
        for (ShoppingCarBean.DataBean.CartListBean item : shoppingCarBean.getData().getCartList()) {
            item.selectEdit = isChecked;
        }
        totalSelectOrder();
        // 更新列表条目的选中状态
        shoppingAdapter.notifyDataSetChanged();
    }

    //TODO 下单状态下的总数和价格的计算
    private boolean totalSelectOrder() {
        int num = 0;
        int totalPrice = 0;
        boolean isSelectAll = true;
        for (ShoppingCarBean.DataBean.CartListBean item : list) {
            if (item.selectOrder) {
                num += item.getNumber();
                totalPrice += item.getNumber() * item.getRetail_price();
            } else {
                if (isSelectAll) {
                    isSelectAll = false;
                }
            }
        }
        String strAll = "全选($)";
        strAll = strAll.replace("$", String.valueOf(num));
        cb_All.setText(strAll);
        tv_Price.setText("￥" + totalPrice);

        return isSelectAll;

    }

    //TODO 编辑状态下的总数和价格的计算
    private boolean totalSelectEdit() {
        int num = 0;
        //int totalPrice = 0;
        boolean isSelectAll = true;//全选
        for (ShoppingCarBean.DataBean.CartListBean item : list) {
            if (item.selectEdit) {//选中的数量
                num += item.getNumber();//数量
                //totalPrice += item.getNumber() * item.getRetail_price();//总价
            } else {
                if (isSelectAll) {//全选为false
                    isSelectAll = false;
                }
            }
        }
        String strAll = "全选($)";
        strAll = strAll.replace("$", String.valueOf(num));
        cb_All.setText(strAll);
        // tv_Price.setText("￥" + totalPrice);

        return isSelectAll;
    }

    //TODO 修改编辑和完成的状态
    private void changeEdit() {
        if ("编辑".equals(tv_Edit.getText().toString())) {
            tv_Edit.setText("完成");
            tv_Submit.setText("删除所选");

            isEdit = true;//是编辑状态
            //价格进行隐藏
            tv_Price.setVisibility(View.GONE);
            //updateGoodSelectStateOrder(false);
        } else if ("完成".equals(tv_Edit.getText().toString())) {
            tv_Edit.setText("编辑");
            tv_Submit.setText("下单");

            isEdit = false;//不是编辑状态
            updateGoodSelectStateEdit(false);
            //价格进行显示
            tv_Price.setVisibility(View.VISIBLE);
        }
        shoppingAdapter.setEditState(isEdit);//删除
        shoppingAdapter.notifyDataSetChanged();
    }

    //TODO 下单 提交
    private void submit() {
        list1.clear();//每次下单前清空一下集合
        if ("下单".equals(tv_Submit.getText().toString())) {
            for (ShoppingCarBean.DataBean.CartListBean item : list) {
                if (item.selectOrder) {//在下单的状态勾选
                    list1.add(item);
                }
            }
            //下单
            Intent intent = new Intent(getActivity(), OrderFormActivity.class);
            MyApp.getMap().put("shoppinglist", list1);
            startActivity(intent);
        } else if ("删除所选".equals(tv_Submit.getText().toString())) {
            //删除购物车所选数据
            deleteCar();
        }
    }

    //TODO 删除所有选中的商品数据
    private void deleteCar() {
        StringBuilder sb = new StringBuilder();
        for (ShoppingCarBean.DataBean.CartListBean item : list) {
            if (item.selectEdit) {
                sb.append(item.getProduct_id());
                sb.append(",");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);//删除所选的数据
        }
        Log.i("TAG", sb.toString());
        presenter.postDeleteShoppingCar(sb.toString());
        shoppingAdapter.notifyDataSetChanged();
    }
}
