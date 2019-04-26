package com.crazywah.piedpiper.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.common.PiedToast;

public class InputView extends RelativeLayout implements View.OnFocusChangeListener {

    private View rootView;

    private EditText inputEt;
    private Button inputBtn;

    public InputView(Context context) {
        super(context);
        init(context);
    }

    public InputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public InputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public InputView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        rootView = LayoutInflater.from(context).inflate(R.layout.view_item_input, this, false);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(rootView);
        inputEt = rootView.findViewById(R.id.input_et);
        inputBtn = rootView.findViewById(R.id.input_btn);
        this.setOnFocusChangeListener(this);
    }

    public void show(String hint, final CallBack callBack) {
        rootView.setVisibility(VISIBLE);
        inputEt.setHint(hint);
        inputBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    if (!TextUtils.isEmpty(inputEt.getText().toString())) {
                        callBack.onFinish(inputEt.getText().toString());
                    } else {
                        PiedToast.showErrorShort("请输入内容");
                    }
                }
            }
        });
    }

    public void dismiss() {
        inputEt.setText("");
        rootView.setVisibility(GONE);
    }

    public boolean isShowing() {
        return rootView.getVisibility() == VISIBLE;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            dismiss();
        }
    }

    public interface CallBack {
        void onFinish(String inputText);
    }

}
