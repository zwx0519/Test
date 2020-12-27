package com.example.test.ui.shop.me;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.Nullable;

import com.example.test.R;
import com.example.test.base.BaseFragment;
import com.example.test.base.IBasePersenter;
import com.example.test.ui.shop.ShopActivity;
import com.example.test.ui.shop.login.LoginActivity;
import com.example.test.ui.shop.me.address.AddressActivity;
import com.example.test.ui.shop.me.collect.FavoritesActivity;
import com.example.test.utils.SpUtils;
import com.example.test.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

//TODO 我的展示
public class MeFragment extends BaseFragment {
    @BindView(R.id.tv_my_login)
    TextView tv_Login;

    @BindView(R.id.ll_my_address)
    LinearLayout ll_address;

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
//        ShopActivity shopActivity = new ShopActivity();
//        Toolbar toolbar=shopActivity.findViewById(R.id.toolbar);
//        toolbar.setVisibility(View.GONE);
    }

    @OnClick({R.id.tv_my_login, R.id.ll_my_address,R.id.ll_five_collect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_my_login:
                initLogin();//登录
                break;
            case R.id.ll_my_address:
                Intent intent = new Intent(getActivity(), AddressActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_five_collect:
                Intent intent1 = new Intent(getActivity(), FavoritesActivity.class);
                startActivity(intent1);
                break;
        }
    }

    private void initLogin() {
        if (!TextUtils.isEmpty(SpUtils.getInstance().getString("token"))) {
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivityForResult(intent, 100);
            //ActivityManagerUtils.startActivity(getActivity(), LoginActivity.class);
        } else {
            ToastUtils.s(mContext, getString(R.string.tips_ok_login));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 100) {
            String name = data.getStringExtra("name");//登录之后显示名字
            tv_Login.setText(name);
        }
    }

    @Override
    protected void initData() {

    }

}
