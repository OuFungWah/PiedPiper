package com.crazywah.piedpiper.util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.base.BaseActivity;
import com.crazywah.piedpiper.base.BaseResultDependence;
import com.crazywah.piedpiper.common.PiedToast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class PhotoDenpendence implements BaseResultDependence {

    private static final String TAG = "PhotoDenpendence";

    public static final int REQUEST_CODE_PERMISSION_STORAGE = 0x201;
    public static final int REQUEST_CODE_PERMISSION_CAMERA = 0x202;
    public static final int REQUEST_CODE_PERMISSION_MESSAGE = 0x203;
    public static final int REQUEST_CODE_PERMISSION_CALL = 0x204;
    public static final int REQUEST_CODE_TAKE_PHOTO = 0x205;
    public static final int REQUEST_CODE_SELECT_PHOTO = 0x206;

    protected Uri imageUri = null;
    protected Bitmap picBitmap = null;
    protected File destination;
    private String innerPath;

    private BaseActivity activity;
    private Handler handler;
    private BitmapCallBack callBack;

    private int afterRequestPermission = -1;

    public PhotoDenpendence(BaseActivity activity, Handler handler) {
        this.activity = activity;
        this.handler = handler;
        innerPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public void commitSelectPictureRequest() {
        if (PermisstionUtil.check(PermisstionUtil.TYPE_READ_STORAGE) && PermisstionUtil.check(PermisstionUtil.TYPE_READ_STORAGE)) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activity.startActivityForResult(intent, REQUEST_CODE_SELECT_PHOTO);
        } else {
            afterRequestPermission = REQUEST_CODE_PERMISSION_STORAGE;
            PermisstionUtil.requestAllPermission(activity, REQUEST_CODE_PERMISSION_STORAGE);
        }
    }

    public void commitTakePhotoRequest() {
        if (PermisstionUtil.check(PermisstionUtil.TYPE_CAMERA)) {
            destination = new File(innerPath, System.currentTimeMillis() + ".png");
            Log.d(TAG, "commitTakePhotoRequest: path = " + destination.getAbsolutePath());
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(activity, PiedPiperApplication.getInstance().getPackageName() + ".my.package.name.provider", destination));
            }else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
            }
            activity.startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
        } else {
            afterRequestPermission = REQUEST_CODE_PERMISSION_CAMERA;
            PermisstionUtil.requestAllPermission(activity, REQUEST_CODE_PERMISSION_CAMERA);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_SELECT_PHOTO:
                selectPicture(resultCode, data);
                break;
            case REQUEST_CODE_TAKE_PHOTO:
                takePhoto(resultCode, data);
                break;
            case REQUEST_CODE_PERMISSION_STORAGE:
                requestStroagePermission(resultCode, data);
                break;
        }
    }

    /**
     * 选择图片的方法
     *
     * @param resultCode
     * @param data
     */
    protected void selectPicture(int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            if (imageUri != null) {
                imageUri = data.getData();
                try {
                    picBitmap = BitmapUtil.getBitmapFormUri(activity, imageUri);
                    if (callBack != null) {
                        callBack.afterGetBitmap(imageUri, picBitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                PiedToast.showErrorShort("选择图片失败");
            }
        } else {
            PiedToast.showErrorShort("选择图片失败");
        }
    }

    /**
     * 拍照
     *
     * @param resultCode
     * @param data
     */
    protected void takePhoto(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            try {
                imageUri = Uri.fromFile(destination);
                FileInputStream in = new FileInputStream(destination);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 10;
                picBitmap = BitmapFactory.decodeStream(in, null, options);
                if (callBack != null) {
                    callBack.afterGetBitmap(imageUri, picBitmap);
                }
                handler.sendEmptyMessage(REQUEST_CODE_TAKE_PHOTO);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        } else {
            PiedToast.showErrorShort("拍图片失败");
        }
    }

    protected void requestStroagePermission(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (afterRequestPermission) {
                case REQUEST_CODE_PERMISSION_STORAGE:
                    commitSelectPictureRequest();
                    break;
                case REQUEST_CODE_PERMISSION_CAMERA:
                    commitTakePhotoRequest();
                    break;
            }
        } else {
            PiedToast.showErrorShort("操作需要权限");
        }
    }

    public BitmapCallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(BitmapCallBack callBack) {
        this.callBack = callBack;
    }

    public void release() {
        activity = null;
    }

    public interface BitmapCallBack {
        void afterGetBitmap(Uri uri, Bitmap bitmap);
        void checkBitmap();
    }

}
