package com.example.test.ui.shop.me.address;

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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.adapter.shop.me.address.AddressCityAdpter;
import com.example.test.base.BaseActivity;
import com.example.test.base.BaseAdapter;
import com.example.test.model.bean.shop.me.address.AddressCityBean;
import com.example.test.presenter.shop.me.address.AddressCityPresenter;
import com.example.test.view.shop.me.address.IAddress;

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

    int id=1;
    private PopupWindow popupWindow;
    private AddressCityAdpter addressCityAdpter;
    private RecyclerView mRlv;
    private ArrayList<AddressCityBean.DataBean> list;

    @Override
    protected int getLayout() {
        return R.layout.activity_address__add;
    }

    @Override
    protected void initView() {
        list = new ArrayList<>();

        String name = et_Name.getText().toString();//姓名
        String Phone = et_Phone.getText().toString();//手机号
        String Detail = et_Detail.getText().toString();//详细地址
    }

    @OnClick({R.id.mRb_address_add_moren, R.id.btn_address_add_cancel, R.id.btn_address_add_ok,R.id.et_address_add_city})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_address_add_city:
                addcity();//弹出来弹窗

                //点击弹出
                popupWindow.showAtLocation(et_City, Gravity.BOTTOM, 0, 70);

                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.5f;
                getWindow().setAttributes(lp);
                break;
                case R.id.mRb_address_add_moren:

                break;
            case R.id.btn_address_add_cancel:
                finishAndRemoveTask();//关闭页面
                break;
            case R.id.btn_address_add_ok:

                break;
        }
    }

    private void addcity() {
        View view = LayoutInflater.from(Address_AddActivity.this).inflate(R.layout.layout_address_add_city_popu, null);
        popupWindow = new PopupWindow(view, GridLayout.LayoutParams.MATCH_PARENT, 300);

        //控制点击pw范围以外的空间关闭pw  设置Pw以外的空间可以点击
        popupWindow.setOutsideTouchable(true);
        //设置背景  告知pw的范围
        popupWindow.setBackgroundDrawable(null);
        //pw可以输入,设置可以获得焦点
        //popupWindow.setFocusable(true);

        //得到pw中的组件
        TextView province =view.findViewById(R.id.tv_address_province);//省市
        TextView city =view.findViewById(R.id.tv_address_city);//省市
        TextView county =view.findViewById(R.id.tv_address_county);//省市
        //省市
        mRlv = view.findViewById(R.id.mRlv_address_city);

        mRlv.setLayoutManager(new LinearLayoutManager(this));
        addressCityAdpter = new AddressCityAdpter(this,list);

        addressCityAdpter.addListClick(new BaseAdapter.IListClick() {
            @Override
            public void itemClick(int pos) {
                int mid = list.get(pos).getId();
                id=mid;
                province.setText(list.get(pos).getName());
                persenter.getAddressCity(id);
            }
        });

        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                persenter.getAddressCity(id);
            }
        });


        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp=getWindow().getAttributes();//得到屏幕参数
                lp.alpha=1f;//设置背景屏幕不透明
                getWindow().setAttributes(lp);//重新设置背景
            }
        });


    }

    @Override
    protected IAddress.Presenter createPersenter() {
        return new AddressCityPresenter(this);
    }

    @Override
    protected void initData() {
        persenter.getAddressCity(id);
    }

    @Override
    public void getAddressCityReturn(AddressCityBean result) {
        List<AddressCityBean.DataBean> data = result.getData();
        list.addAll(data);
        addressCityAdpter.notifyDataSetChanged();
    }

}