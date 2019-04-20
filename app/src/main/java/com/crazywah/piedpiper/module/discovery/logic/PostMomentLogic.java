package com.crazywah.piedpiper.module.discovery.logic;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;

import com.crazywah.piedpiper.base.BaseLogic;
import com.crazywah.piedpiper.common.PiedCallback;
import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.common.RequestManager;
import com.crazywah.piedpiper.module.discovery.bean.ItemAddPicBean;
import com.crazywah.piedpiper.util.BitmapUtil;
import com.crazywah.piedpiper.util.DensityUtils;
import com.crazywah.piedpiper.util.EmojiUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PostMomentLogic extends BaseLogic {

    public static final int MSG_PIC_CHANGE = 0;
    public static final int MSG_REQUEST_PHOTO = 1;
    public static final int MSG_POST_SUCC = 2;
    public static final int MSG_POSY_FAIL = 3;

    public static final int IMAGE_SIZE = 9;

    private List<Object> bitmapList = new ArrayList<>();
    private List<Uri> sourceUriList = new ArrayList<>();
    private List<Bitmap> sourceBitmapList = new ArrayList<>();

    private boolean isPosting = false;

    private Gson parser = new Gson();

    public PostMomentLogic(Context context, Handler handler) {
        super(context, handler);
        combineList();
    }

    public void postMoment(String content, int visiableRange) {
        if(!isPosting) {
            isPosting = true;
            List<String> base64s = new ArrayList<>();
            for (Bitmap bitmap : sourceBitmapList) {
                base64s.add(BitmapUtil.bitmapToBase64(bitmap));
            }
            RequestManager.getInstance().postMoment(EmojiUtil.emojiConvert(content), visiableRange, base64s.isEmpty() ? null : parser.toJson(base64s), new PiedCallback<Void>() {
                @Override
                public void onSuccess(Void object) {
                    notifyUi(MSG_POST_SUCC);
                    isPosting = false;
                }

                @Override
                public boolean onFail(String message) {
                    notifyUi(MSG_POSY_FAIL);
                    isPosting = false;
                    return false;
                }
            });
        }else{
            PiedToast.showShort("发送中...");
        }
    }

    public List<Object> getBitmapList() {
        return bitmapList;
    }

    public void setBitmapList(List<Object> bitmapList) {
        this.bitmapList = bitmapList;
    }

    public void add(Bitmap bitmap) {
        if (bitmapList.size() < IMAGE_SIZE || bitmapList.size() == IMAGE_SIZE && bitmapList.get(bitmapList.size() - 1) instanceof ItemAddPicBean) {
            bitmapList.add(bitmapList.size() - 1 >= 0 ? bitmapList.size() - 1 : 0, bitmap);
            combineList();
            notifyUi(MSG_PIC_CHANGE);
        } else {
            PiedToast.showShort("只能添加9张图片");
        }
    }

    public void remove(int position) {
        bitmapList.remove(position);
        sourceBitmapList.remove(position);
        sourceUriList.remove(position);
        combineList();
        notifyUi(MSG_PIC_CHANGE);
    }

    private void combineList() {
        if (bitmapList != null && !bitmapList.isEmpty()) {
            if (!(bitmapList.get(bitmapList.size() - 1) instanceof ItemAddPicBean) && bitmapList.size() + 1 <= IMAGE_SIZE) {
                bitmapList.add(new ItemAddPicBean());
            }
            if (bitmapList.size() >= 10) {
                bitmapList.remove(bitmapList.size() - 1);
            }
        } else if (bitmapList != null && bitmapList.isEmpty()) {
            bitmapList.add(new ItemAddPicBean());
        }
    }

    public Bitmap scaleBitmapWithHeight(Bitmap bitmap, int dp) {
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        int expectHight = DensityUtils.dp2px(dp);
        int expectWidth = (int) (expectHight * (width / height));
        return Bitmap.createScaledBitmap(bitmap, expectWidth, expectHight, false);
    }

    public void addSource(Uri uri, Bitmap bitmap) {
        sourceUriList.add(uri);
        sourceBitmapList.add(bitmap);
        add(scaleBitmapWithHeight(bitmap, 220));
    }

    public List<Uri> getSourceUriList() {
        return sourceUriList;
    }

    public void setSourceUriList(List<Uri> sourceUriList) {
        this.sourceUriList = sourceUriList;
    }
}
