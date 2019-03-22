package com.crazywah.piedpiper.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.util.BitmapUtil;
import com.crazywah.piedpiper.util.PermisstionUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public abstract class BasePhotoActivity extends BaseActivity {

    protected Uri imageUri = null;
    protected Bitmap picBitmap = null;
    protected File destination;
    private String innerPath;

    private int afterRequestPermission = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageUri = setImageUri();
        picBitmap = setPicBitmap();
        innerPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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

    protected void commitSelectPictureRequest() {
        if (PermisstionUtil.check(PermisstionUtil.TYPE_READ_STORAGE) && PermisstionUtil.check(PermisstionUtil.TYPE_READ_STORAGE)) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_CODE_SELECT_PHOTO);
        } else {
            afterRequestPermission = REQUEST_CODE_PERMISSION_STORAGE;
            PermisstionUtil.requestAllPermission(this, REQUEST_CODE_PERMISSION_STORAGE);
        }
    }

    protected void commitTakePhotoRequest() {
        if (PermisstionUtil.check(PermisstionUtil.TYPE_CAMERA)) {
            destination = new File(innerPath, System.currentTimeMillis() + ".png");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
            startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
        } else {
            afterRequestPermission = REQUEST_CODE_PERMISSION_CAMERA;
            PermisstionUtil.requestAllPermission(this, REQUEST_CODE_PERMISSION_CAMERA);
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
            if (imageUri != null && picBitmap != null) {
                imageUri = data.getData();
                try {
                    picBitmap = BitmapUtil.getBitmapFormUri(this, imageUri);
                    afterSelected(imageUri, picBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    /**
     * 选择图片后调用的方法
     *
     * @param imageUri
     * @param picBitmap
     */
    protected abstract void afterSelected(Uri imageUri, Bitmap picBitmap);

    protected abstract void afterTakePhoto();

    /**
     * 设置Uri资源
     *
     * @return
     */
    protected abstract Uri setImageUri();

    /**
     * 设置位图资源
     *
     * @return
     */
    protected abstract Bitmap setPicBitmap();

}
