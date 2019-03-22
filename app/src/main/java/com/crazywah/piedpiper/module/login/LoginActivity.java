package com.crazywah.piedpiper.module.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazywah.piedpiper.module.homepage.activity.MainActivity;
import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.base.BaseActivity;
import com.crazywah.piedpiper.common.ConstValue;
import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.module.register.activity.RegisterActivity;
import com.crazywah.piedpiper.util.SPUtil;
import com.google.gson.Gson;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private LoginLogic logic;

    private ImageView avatarImg;
    private TextView registerTv;
    private TextView loginErrorTv;
    private EditText accountEdt;
    private EditText passwordEdt;
    private CheckBox rememberPasswordCb;
    private CheckBox autoLoginCb;
    private Button loginBtn;

    private String accountStr;
    private String passwordStr;

    private Gson gson = new Gson();

    private String spname = ConstValue.SP_NAMES[ConstValue.LOGIN_INFO];

    public static void launch(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: " + this.getPackageName());
        initView();
        setView();
    }

    private void initView() {
        avatarImg = findViewById(R.id.login_avatar_img);
        registerTv = findViewById(R.id.login_to_register);
        loginErrorTv = findViewById(R.id.login_error_tv);
        accountEdt = findViewById(R.id.login_account_et);
        passwordEdt = findViewById(R.id.login_password_et);
        rememberPasswordCb = findViewById(R.id.remember_cb);
        autoLoginCb = findViewById(R.id.auto_login_cb);
        loginBtn = findViewById(R.id.login_btn);
    }

    private void setView() {
        //还原记住密码
        if (SPUtil.from(spname).contains(LoginConst.SP_KEY_ISREMEMBER) && SPUtil.from(spname).getBoolean(LoginConst.SP_KEY_ISREMEMBER)) {
            accountEdt.setText(SPUtil.from(spname).getString(LoginConst.SP_KEY_ACCOUNTID));
            passwordEdt.setText(SPUtil.from(spname).getString(LoginConst.SP_KEY_PASSWORD));
            rememberPasswordCb.setChecked(true);
        }
        loginBtn.setOnClickListener(this);
        registerTv.setOnClickListener(this);
        autoLoginCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rememberPasswordCb.setChecked(isChecked);
            }
        });
        rememberPasswordCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked && autoLoginCb.isChecked()) {
                    autoLoginCb.setChecked(false);
                }
            }
        });
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
                saveLocal();
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
                accountStr = accountEdt.getText().toString();
                passwordStr = passwordEdt.getText().toString();
                logic.doLogin(accountStr, passwordStr);
                break;
            case R.id.login_to_register:
                RegisterActivity.launch(this);
                break;
            default:
                break;
        }
    }

    private void saveLocal() {
        //记住密码与自动登录
        SPUtil.from(spname).save(LoginConst.SP_KEY_ISREMEMBER, rememberPasswordCb.isChecked());
        SPUtil.from(spname).save(LoginConst.SP_KEY_ISAUTO, autoLoginCb.isChecked());

        if (rememberPasswordCb.isChecked()) {
            SPUtil.from(spname).save(LoginConst.SP_KEY_ACCOUNTID, accountStr);
            SPUtil.from(spname).save(LoginConst.SP_KEY_PASSWORD, passwordStr);
        } else {
            SPUtil.from(spname).remove(LoginConst.SP_KEY_ACCOUNTID);
            SPUtil.from(spname).remove(LoginConst.SP_KEY_PASSWORD);
        }
        if (autoLoginCb.isChecked()) {
            SPUtil.from(spname).save(LoginConst.SP_KEY_TOKEN, PiedPiperApplication.getLoginUser().getToken());
            SPUtil.from(spname).save(LoginConst.SP_KEY_USER, gson.toJson(PiedPiperApplication.getLoginUser()));
        } else {
            SPUtil.from(spname).remove(LoginConst.SP_KEY_TOKEN);
            SPUtil.from(spname).remove(LoginConst.SP_KEY_USER);

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
