package com.example.test.ui.shop.me.address;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressActivity extends AppCompatActivity {

    @BindView(R.id.mRlv_Address)
    RecyclerView mRlvAddress;
    @BindView(R.id.btn_address_add)
    Button btnAddressAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_address_add)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.btn_address_add:

                break;
        }
    }
}