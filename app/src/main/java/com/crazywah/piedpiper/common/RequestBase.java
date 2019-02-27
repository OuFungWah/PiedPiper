package com.crazywah.piedpiper.common;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public abstract class RequestBase<T> extends Request<ResponseBase> {

    private static final String TAG = "RequestBase";

    private Gson mParser;
    private Response.Listener<T> listener;
    private Response.ErrorListener errorListener;

    public RequestBase(String url, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(Method.POST, url, errorListener);
        this.listener = listener;
        this.errorListener = errorListener;
    }

    public RequestBase(int method, String url, @Nullable Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mParser = new Gson();
    }

    @Override
    protected Response<ResponseBase> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        Log.d(TAG, "parseNetworkResponse: parsed = " + parsed);
        ResponseBase responseBase = mParser.fromJson(parsed, ResponseBase.class);
        responseBase.setData(mParser.fromJson(responseBase.getResult(), getTClass()));
        if (responseBase != null) {
            if (responseBase.getStatus() == null) {
                return Response.error(new VolleyError("服务器返回数据为空"));
            } else {
                if (responseBase.getStatus() == 0) {
                    return Response.error(new VolleyError("服务器返回数据为空"));
                } else {
                    return Response.success(responseBase, HttpHeaderParser.parseCacheHeaders(response));
                }
            }
        } else {
            return Response.error(new VolleyError("服务器返回数据为空"));
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put(ConstValue.HEADER_K_CONTENT_TYPE, ConstValue.HEADER_V_CONTENT_TYPE_JSON);
        return headers;
    }

    @Override
    protected Map<String, String> getParams() {
        return getParam();
    }

    @Override
    protected void deliverResponse(ResponseBase response) {
        if (response.getStatus() == ResponseStateCode.CODE_200) {
            if (listener != null) {
                listener.onResponse((T) response.getData());
            }
        } else {
            if (errorListener != null) {
                errorListener.onErrorResponse(new VolleyError(ResponseStateCode.getMessageByCode(response.getStatus())));
            }
        }

    }

    @Override
    public String getBodyContentType() {
        return "application/json; charset=utf-8";
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        try {
            return new Gson().toJson(getParams()).getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public abstract Map<String, String> getParam();

    public Class<T> getTClass() {
        Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return tClass;
    }

}
