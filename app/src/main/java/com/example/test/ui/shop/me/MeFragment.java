package com.example.test.ui.shop.me;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.test.R;
import com.example.test.base.BaseFragment;
import com.example.test.base.IBasePersenter;
import com.example.test.ui.shop.login.LoginActivity;
import com.example.test.utils.ActivityManagerUtils;
import com.example.test.utils.SpUtils;
import com.example.test.utils.ToastUtils;

import butterknife.BindView;

//TODO 我的展示
public class MeFragment extends BaseFragment {
    @BindView(R.id.tv_my_login)
    TextView tv_Login;

    @Override
    protected int getLayout() {
        return R.layout.fragment_me;
    }


    @Override
    protected IBasePersenter createPrenter() {
        return null;
    }

    @Override
    protected void initView() {
        tv_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(SpUtils.getInstance().getString("token"))){
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivityForResult(intent,100);

                    //ActivityManagerUtils.startActivity(getActivity(), LoginActivity.class);
                }else{
                    ToastUtils.s(mContext,getString(R.string.tips_ok_login));
                }
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == 100){
            String name = data.getStringExtra("name");//登录之后显示名字
            tv_Login.setText(name);
        }
    }

    @Override
    protected void initData() {

    }
}
