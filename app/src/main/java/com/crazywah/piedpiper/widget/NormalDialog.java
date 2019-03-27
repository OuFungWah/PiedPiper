package com.crazywah.piedpiper.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.util.DensityUtils;

public class NormalDialog extends Dialog implements View.OnClickListener {

    private View rootView;
    private TextView titleTv;
    private EditText inputEt;
    private TextView negativeTv;
    private TextView positiveTv;

    private CallBack callBack = null;

    public NormalDialog(@NonNull Context context) {
        super(context);
    }

    public NormalDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected NormalDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void init() {
        rootView = getLayoutInflater().inflate(R.layout.dialog_send_friend_request, null, false);
        titleTv = rootView.findViewById(R.id.normal_dialog_title_tv);
        inputEt = rootView.findViewById(R.id.normal_dialog_input_et);
        negativeTv = rootView.findViewById(R.id.normal_dialog_negative_tv);
        positiveTv = rootView.findViewById(R.id.normal_dialog_positive_tv);
        setContentView(rootView, new ViewGroup.LayoutParams(DensityUtils.dp2px(250), ViewGroup.LayoutParams.WRAP_CONTENT));
        getWindow().setGravity(Gravity.CENTER);
        negativeTv.setOnClickListener(this);
        positiveTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.normal_dialog_positive_tv:
                if (callBack != null) {
                    callBack.onPositive(inputEt.getText().toString());
                }
                break;
            case R.id.normal_dialog_negative_tv:
                if (callBack != null) {
                    callBack.onNegative();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void show() {
        super.show();
        inputEt.setText("");
    }

    public void setTitle(String title) {
        titleTv.setText(title);
    }

    public CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack {
        void onPositive(String text);

        void onNegative();
    }

}
