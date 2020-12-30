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
import com.example.test.db.DaoSession;
import com.example.test.model.bean.shop.me.address.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AddressActivity extends AppCompatActivity {

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

    private void initView() {
        DaoSession daoSession = MyApp.getDaoSession();
        List<User> users = daoSession.getUserDao().loadAll();
        if(users!=null){
            mRlv.setLayoutManager(new LinearLayoutManager(this));
            mRlv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
            AddressAddAdapter addressAddAdapter = new AddressAddAdapter(this, users);
            mRlv.setAdapter(addressAddAdapter);

            addressAddAdapter.notifyDataSetChanged();
        }
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