package com.crazywah.piedpiper.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.util.DensityUtils;
import com.crazywah.piedpiper.util.PhotoDenpendence;

public class PhotoDialog extends Dialog implements View.OnClickListener {

    private View dialogView;
    private View takePhotoRl;
    private View selectPhotoRl;
    private View checkPhotoRl;
    private View cancelRl;
    private PhotoDenpendence dependence;

    public PhotoDialog(@NonNull Context context) {
        super(context);
        init(context);
    }

    public PhotoDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected PhotoDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_photo, null, false);
        setContentView(dialogView, new ViewGroup.LayoutParams(DensityUtils.getScreenWidth(), ViewGroup.LayoutParams.WRAP_CONTENT));
        if (getWindow() != null) {
            getWindow().setGravity(Gravity.BOTTOM);
            getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        }
        takePhotoRl = dialogView.findViewById(R.id.dialog_photo_take_rl);
        selectPhotoRl = dialogView.findViewById(R.id.dialog_photo_select_rl);
        checkPhotoRl = dialogView.findViewById(R.id.dialog_photo_check_rl);
        cancelRl = dialogView.findViewById(R.id.dialog_photo_cancel_rl);

        takePhotoRl.setOnClickListener(this);
        selectPhotoRl.setOnClickListener(this);
        checkPhotoRl.setOnClickListener(this);
        cancelRl.setOnClickListener(this);
    }

    public void hideTakeAndSelect() {
        takePhotoRl.setVisibility(View.GONE);
        selectPhotoRl.setVisibility(View.GONE);
    }

    public void hideCheckImg(){
        checkPhotoRl.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_photo_take_rl:
                dependence.commitTakePhotoRequest();
                break;
            case R.id.dialog_photo_select_rl:
                dependence.commitSelectPictureRequest();
                break;
            case R.id.dialog_photo_check_rl:
                if (dependence.getCallBack() != null) {
                    dependence.getCallBack().checkBitmap();
                }
                break;
            case R.id.dialog_photo_cancel_rl:
                dismiss();
                break;
            default:
                break;
        }
    }


    public void setDependence(PhotoDenpendence dependence) {
        this.dependence = dependence;
    }

    public PhotoDenpendence getDependence() {
        return dependence;
    }
}
