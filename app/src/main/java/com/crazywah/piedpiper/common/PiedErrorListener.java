package com.crazywah.piedpiper.common;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public class PiedErrorListener implements Response.ErrorListener {

    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
        PiedToast.showShort(error.getMessage());
    }

}
