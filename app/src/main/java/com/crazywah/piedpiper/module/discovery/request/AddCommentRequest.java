package com.crazywah.piedpiper.module.discovery.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.crazywah.piedpiper.bean.Comment;
import com.crazywah.piedpiper.common.RequestBase;
import com.crazywah.piedpiper.common.RequestUrl;
import com.google.gson.Gson;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddCommentRequest extends RequestBase<Comment> {

    private Comment comment;

    public AddCommentRequest(int momentId, String toId, String content, Response.Listener<Comment> listener, Response.ErrorListener errorListener) {
        super(RequestUrl.URL_ADD_COMMENT, listener, errorListener);
        comment = new Comment();
        comment.setMomentId(momentId);
        comment.setToId(toId);
        comment.setContent(content);
    }

    public AddCommentRequest(Comment comment, Response.Listener<Comment> listener, Response.ErrorListener errorListener) {
        super(RequestUrl.URL_ADD_COMMENT, listener, errorListener);
        this.comment = comment;
    }

    @Override
    public Map<String, String> getParam() {
        return null;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return new Gson().toJson(comment).getBytes();
    }
}
