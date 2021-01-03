package com.example.test.ui.shop.me;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.test.R;
import com.example.test.app.MyApp;
import com.example.test.base.BaseFragment;
import com.example.test.base.IBasePersenter;
import com.example.test.model.bean.shop.login.LogoutBean;
import com.example.test.presenter.shop.login.LogoutPresenter;
import com.example.test.ui.shop.login.LoginActivity;
import com.example.test.ui.shop.me.address.AddressActivity;
import com.example.test.ui.shop.me.collect.FavoritesActivity;
import com.example.test.ui.shop.me.headportrait.Head_PortraitActivity;
import com.example.test.utils.ActivityCollectorUtil;
import com.example.test.utils.ImageLoaderUtils;
import com.example.test.utils.SpUtils;
import com.example.test.utils.ToastUtils;
import com.example.test.utils.TxtUtils;
import com.example.test.view.shop.login.ILogout;
import com.example.test.view.shop.me.headportrait.IUpdateUserInfo;

import butterknife.BindView;
import butterknife.OnClick;

//TODO 我的展示
public class MeFragment extends BaseFragment<ILogout.Presenter> implements ILogout.View {
    @BindView(R.id.ll_my_address)
    LinearLayout ll_address;
    @BindView(R.id.iv_my_img)
    ImageView iv_Img;
    @BindView(R.id.iv_my_return)
    ImageView iv_Return;

    public static final int LOGIN_ME = 10001; //登录成功的回传值
    public static final int LOGINOUT_ME = 10002; //退出登录的回传
    @BindView(R.id.tv_my_login)
    TextView tv_Login;
    @BindView(R.id.tv_my_head_nickname)
    TextView tv_Name;
    @BindView(R.id.tv_my_head_mark)
    TextView tv_Mark;

    //懒加载
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            initData();
            initLogin();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_me;
    }

    @Override
    protected ILogout.Presenter createPrenter() {
        return new LogoutPresenter(this);
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.ll_my_address, R.id.ll_five_collect, R.id.iv_my_img, R.id.iv_my_return, R.id.tv_my_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
                presenter.postLogout();
                break;
            case R.id.tv_my_login://登录
                initLogin();
                break;
        }
    }


    //判断是否登录
    private void initLogin() {
        String token = SpUtils.getInstance().getString("token");
        if (!TextUtils.isEmpty(token)) {
            openUserInfoDetail();//不为空 进行跳转到用户详情
        } else {
            openLogin();//为空 进行登录
        }
    }

    //TODO 打开登录页面
    private void openLogin() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        startActivityForResult(intent, LOGIN_ME);
        isLogin(false);
    }

    //TODO 打开用户信息
    private void openUserInfoDetail() {
        ToastUtils.s(getActivity(), "此用户已登录");
        Intent intent = new Intent(mContext, Head_PortraitActivity.class);

        String txtName = tv_Name.getText().toString();//姓名
        String txtMark = tv_Mark.getText().toString();//签名

        MyApp.getMap().put("txtName",txtName);
        MyApp.getMap().put("txtMark",txtMark);

        startActivity(intent);
        isLogin(true);
    }


    //TODO 登录状态的界面控制
    private void isLogin(boolean b) {
        if (b) {
            tv_Login.setVisibility(View.GONE);//隐藏
            tv_Name.setVisibility(View.VISIBLE);//显示
            tv_Mark.setVisibility(View.VISIBLE);
            String username = SpUtils.getInstance().getString("username");
            String nickname = SpUtils.getInstance().getString("nickname");
            String avatar = SpUtils.getInstance().getString("avatar");
            String mark = SpUtils.getInstance().getString("mark");
            if (!TextUtils.isEmpty(nickname)) {
                tv_Name.setText(nickname);
            } else {
                tv_Name.setText(username);
            }
            ImageLoaderUtils.loadImage(avatar, iv_Img);
            TxtUtils.setTextView(tv_Mark, mark);
            String img = SpUtils.getInstance().getString("img");
            if (!TextUtils.isEmpty(img)) {
                //上传头像后选择头像并返回
                Glide.with(this).load(img).apply(new RequestOptions().circleCrop()).into(iv_Img);
            }
        } else {
            tv_Login.setVisibility(View.VISIBLE);//点击登录显示
            tv_Name.setVisibility(View.GONE);//隐藏
            tv_Mark.setVisibility(View.GONE);
            Glide.with(this).load(R.mipmap.c4).apply(new RequestOptions().circleCrop()).into(iv_Img);
        }
    }

    @Override
    protected void initData() {
        String token = SpUtils.getInstance().getString("token");
        //如果token不为空
        if (!TextUtils.isEmpty(token)) {
            isLogin(true);
        } else {
            isLogin(false);
        }
    }

    //TODO 退出登录
    @Override
    public void postLogoutReturn(LogoutBean result) {
        SpUtils.getInstance().remove("token");

        //清空Sp
        //SpUtils.getInstance().delete();

        //退出登录
        ActivityCollectorUtil.finishAllActivity();

        //关闭页面
        // finishAndRemoveTask();
        isLogin(false);
    }
}
