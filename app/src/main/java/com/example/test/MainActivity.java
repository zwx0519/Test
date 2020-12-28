package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.ui.shop.JumpActivity;
import com.example.test.ui.shop.ShopActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_shop = (Button) findViewById(R.id.btn_shop);

        btn_shop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_shop:
                Intent intent = new Intent(this, ShopActivity.class);
                //Intent intent = new Intent(this, JumpActivity.class);
                startActivity(intent);
                break;
        }
    }
}