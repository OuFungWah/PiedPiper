package com.crazywah.piedpiper.module.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.base.BaseActivity;
import com.crazywah.piedpiper.common.ConstValue;
import com.crazywah.piedpiper.common.PiedDatePicker;
import com.crazywah.piedpiper.common.PiedEvent;
import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.module.login.LoginConst;
import com.crazywah.piedpiper.module.user.logic.ModifyInfoLogic;
import com.crazywah.piedpiper.util.SPUtil;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;

public class ModifyInfoActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "ModifyInfoActivity";

    public static final String KEY_METHOD = "key_method";

    private int type = 0;

    private ModifyInfoLogic logic;

    private TextView titileTv;
    private TextView finishTv;
    private RadioGroup radioGroup;
    private View inputContainer;
    private EditText firstEt;
    private EditText secondEt;
    private TextView dateTv;

    private PiedDatePicker datePicker;

    private String firstStr;
    private String secondStr;
    private int checkedGender;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static void launch(Context context, String method) {
        Intent intent = new Intent(context, ModifyInfoActivity.class);
        intent.putExtra(KEY_METHOD, method);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleArgument(getIntent());
        initView();
        setView();
    }

    private void initView() {
        titileTv = findViewById(R.id.info_modify_title_tv);
        finishTv = findViewById(R.id.info_modify_finish_tv);
        radioGroup = findViewById(R.id.info_modify_radio_group);
        inputContainer = findViewById(R.id.info_modify_edittext_container);
        firstEt = findViewById(R.id.info_modify_first_input_et);
        secondEt = findViewById(R.id.info_modify_second_input_et);
        dateTv = findViewById(R.id.info_modify_date_tv);
        datePicker = PiedDatePicker.create();
    }

    private void setView() {
        switch (type) {
            case ModifyInfoLogic.INFO_TYPE_PASSWORD:
                showTwoEt("修改密码", "输入新密码", null, "再次输入密码", null);
                firstEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                secondEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                firstEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                secondEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case ModifyInfoLogic.INFO_TYPE_NICKNAME:
                showOneEt("修改昵称", "请输入新昵称", PiedPiperApplication.getLoginUser().getNickname());
                break;
            case ModifyInfoLogic.INFO_TYPE_MOBILE:
                showOneEt("修改电话", "请输入新电话", PiedPiperApplication.getLoginUser().getMobile());
                firstEt.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
                break;
            case ModifyInfoLogic.INFO_TYPE_ADDRESS:
                showOneEt("修改地址", "请输入新地址", PiedPiperApplication.getLoginUser().getAddress());
                break;
            case ModifyInfoLogic.INFO_TYPE_EMAIL:
                showOneEt("修改邮箱", "请输入新邮箱", PiedPiperApplication.getLoginUser().getEmail());
                firstEt.setInputType(EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                break;
            case ModifyInfoLogic.INFO_TYPE_SIGNATURE:
                showOneEt("修改签名", "请输入新签名", PiedPiperApplication.getLoginUser().getSignature());
                break;
            case ModifyInfoLogic.INFO_TYPE_BIRTHDAY:
                if(PiedPiperApplication.getLoginUser().getBirthday() !=null ) {
                    showDateEt("修改生日", sdf.format(PiedPiperApplication.getLoginUser().getBirthday()));
                    datePicker.setDate(PiedPiperApplication.getLoginUser().getBirthday());
                }else{
                    showDateEt("修改生日", "请选择你的生日");
                }
                break;
            case ModifyInfoLogic.INFO_TYPE_GENDER:
                showRadioGroup("修改性别", PiedPiperApplication.getLoginUser().getGender());
                break;
            default:
                break;
        }
        finishTv.setOnClickListener(this);
        dateTv.setOnClickListener(this);
    }

    private void showRadioGroup(String title, int selectedIndex) {
        titileTv.setText(title);
        radioGroup.setVisibility(View.VISIBLE);
        switch (selectedIndex) {
            case 0:
                radioGroup.check(R.id.info_modify_radio_male);
                break;
            case 1:
                radioGroup.check(R.id.info_modify_radio_female);
                break;
            default:
                radioGroup.check(R.id.info_modify_radio_unknow);
                break;
        }
        inputContainer.setVisibility(View.GONE);
        dateTv.setVisibility(View.GONE);
    }

    private void showOneEt(String title, String firstHint, String firstText) {
        titileTv.setText(title);
        radioGroup.setVisibility(View.GONE);
        inputContainer.setVisibility(View.VISIBLE);
        firstEt.setVisibility(View.VISIBLE);
        firstEt.setHint(firstHint);
        firstEt.setText(firstText);
        secondEt.setVisibility(View.GONE);
        dateTv.setVisibility(View.GONE);
    }

    private void showTwoEt(String title, String firstHint, String firstText, String secondHint, String secondText) {
        titileTv.setText(title);
        radioGroup.setVisibility(View.GONE);
        inputContainer.setVisibility(View.VISIBLE);
        firstEt.setVisibility(View.VISIBLE);
        secondEt.setVisibility(View.VISIBLE);
        firstEt.setHint(firstHint);
        firstEt.setText(firstText);
        secondEt.setHint(secondHint);
        secondEt.setText(secondText);
        dateTv.setVisibility(View.GONE);
    }

    private void showDateEt(String title, String date) {
        titileTv.setText(title);
        radioGroup.setVisibility(View.GONE);
        inputContainer.setVisibility(View.GONE);
        dateTv.setVisibility(View.VISIBLE);
        dateTv.setText(date);
    }

    private void handleArgument(Intent intent) {
        String method = intent.getStringExtra(KEY_METHOD);
        switch (method) {
            case "getNickname":
                type = ModifyInfoLogic.INFO_TYPE_NICKNAME;
                break;
            case "getPassword":
                type = ModifyInfoLogic.INFO_TYPE_PASSWORD;
                break;
            case "getGenderString":
                type = ModifyInfoLogic.INFO_TYPE_GENDER;
                break;
            case "getBirthday":
                type = ModifyInfoLogic.INFO_TYPE_BIRTHDAY;
                break;
            case "getMobile":
                type = ModifyInfoLogic.INFO_TYPE_MOBILE;
                break;
            case "getAddress":
                type = ModifyInfoLogic.INFO_TYPE_ADDRESS;
                break;
            case "getEmail":
                type = ModifyInfoLogic.INFO_TYPE_EMAIL;
                break;
            case "getSignature":
                type = ModifyInfoLogic.INFO_TYPE_SIGNATURE;
                break;
            default:
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_info_modify;
    }

    @Override
    protected boolean onHandle(Message msg) {
        switch (msg.what) {
            case ModifyInfoLogic.MSG_UPDATE_SUCC:
                PiedToast.showShort("修改成功");
                switch (type) {
                    case ModifyInfoLogic.INFO_TYPE_PASSWORD:
                        PiedPiperApplication.getLoginUser().setPassword(firstStr);
                        if (SPUtil.from(ConstValue.SP_NAMES[ConstValue.LOGIN_INFO]).contains(LoginConst.SP_KEY_ISAUTO) && SPUtil.from(ConstValue.SP_NAMES[ConstValue.LOGIN_INFO]).getBoolean(LoginConst.SP_KEY_ISAUTO)) {
                            SPUtil.from(ConstValue.SP_NAMES[ConstValue.LOGIN_INFO]).save(LoginConst.SP_KEY_PASSWORD, firstStr);
                        }
                        break;
                    case ModifyInfoLogic.INFO_TYPE_NICKNAME:
                        PiedPiperApplication.getLoginUser().setNickname(firstStr);
                        break;
                    case ModifyInfoLogic.INFO_TYPE_MOBILE:
                        PiedPiperApplication.getLoginUser().setMobile(firstStr);
                        break;
                    case ModifyInfoLogic.INFO_TYPE_ADDRESS:
                        PiedPiperApplication.getLoginUser().setAddress(firstStr);
                        break;
                    case ModifyInfoLogic.INFO_TYPE_EMAIL:
                        PiedPiperApplication.getLoginUser().setEmail(firstStr);
                        break;
                    case ModifyInfoLogic.INFO_TYPE_SIGNATURE:
                        PiedPiperApplication.getLoginUser().setSignature(firstStr);
                        break;
                    case ModifyInfoLogic.INFO_TYPE_BIRTHDAY:
                        PiedPiperApplication.getLoginUser().setBirthday(datePicker.getDate());
                        break;
                    case ModifyInfoLogic.INFO_TYPE_GENDER:
                        PiedPiperApplication.getLoginUser().setGender(getSelectedRadioIndex());
                        break;
                    default:
                        break;
                }
                EventBus.getDefault().post(new PiedEvent(PiedEvent.EventType.MSG_NOTIFY_REGISTRANT_UPDATE));
                finish();
                break;
            case ModifyInfoLogic.MSG_UPDATE_FAIL:
                PiedToast.showShort("修改失败");
                break;
        }
        return false;
    }

    @Override
    protected void prepareLogic() {
        logic = new ModifyInfoLogic(this, handler);
    }

    @Override
    public void onEvent(PiedEvent event) {
        super.onEvent(event);
        switch (event.getType()) {
            case MSG_AFTER_SELECT_DATE:
                if (type == ModifyInfoLogic.INFO_TYPE_BIRTHDAY) {
                    dateTv.setText(sdf.format(datePicker.getDate()));
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.info_modify_finish_tv:
                firstStr = firstEt.getText().toString();
                secondStr = secondEt.getText().toString();
                checkedGender = getSelectedRadioIndex();
                switch (type) {
                    case ModifyInfoLogic.INFO_TYPE_PASSWORD:
                        if (checkInput("密码", firstStr, 6, 16)) {
                            if (!firstStr.equals(secondStr)) {
                                PiedToast.showErrorShort("请正确地再次输入密码");
                            } else {
                                logic.updatePassowrd(firstStr);
                            }
                        }
                        break;
                    case ModifyInfoLogic.INFO_TYPE_NICKNAME:
                        if (checkInput("昵称", firstStr, 1, 16)) {
                            logic.updateNickname(firstStr);
                        }
                        break;
                    case ModifyInfoLogic.INFO_TYPE_MOBILE:
                        if (checkInput("电话", firstStr, 0, 13)) {
                            logic.updateMobile(firstStr);
                        }
                        break;
                    case ModifyInfoLogic.INFO_TYPE_ADDRESS:
                        if (checkInput("地址", firstStr, 0, 20)) {
                            logic.updateMobile(firstStr);
                        }
                        break;
                    case ModifyInfoLogic.INFO_TYPE_EMAIL:
                        if (checkInput("邮箱", firstStr, 0, 20)) {
                            logic.updateEmail(firstStr);
                        }
                        break;
                    case ModifyInfoLogic.INFO_TYPE_SIGNATURE:
                        if (checkInput("签名", firstStr, 0, 50)) {
                            logic.updateSignature(firstStr);
                        }
                        break;
                    case ModifyInfoLogic.INFO_TYPE_BIRTHDAY:
                        if (datePicker.getDate() != null) {
                            logic.updateBirthday(datePicker.getDate());
                        }
                        break;
                    case ModifyInfoLogic.INFO_TYPE_GENDER:
                        logic.updateGender(checkedGender + "");
                        break;
                    default:
                        break;
                }
                break;
            case R.id.info_modify_date_tv:
                datePicker.show(getSupportFragmentManager(), "ModifyBirthday");
                break;
        }
    }

    private int getSelectedRadioIndex() {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.info_modify_radio_male:
                return 0;
            case R.id.info_modify_radio_female:
                return 1;
            case R.id.info_modify_radio_unknow:
                return 2;
            default:
                return 3;
        }
    }

    private boolean checkInput(String title, String content, int min, int max) {
        if (TextUtils.isEmpty(content)) {
            PiedToast.showErrorShort("请填写" + title);
            return false;
        } else if (content.length() < min || content.length() > max) {
            PiedToast.showErrorShort(title + "必须" + min + "个字符或以上" + max + "个字符以下");
            return false;
        } else {
            return true;
        }
    }

}
