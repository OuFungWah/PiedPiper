package com.crazywah.piedpiper.register.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.base.BaseActivity;
import com.crazywah.piedpiper.common.ConstValue;
import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.login.LoginActivity;
import com.crazywah.piedpiper.register.logic.RegisterLogic;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private RegisterLogic logic;

    private TextView registerToLoginTv;
    private ImageView avatarImg;
    private EditText nicknameEt;
    private EditText accountEt;
    private EditText passwordEt;
    private EditText confirmPasswordEt;

    private TextView accountErrorTv;
    private TextView nicknameErrorTv;
    private TextView passwordErrorTv;
    private TextView confirmPasswordErrorTv;

    private Button registerBtn;

    private String accountId;
    private String nickname;
    private String password;
    private String confirmPassword;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, RegisterActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {

        registerToLoginTv = findViewById(R.id.register_to_login);
        avatarImg = findViewById(R.id.register_avatar_img);
        nicknameEt = findViewById(R.id.register_nickname_et);
        accountEt = findViewById(R.id.register_account_et);
        passwordEt = findViewById(R.id.register_password_et);
        confirmPasswordEt = findViewById(R.id.register_confirm_password_et);

        accountErrorTv = findViewById(R.id.register_account_error_tv);
        nicknameErrorTv = findViewById(R.id.register_nickname_error_tv);
        passwordErrorTv = findViewById(R.id.register_password_error_tv);
        confirmPasswordErrorTv = findViewById(R.id.register_confirm_password_error_tv);

        registerBtn = findViewById(R.id.register_btn);

        avatarImg.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        registerToLoginTv.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected boolean onHandle(Message msg) {
        switch (msg.what) {
            case RegisterLogic.REGISTER_SUCC:
                PiedToast.showShort("注册成功");
                LoginActivity.launch(this);
                finish();
                break;
            case RegisterLogic.REGISTER_FAIL:
                PiedToast.showShort((String)msg.obj);
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    protected void prepareLogic() {
        logic = new RegisterLogic(this, handler);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_avatar_img:
                break;
            case R.id.register_btn:
                accountId = accountEt.getText().toString();
                nickname = nicknameEt.getText().toString();
                password = passwordEt.getText().toString();
                confirmPassword = confirmPasswordEt.getText().toString();
                if (checkText()) {
                    logic.register(accountId, nickname, password);
                } else {
                    PiedToast.showErrorShort("信息有误，请检查");
                }
                break;
            case R.id.register_to_login:
                LoginActivity.launch(this);
                finish();
                break;
            default:
                break;
        }
    }

    private boolean checkText() {
        boolean flag = false;
        accountErrorTv.setVisibility(View.GONE);
        nicknameErrorTv.setVisibility(View.GONE);
        passwordErrorTv.setVisibility(View.GONE);
        confirmPasswordErrorTv.setVisibility(View.GONE);
        if (TextUtils.isEmpty(accountId)) {
            accountErrorTv.setText("*账户不可为空*");
            accountErrorTv.setVisibility(View.VISIBLE);
        } else if (accountId.length() > ConstValue.ACCOUNT_MAX || accountId.length() < ConstValue.ACCOUNT_MIN) {
            accountErrorTv.setText("*账户长度必须大于" + ConstValue.ACCOUNT_MIN + "并小于" + ConstValue.ACCOUNT_MAX + "*");
            accountErrorTv.setVisibility(View.VISIBLE);
        } else if (TextUtils.isEmpty(nickname)) {
            nicknameErrorTv.setText("*昵称不可为空*");
            nicknameErrorTv.setVisibility(View.VISIBLE);
        } else if (nickname.length() < ConstValue.NICKNAME_MIN || nickname.length() > ConstValue.NICKNAME_MAX) {
            nicknameErrorTv.setText("*昵称长度必须大于" + ConstValue.NICKNAME_MIN + "并小于" + ConstValue.NICKNAME_MAX + "*");
            nicknameErrorTv.setVisibility(View.VISIBLE);
        } else if (TextUtils.isEmpty(password)) {
            passwordErrorTv.setText("*密码不可为空*");
            passwordErrorTv.setVisibility(View.VISIBLE);
        } else if (password.length() < ConstValue.PASSWORD_MIN || password.length() > ConstValue.PASSWORD_MAX) {
            passwordErrorTv.setText("*密码长度必须大于" + ConstValue.PASSWORD_MIN + "并小于" + ConstValue.PASSWORD_MAX + "*");
            passwordErrorTv.setVisibility(View.VISIBLE);
        } else if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordErrorTv.setText("*请再填一次密码*");
            confirmPasswordErrorTv.setVisibility(View.VISIBLE);
        } else if (!password.equals(confirmPassword)) {
            confirmPasswordErrorTv.setText("*两次密码不一致*");
            confirmPasswordErrorTv.setVisibility(View.VISIBLE);
        } else {
            flag = true;
        }
        return flag;
    }

}
