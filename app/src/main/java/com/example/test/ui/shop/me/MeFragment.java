package com.example.test.ui.shop.me;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.test.R;
import com.example.test.base.BaseFragment;
import com.example.test.base.IBasePersenter;
import com.example.test.ui.shop.login.LoginActivity;
import com.example.test.ui.shop.me.address.AddressActivity;
import com.example.test.ui.shop.me.collect.FavoritesActivity;
import com.example.test.ui.shop.me.headportrait.Head_PortraitActivity;
import com.example.test.utils.ImageLoaderUtils;
import com.example.test.utils.SpUtils;
import com.example.test.utils.TxtUtils;

import butterknife.BindView;
import butterknife.OnClick;

//TODO 我的展示
public class MeFragment extends BaseFragment {
    @BindView(R.id.tv_my_login)
    TextView tv_Login;
    @BindView(R.id.ll_my_address)
    LinearLayout ll_address;
    @BindView(R.id.iv_my_img)
    ImageView iv_Img;
    @BindView(R.id.iv_my_return)
    ImageView iv_Return;

    public static final int LOGIN_ME = 10001; //登录成功的回传值
    public static final int LOGINOUT_ME = 10002; //退出登录的回传




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

    }

    @OnClick({R.id.tv_my_login, R.id.ll_my_address, R.id.ll_five_collect, R.id.iv_my_img,R.id.iv_my_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_my_login:
                initLogin();//登录
                break;
            case R.id.ll_my_address://地址管理
                Intent intent = new Intent(getActivity(), AddressActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_five_collect://收藏页面
                Intent intent1 = new Intent(getActivity(), FavoritesActivity.class);
                startActivity(intent1);
                break;
            case R.id.iv_my_img://点击头像
                Intent intent2 = new Intent(getActivity(), Head_PortraitActivity.class);
                startActivity(intent2);
                break;
            case R.id.iv_my_return://退出登录
                loginOut();
                break;
        }
    }

    //判断是否登录
    private void initLogin() {
        String token = SpUtils.getInstance().getString("token");
        if (!TextUtils.isEmpty(token)) {
            openUserInfoDetail();
        } else {
            openLogin();
        }

    }

    //TODO 退出登录
    private void loginOut(){

    }

    //TODO 打开登录页面
    private void openLogin() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        startActivityForResult(intent, LOGIN_ME);
    }

    //TODO 打开用户信息
    private void openUserInfoDetail() {

    }


    //TODO 登录状态的界面控制

    private void isLogin(boolean b) {
        if (b) {
            String username = SpUtils.getInstance().getString("username");
            String nickname = SpUtils.getInstance().getString("nickname");
            String avatar = SpUtils.getInstance().getString("avatar");
            String mark = SpUtils.getInstance().getString("mark");
            if (!TextUtils.isEmpty(nickname)) {
                tv_Login.setText(nickname);
            } else {
                tv_Login.setText(username);
            }
            ImageLoaderUtils.loadImage(avatar, iv_Img);
        }
    }


    //TODO 登录成功
    public void loginSuccess() {
        isLogin(true);
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
        String token = SpUtils.getInstance().getString("token");
        if (!TextUtils.isEmpty(token)) {
            isLogin(true);
        } else {
            isLogin(false);
        }
    }

}
