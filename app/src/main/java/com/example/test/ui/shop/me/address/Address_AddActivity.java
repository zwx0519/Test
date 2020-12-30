package com.example.test.ui.shop.me.address;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.app.MyApp;
import com.example.test.base.BaseActivity;
import com.example.test.base.BaseAdapter;
import com.example.test.db.DaoSession;
import com.example.test.model.bean.shop.me.address.AddressCityBean;
import com.example.test.model.bean.shop.me.address.User;
import com.example.test.presenter.shop.me.address.AddressCityPresenter;
import com.example.test.utils.ToastUtils;
import com.example.test.view.shop.me.address.IAddress;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class Address_AddActivity extends BaseActivity<IAddress.Presenter> implements IAddress.View {

    @BindView(R.id.et_address_add_name)
    EditText et_Name;
    @BindView(R.id.et_address_add_phone)
    EditText et_Phone;
    @BindView(R.id.et_address_add_city)
    EditText et_City;
    @BindView(R.id.et_address_add_detail)
    EditText et_Detail;
    @BindView(R.id.mRb_address_add_moren)
    RadioButton mRb_Moren;
    @BindView(R.id.btn_address_add_cancel)
    Button btn_Cancel;
    @BindView(R.id.btn_address_add_ok)
    Button btn_Ok;
    @BindView(R.id.mCl_addreess)
    ConstraintLayout mCl_addreess;

    int id = 1;//第一个Id开始

    private PopupWindow popupWindow;
    private AddressCityAdpter addressCityAdpter;
    private RecyclerView mRlv;
    private ArrayList<AddressCityBean.DataBean> list;

    private int defaultTabCount = 3; //tab 的数量
    private String defaultProvince = "省份"; //显示在上面tab中的省份
    private String defaultCity = "城市"; //显示在上面tab中的城市
    private String defaultCounty = "区县"; //显示在上面tab中的区县

    // recyclerView 选中Item 的颜色
    private int defaultSelectedColor = Color.parseColor("#AB2B2B");
    // recyclerView 未选中Item 的颜色
    private int defaultUnSelectedColor = Color.parseColor("#262626");
    // 确定字体不可以点击时候的颜色
    private int defaultSureUnClickColor = Color.parseColor("#7F7F7F");
    // 确定字体可以点击时候的颜色
    private int defaultSureCanClickColor = Color.parseColor("#AB2B2B");

    private AddressCityBean addressAddProvinceBean;  // 总数据
    private AddressCityBean.DataBean mSelectProvice; //选中 省份 bean
    private AddressCityBean.DataBean mSelectCity;//选中 城市  bean
    private AddressCityBean.DataBean mSelectCounty;//选中 区县  bean

    private int mSelectProvicePosition = 0; //选中 省份 位置
    private int mSelectCityPosition = 0;//选中 城市  位置
    private int mSelectCountyPosition = 0;//选中 区县  位置
    private TabLayout mTab; // tabLayout
    private TextView sure;//Tab页里面的确定
    private DaoSession daoSefssion;

    @Override
    protected int getLayout() {
        return R.layout.activity_address_add;
    }

    @Override
    protected void initView() {
        et_City.setFocusable(false);//让EditText失去焦点，然后获取点击事件
        daoSefssion = MyApp.getDaoSession();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.mRb_address_add_moren, R.id.btn_address_add_cancel, R.id.btn_address_add_ok, R.id.et_address_add_city})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_address_add_city://点击地址
                addcity();//弹出来弹窗
                //点击弹出
                break;
            case R.id.mRb_address_add_moren://默认

                break;
            case R.id.btn_address_add_cancel://取消
                finishAndRemoveTask();//关闭页面
                break;
            case R.id.btn_address_add_ok://保存
                initsave();//进行保存
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initsave() {
        String name = et_Name.getText().toString();//姓名
        String Phone = et_Phone.getText().toString();//手机号
        String Detail = et_Detail.getText().toString();//详细地址
        String City= et_City.getText().toString();//省市

        //GreenDao插入数据
        User user = new User();
        user.setMName(name);
        user.setMPhone(Phone);
        user.setMCity(City);
        user.setMDetail(Detail);
        mRb_Moren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRb_Moren.isChecked()){
                    user.setABoolean(true);
                }else {
                    user.setABoolean(false);
                }
            }
        });

        long insert = daoSefssion.getUserDao().insert(user);
        if(insert>0){
            Log.e("TAG","添加成功");
            finishAndRemoveTask();
        }else {
            Log.e("TAG","添加失败");
        }

    }

    private void addcity() {
        View view = LayoutInflater.from(Address_AddActivity.this).inflate(R.layout.layout_address_add_city_popu, null);
        popupWindow = new PopupWindow(view, GridLayout.LayoutParams.MATCH_PARENT, 300);
        persenter.getAddressCity(id);
        list = new ArrayList<>();

        initTab(view);

        //省市
        mRlv =(RecyclerView) view.findViewById(R.id.mRlv_address_city);
        mRlv.setLayoutManager(new LinearLayoutManager(this));
        addressCityAdpter = new AddressCityAdpter(this, list);
        mRlv.setAdapter(addressCityAdpter);

        // 初始化默认的本地数据  也提供了方法接收外面数据
        mRlv.post(new Runnable() {
            @Override
            public void run() {
                persenter.getAddressCity(id);
            }
        });

//        addressCityAdpter.addListClick(new BaseAdapter.IListClick() {
//            @Override
//            public void itemClick(int pos) {
//                int mid = list.get(pos).getId();
//                id = mid;
//                province.setText(list.get(pos).getName());
//                persenter.getAddressCity(id);
//                city.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        persenter.getAddressCity(id);
//                    }
//                });
//            }
//        });
        //控制点击pw范围以外的空间关闭pw  设置Pw以外的空间可以点击
        popupWindow.setOutsideTouchable(true);
        //设置背景  告知pw的范围
        popupWindow.setBackgroundDrawable(null);
        //获取焦点
        popupWindow.setFocusable(true);

        popupWindow.showAtLocation(mCl_addreess, Gravity.BOTTOM, 0, 0);
        //设置阴影
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams attributes = getWindow().getAttributes();
                attributes.alpha = 1f;
                getWindow().setAttributes(attributes);
            }
        });
    }

    //TODO 弹窗中确定和Tab
    private void initTab(View view) {
        //确定
        sure = view.findViewById(R.id.tv_address_sure);
        sure.setTextColor(defaultSureUnClickColor);//确定不可以点击

        //点击确定的监听事件
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = v.getId();
                if (i == R.id.tv_address_sure) {
                    if (mSelectProvice != null && mSelectCity != null && mSelectCounty != null) {
                        //   回调接口
                        if (et_City != null) {
                            et_City.setText(mSelectProvice.getName() + " " + mSelectCity.getName() + " " + mSelectCounty.getName());
                            popupWindow.dismiss();//关闭弹窗
//                            WindowManager.LayoutParams lp = getWindow().getAttributes();
//                            lp.alpha = 1f;
//                            getWindow().setAttributes(lp);
                            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                                @Override
                                public void onDismiss() {
                                    WindowManager.LayoutParams lp = getWindow().getAttributes();//得到屏幕参数
                                    lp.alpha = 1f;//设置背景屏幕不透明
                                    getWindow().setAttributes(lp);//重新设置背景
                                }
                            });
                            et_City.setFocusable(true);//重新获得焦点
                        }
                    } else {
                        Toast.makeText(Address_AddActivity.this, "地址还没有选完整哦", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        // tablayout初始化
        mTab = (TabLayout) view.findViewById(R.id.mTab_address_city);

        //配置Tab页
        mTab.addTab(mTab.newTab().setText(defaultProvince));//区市
        mTab.addTab(mTab.newTab().setText(defaultCity));//城市
        mTab.addTab(mTab.newTab().setText(defaultCounty));//区县


        mTab.addOnTabSelectedListener(tabSelectedListener);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        list = null;
    }

    //TODO TabLayout 切换事件
    TabLayout.BaseOnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {//选中Tab页时
            list.clear();//先清空集合
            switch (tab.getPosition()) {
                case 0:
                    persenter.getAddressCity(id);
                    addressCityAdpter.notifyDataSetChanged();
                    // 滚动到这个位置
                    mRlv.smoothScrollToPosition(mSelectProvicePosition);
                    break;
                case 1:
                    // 点到城市的时候要判断有没有选择省份
                    if (mSelectProvice != null) {//如果省份不为空
                        for (AddressCityBean.DataBean itemBean : list) {
                            //进行判断是否一致
                            if (itemBean.getName().equals(mSelectProvice.getName()))
                                list.add(itemBean);
                        }
                    } else {
                        ToastUtils.s(Address_AddActivity.this,"请您先选择省份");
                    }
                    addressCityAdpter.notifyDataSetChanged();
                    // 滚动到这个位置
                    mRlv.smoothScrollToPosition(mSelectCityPosition);
                    break;
                case 2:
                    // 点到区的时候要判断有没有选择省份与城市
                    if (mSelectProvice != null && mSelectCity != null) {
                        for (AddressCityBean.DataBean itemBean : list) {
                            if (itemBean.getName().equals(mSelectCity.getName()))
                                list.add(itemBean);
                        }
                    } else {
                        ToastUtils.s(Address_AddActivity.this,"请您先选择省份与城市");
                    }
                    addressCityAdpter.notifyDataSetChanged();
                    // 滚动到这个位置
                    mRlv.smoothScrollToPosition(mSelectCountyPosition);
                    break;
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    @Override
    protected IAddress.Presenter createPersenter() {
        return new AddressCityPresenter(this);
    }

    @Override
    protected void initData() {
        persenter=new AddressCityPresenter(this);
    }

    //TODO 省市地址
    @Override
    public void getAddressCityReturn(AddressCityBean result) {
        List<AddressCityBean.DataBean> data = result.getData();
        list.addAll(data);
        addressCityAdpter.notifyDataSetChanged();
    }

    //TODO 内部视频器
    //内部适配器
    class AddressCityAdpter extends BaseAdapter {

        public AddressCityAdpter(Context context, List Data) {
            super(context, Data);
        }

        @Override
        protected int getLayout(int type) {
            return R.layout.layout_address_add_popu_item;
        }

        @Override
        protected void bindData(Object data, VH vh) {

            //获取Tab页的点击
            int tabSelectPosition = mTab.getSelectedTabPosition();
            AddressCityBean.DataBean bean= (AddressCityBean.DataBean) data;

            TextView province = (TextView) vh.getViewById(R.id.tv_address_item_province);
            province.setText(bean.getName());
            province.setTextColor(defaultUnSelectedColor);//未改变选中区市的颜色

            // 设置选中效果的颜色
            switch (tabSelectPosition) {
                case 0:
                    if (bean != null && mSelectProvice != null && bean.getName().equals(mSelectProvice.getName())) {
                        province.setTextColor(defaultSelectedColor);//改变选中区市的颜色
                    }
                    break;
                case 1:
                    if (bean != null && mSelectCity != null &&
                            bean.getName().equals(mSelectCity.getName())) {
                        province.setTextColor(defaultSelectedColor);//改变选中城市的颜色
                    }
                    break;
                case 2:
                    if (bean != null && mSelectCounty != null &&
                            bean.getName().equals(mSelectCounty.getName())) {
                        province.setTextColor(defaultSelectedColor);
                    }
                    break;
            }

            // 设置点击之后的事件
            province.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 点击 分类别
                    switch (tabSelectPosition) {
                        case 0:
                            mSelectProvice = bean;
                            // 清空后面两个的数据
                            mSelectCity = null;
                            mSelectCounty = null;
                            mSelectCityPosition = 0;
                            mSelectCountyPosition = 0;
                            // 获得Tab页的 并且配置Tab
                            mTab.getTabAt(1).setText(defaultCity);
                            mTab.getTabAt(2).setText(defaultCounty);
                            // 设置这个对应的标题
                            mTab.getTabAt(0).setText(mSelectProvice.getName());
                            // 跳到下一个选择
                            mTab.getTabAt(1).select();
                            // 灰掉确定按钮
                            sure.setTextColor(defaultSureUnClickColor);
                            persenter.getAddressCity(bean.getId());
                            mSelectProvicePosition = bean.getId();
                            break;
                        case 1:
                            mSelectCity = bean;
                            // 清空后面一个的数据
                            mSelectCounty = null;
                            mSelectCityPosition = 0;
                            mTab.getTabAt(2).setText(defaultCounty);
                            // 设置这个对应的标题
                            mTab.getTabAt(1).setText(mSelectCity.getName());
                            // 跳到下一个选择
                            mTab.getTabAt(2).select();
                            // 灰掉确定按钮
                            sure.setTextColor(defaultSureUnClickColor);
                            persenter.getAddressCity(bean.getId());
                            mSelectCityPosition = bean.getId();
                            break;
                        case 2:
                            mSelectCounty = bean;
                            // 没了，选完了，这个时候可以点确定了
                            mTab.getTabAt(2).setText(mSelectCounty.getName());
                            notifyDataSetChanged();
                            // 确定按钮变亮
                            sure.setTextColor(defaultSureCanClickColor);
                            persenter.getAddressCity(bean.getId());
                            mSelectCountyPosition = bean.getId();
                            break;
                    }
                }
            });

        }

    }
}