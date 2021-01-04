package com.example.test.ui.shop.me.address;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.adapter.shop.me.address.AddressAddAdapter;
import com.example.test.app.MyApp;
import com.example.test.base.BaseActivity;
import com.example.test.base.IBasePersenter;
import com.example.test.db.DaoSession;
import com.example.test.model.bean.shop.me.address.AddressBean;
import com.example.test.model.bean.shop.me.address.AddressDeteleBean;
import com.example.test.model.bean.shop.me.address.AddressListBean;
import com.example.test.model.bean.shop.me.address.User;
import com.example.test.presenter.shop.me.address.AddressPresenter;
import com.example.test.view.shop.me.address.IAddAddress;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddressActivity extends BaseActivity<IAddAddress.Presenter> implements IAddAddress.View {

    @BindView(R.id.mRlv_Address)
    RecyclerView mRlv;
    @BindView(R.id.btn_address_add)
    Button btn_Add;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        bind = ButterKnife.bind(this);
        initView();
    }

    @Override
    protected int getLayout() {
        return 0;
    }

    protected void initView() {
        DaoSession daoSession = MyApp.getDaoSession();
        List<User> users = daoSession.getUserDao().loadAll();
        if (users != null) {
            mRlv.setLayoutManager(new LinearLayoutManager(this));
            mRlv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            AddressAddAdapter addressAddAdapter = new AddressAddAdapter(this, users);
            mRlv.setAdapter(addressAddAdapter);

            addressAddAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected IAddAddress.Presenter createPersenter() {
        return new AddressPresenter(this);
    }

    @Override
    protected void initData() {
        persenter.getAddressList();//收货列表
        persenter.postAddressDelete(String.valueOf(1));//删除地址
    }

    //收货列表
    @Override
    public void getAddressListReturn(AddressListBean result) {

    }

    //TODO 删除地址
    @Override
    public void postAddressDeleteReturn(AddressDeteleBean result) {

    }

    @OnClick(R.id.btn_address_add)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_address_add:
                Intent intent = new Intent(AddressActivity.this, Address_AddActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }

}