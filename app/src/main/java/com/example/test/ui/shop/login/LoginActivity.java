package com.example.test.ui.shop.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.test.R;
import com.example.test.base.BaseActivity;
import com.example.test.model.bean.shop.login.LoginBean;
import com.example.test.presenter.shop.login.LoginPresenter;
import com.example.test.utils.ActivityManagerUtils;
import com.example.test.utils.SpUtils;
import com.example.test.utils.ToastUtils;
import com.example.test.view.shop.login.ILogin;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 密码必须是8位以上  字母+数字组合
 */

public class LoginActivity extends BaseActivity<ILogin.Presenter> implements ILogin.View {

    @BindView(R.id.et_input_username)
    EditText et_Username;
    @BindView(R.id.et_input_pw)
    EditText et_Pw;
    @BindView(R.id.iv_img_pw)
    ImageView iv_img;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_login_register)
    TextView tv_Register;
    @BindView(R.id.tv_login_forget_pw)
    TextView tv_Forget;
    private String token;
    String register_token;
    String register_username;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }


    @Override
    protected ILogin.Presenter createPersenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initView() {
        iv_img.setTag(1);

        Intent intent = getIntent();
        register_token = intent.getStringExtra("register_token");
        register_username = intent.getStringExtra("register_username");
    }

    @Override
    protected void initData() {
        persenter = new LoginPresenter(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void postLoginReturn(LoginBean result) {
        token = result.getData().getToken();
        if (!TextUtils.isEmpty(token)) {
            SpUtils.getInstance().setValue("token", result.getData().getToken());
            SpUtils.getInstance().setValue("uid", result.getData().getUserInfo().getUid());

            String name = et_Username.getText().toString();
            Intent intent = getIntent();
            intent.putExtra("name",name);
            setResult(100,intent);
            SpUtils.getInstance().setValue("username",name);

            finishAndRemoveTask();//关闭当前页面返回之前页面+
        }
    }


    @OnClick({R.id.iv_img_pw, R.id.btn_login,R.id.tv_login_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_img_pw:
                initPwImg();
                break;
            case R.id.btn_login://点击登录
                login();
                break;
            case R.id.tv_login_register://点击注册
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivityForResult(intent,100);
                break;
        }
    }

    private void initPwImg() {
        int tag = (int) iv_img.getTag();
        if (tag == 1) {
            iv_img.setImageResource(R.mipmap.ic_pw_close);
            iv_img.setTag(2);//关闭
            et_Pw.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            iv_img.setImageResource(R.mipmap.ic_pw_open);
            iv_img.setTag(1);//打开密码
            et_Pw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

        }
    }

    private void login() {
        //获取输入的用户名和密码
        String username = et_Username.getText().toString();
        String pw = et_Pw.getText().toString();
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pw)){
            String token=SpUtils.getInstance().getString("token");
            if(token!=null){
                persenter.postLogin(username, pw);
                if (token != null) {
                    persenter.postLogin(username, pw);
                    SpUtils.getInstance().setValue("name",username);
                } else {
                    ToastUtils.s(this, getString(R.string.tips_login));
                }
            }else {
                ToastUtils.s(this,getString(R.string.tips_login));
            }
        }else{
            ToastUtils.s(this,getString(R.string.tips_login_input));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == 100){
            String regist_name = data.getStringExtra("name");
            String regist_pw = data.getStringExtra("pw");
            et_Username.setText(regist_name);
            et_Pw.setText(regist_pw);
        }
    }

}