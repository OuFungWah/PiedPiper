package com.crazywah.photo_viewer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.bumptech.glide.Glide;
import com.jsibbold.zoomage.ZoomageView;

public class PhotoViewerActivity extends FragmentActivity implements View.OnClickListener {

    private static final String TAG = "PhotoViewerActivity";

    public static final String KEY_BITMAP = "k_bitmap";
    public static final String KEY_URL = "k_url";
    public static final String KEY_URI = "k_uri";

    private View container;
    private ZoomageView zoomageView;
    private View backView;

    private Bitmap mBitmap;
    private String url;
    private Uri uri;

    private float distance = 500;

    public static void launch(Bitmap bitmap, Context context) {
        if (bitmap != null) {
            Intent intent = new Intent(context, PhotoViewerActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(KEY_BITMAP, bitmap);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }

    public static void launch(String url, Context context) {
        if (!TextUtils.isEmpty(url)) {
            Intent intent = new Intent(context, PhotoViewerActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(KEY_URL, url);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }

    public static void launch(Uri uri, Context context) {
        if (uri != null) {
            Intent intent = new Intent(context, PhotoViewerActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(KEY_URI, uri);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewer);
        handleParams();
        initView();
        setView();
    }

    private void handleParams() {
        Bundle params = getIntent().getExtras();
        if (params != null) {
            if (params.containsKey(KEY_BITMAP)) {
                mBitmap = params.getParcelable(KEY_BITMAP);
            }
            if (params.containsKey(KEY_URL)) {
                url = params.getString(KEY_URL);
            }
            if (params.containsKey(KEY_URI)) {
                uri = params.getParcelable(KEY_URI);
            }
        }
    }

    private void initView() {
        zoomageView = findViewById(R.id.zoom_img);
        container = findViewById(R.id.relative_container);
        backView = findViewById(R.id.back_btn);
    }

    private void setView() {
        if (mBitmap != null) {
            zoomageView.setImageBitmap(mBitmap);
        }
        if (!TextUtils.isEmpty(url)) {
            Glide.with(this).load(Uri.parse(url)).into(zoomageView);
        }
        if (uri != null) {
            Glide.with(this).load(uri).into(zoomageView);
        }
        zoomageView.setOnTouchListener(new View.OnTouchListener() {

            float lastY = -1;
            boolean moreThanOne = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
//                        Log.d(TAG, "onTouch: ACTION_DOWN");
                        if (event.getPointerCount() == 1) {
                            lastY = event.getY();
                        }
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
//                        Log.d(TAG, "onTouch: ACTION_POINTER_DOWN");
                        moreThanOne = true;
                        lastY = -1;
                        break;
                    case MotionEvent.ACTION_MOVE:
//                        Log.d(TAG, "onTouch: ACTION_MOVE");
                        if (!moreThanOne) {
                            if (event.getPointerCount() == 1 && lastY >= 0 && lastY < event.getY()) {
                                float alpha = (distance - (event.getY() - lastY)) / distance;
                                setAllViewAlpha(alpha);
                            } else {
                                lastY = -1;
                            }
                        } else {
                            setAllViewAlpha(1f);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
//                        Log.d(TAG, "onTouch: ACTION_UP");
                        if (!moreThanOne && event.getPointerCount() == 1 && lastY >= 0 && event.getY() - lastY > distance) {
                            PhotoViewerActivity.this.finish();
                        } else {
                            setAllViewAlpha(1f);
                        }
                        moreThanOne = false;
                        break;
//                    case MotionEvent.ACTION_POINTER_UP:
//                        if (event.getPointerCount() == 1) {
//                        }
//                        break;
                }
                return false;
            }
        });
        backView.setOnClickListener(this);
    }

    protected void setAllViewAlpha(float alpha) {
        zoomageView.setAlpha(alpha);
        container.setAlpha(alpha);
        backView.setAlpha(alpha);
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }
}
