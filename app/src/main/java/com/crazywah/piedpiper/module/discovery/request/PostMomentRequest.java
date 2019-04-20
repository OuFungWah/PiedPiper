package com.crazywah.piedpiper.module.discovery.request;

import com.android.volley.Response;
import com.crazywah.piedpiper.common.RequestBase;
import com.crazywah.piedpiper.common.RequestUrl;

import java.util.HashMap;
import java.util.Map;

public class PostMomentRequest extends RequestBase<Void> {

    private String postContent;
    private int visiableRange;
    private String photoList;

    public PostMomentRequest(String postContent, int visiableRange, String photoList, Response.Listener<Void> listener, Response.ErrorListener errorListener) {
        super(RequestUrl.URL_POST_MOMENT, listener, errorListener);
        this.postContent = postContent;
        this.visiableRange = visiableRange;
        this.photoList = photoList;
    }

    @Override
    public Map<String, String> getParam() {
        Map<String, String> params = new HashMap<>();
        params.put("postContent", postContent);
        params.put("visiableRange", visiableRange + "");
        params.put("photoList", photoList);
        return params;
    }
}
