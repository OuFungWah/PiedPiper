package com.crazywah.piedpiper.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazywah.piedpiper.MainActivity;
import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.base.BaseActivity;
import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.register.activity.RegisterActivity;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private LoginLogic logic;

    private ImageView avatarImg;
    private TextView registerTv;
    private TextView loginErrorTv;
    private EditText accountEdt;
    private EditText passwordEdt;
    private Button loginBtn;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: " + this.getPackageName());
        initView();
    }

    private void initView() {
        avatarImg = findViewById(R.id.login_avatar_img);
        registerTv = findViewById(R.id.login_to_register);
        loginErrorTv = findViewById(R.id.login_error_tv);
        accountEdt = findViewById(R.id.login_account_et);
        passwordEdt = findViewById(R.id.login_password_et);
        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);
        registerTv.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected boolean onHandle(Message msg) {
        switch (msg.what) {
            case LoginLogic.ERROR_NO_ACCOUNT:
                loginErrorTv.setText("账户不可为空");
                loginErrorTv.setVisibility(View.VISIBLE);
                break;
            case LoginLogic.ERROR_NO_PASSWORD:
                loginErrorTv.setText("密码不可为空");
                loginErrorTv.setVisibility(View.VISIBLE);
                break;
            case LoginLogic.ERROR_ACCOUNT_LEN:
                loginErrorTv.setText("账户长度不小于8个字符");
                loginErrorTv.setVisibility(View.VISIBLE);
                break;
            case LoginLogic.ERROR_PASSWORD_LEN:
                loginErrorTv.setText("密码长度不小于8 个字符");
                loginErrorTv.setVisibility(View.VISIBLE);
                break;
            case LoginLogic.LOGIN_SUCC:
                PiedToast.showShort("登录成功");
                MainActivity.launch(this);
                finish();
                loginErrorTv.setVisibility(View.GONE);
                break;
            case LoginLogic.LOGIN_FAIL:
                PiedToast.showErrorShort((String) msg.obj);
                loginErrorTv.setVisibility(View.GONE);
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    protected void prepareLogic() {
        logic = new LoginLogic(this, handler);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                String accountStr = accountEdt.getText().toString();
                String passwordStr = passwordEdt.getText().toString();
                logic.doLogin(accountStr, passwordStr);
                break;
            case R.id.login_to_register:
                RegisterActivity.launch(this);
                break;
            default:
                break;
        }
    }

    private long pressTime = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - pressTime > 2000) {
            PiedToast.showShort("再按一次退出应用");
            pressTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }
}
