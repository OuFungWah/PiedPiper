package com.crazywah.piedpiper.common;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.crazywah.piedpiper.bean.Comment;
import com.crazywah.piedpiper.bean.Moment;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.module.chatroom.request.GetUsersByRelationRequest;
import com.crazywah.piedpiper.module.chatroom.request.PicUpLoadRequest;
import com.crazywah.piedpiper.module.chatroom.request.UserInfoRequest;
import com.crazywah.piedpiper.module.contact.request.FriendListRequest;
import com.crazywah.piedpiper.module.contact.request.HandleFriendRequest;
import com.crazywah.piedpiper.module.discovery.bean.MomentDetail;
import com.crazywah.piedpiper.module.discovery.request.AddCommentRequest;
import com.crazywah.piedpiper.module.discovery.request.DeleteMomentRequest;
import com.crazywah.piedpiper.module.discovery.request.DislikeRequest;
import com.crazywah.piedpiper.module.discovery.request.GetMomentDetailRequest;
import com.crazywah.piedpiper.module.discovery.request.GetMomentRequest;
import com.crazywah.piedpiper.module.discovery.request.LikeRequest;
import com.crazywah.piedpiper.module.discovery.request.PostMomentRequest;
import com.crazywah.piedpiper.module.homepage.request.GetAllInfoRequest;
import com.crazywah.piedpiper.module.homepage.request.GetStrangersRequest;
import com.crazywah.piedpiper.module.login.request.LoginRequest;
import com.crazywah.piedpiper.module.register.request.RegisterRequest;
import com.crazywah.piedpiper.module.user.request.AddFriendRequest;
import com.crazywah.piedpiper.module.user.request.UpdateAddressRequest;
import com.crazywah.piedpiper.module.user.request.UpdateBirthdayRequest;
import com.crazywah.piedpiper.module.user.request.UpdateEmailRequest;
import com.crazywah.piedpiper.module.user.request.UpdateGenderRequest;
import com.crazywah.piedpiper.module.user.request.UpdateMobileRequest;
import com.crazywah.piedpiper.module.user.request.UpdateNicknameRequest;
import com.crazywah.piedpiper.module.user.request.UpdatePasswordRequest;
import com.crazywah.piedpiper.module.user.request.UpdateSignatureRequest;
import com.crazywah.piedpiper.module.user.request.UploadAvatarRequest;

import java.util.Date;
import java.util.List;

public class RequestManager {

    private RequestQueue queue;

    public void init(Context context) {
        queue = Volley.newRequestQueue(context);
        queue.start();
    }

    public void login(String accountId, String password, final PiedCallback<User> callback) {
        LoginRequest loginRequest = new LoginRequest(accountId, password, new Response.Listener<User>() {
            @Override
            public void onResponse(User user) {
                if (user != null) {
                    callback.onSuccess(user);
                } else {
                    callback.onFail("返回数据为空");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(loginRequest);
    }

    public void register(String accountId, String nickname, String password, final PiedCallback<User> callback) {
        RegisterRequest registerRequest = new RegisterRequest(accountId, nickname, password, new Response.Listener<User>() {
            @Override
            public void onResponse(User response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(registerRequest);
    }

    public void getUserInfo(String targetId, final PiedCallback<User> callback) {
        UserInfoRequest userInfoRequest = new UserInfoRequest(targetId, new Response.Listener<User>() {
            @Override
            public void onResponse(User response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(userInfoRequest);
    }

    public void getUserInfo(final PiedCallback<User> callback) {
        GetAllInfoRequest userInfoRequest = new GetAllInfoRequest(new Response.Listener<User>() {
            @Override
            public void onResponse(User response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(userInfoRequest);
    }

    public void uploadPic(String base64, final PiedCallback callback) {
        PicUpLoadRequest userInfoRequest = new PicUpLoadRequest(base64, new Response.Listener<Object>() {
            @Override
            public void onResponse(Object response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(userInfoRequest);
    }

    public void uploadAvatar(String base64, final PiedCallback callback) {
        UploadAvatarRequest userInfoRequest = new UploadAvatarRequest(base64, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(userInfoRequest);
    }

    public void getFriends(final PiedCallback<List<User>> callback) {
        GetUsersByRelationRequest request = new GetUsersByRelationRequest(1, new Response.Listener<List<User>>() {
            @Override
            public void onResponse(List<User> response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(request);
    }

    public void updateNickname(String value, final PiedCallback<Void> callback) {
        UpdateNicknameRequest request = new UpdateNicknameRequest(value, new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(request);
    }

    public void updatePassword(String value, final PiedCallback<Void> callback) {
        UpdatePasswordRequest request = new UpdatePasswordRequest(value, new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(request);
    }

    public void updateGender(String value, final PiedCallback<Void> callback) {
        UpdateGenderRequest request = new UpdateGenderRequest(value, new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(request);
    }

    public void updateMobile(String value, final PiedCallback<Void> callback) {
        UpdateMobileRequest request = new UpdateMobileRequest(value, new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(request);
    }

    public void updateAddress(String value, final PiedCallback<Void> callback) {
        UpdateAddressRequest request = new UpdateAddressRequest(value, new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(request);
    }

    public void updateEmail(String value, final PiedCallback<Void> callback) {
        UpdateEmailRequest request = new UpdateEmailRequest(value, new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(request);
    }

    public void updateSignature(String value, final PiedCallback<Void> callback) {
        UpdateSignatureRequest request = new UpdateSignatureRequest(value, new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(request);
    }

    public void updateBirthday(Date date, final PiedCallback<Void> callback) {
        UpdateBirthdayRequest request = new UpdateBirthdayRequest(date, new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(request);
    }

    public void sendFriendRequest(String originId, String targetId, String requestMessage, final PiedCallback<Void> callback) {
        AddFriendRequest request = new AddFriendRequest(originId, targetId, requestMessage, new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(request);
    }

    public void handleFriendRequest(String toId, int result, String attach, final PiedCallback<Void> callback) {
        HandleFriendRequest request = new HandleFriendRequest(toId, result, attach, new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(request);
    }

    public void getAllFriendRequests(final PiedCallback<List<User>> callback) {
        FriendListRequest request = new FriendListRequest(new Response.Listener<List<User>>() {
            @Override
            public void onResponse(List<User> response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(request);
    }

    public void getAllMoments(int limit, int offset, final PiedCallback<List<Moment>> callback) {
        GetMomentRequest request = new GetMomentRequest(limit, offset, new Response.Listener<List<Moment>>() {
            @Override
            public void onResponse(List<Moment> response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(request);
    }

    public void searchStrangers(String id, final PiedCallback<List<User>> callback) {
        GetStrangersRequest request = new GetStrangersRequest(id, new Response.Listener<List<User>>() {
            @Override
            public void onResponse(List<User> response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(request);
    }

    public void postMoment(String postContent, int visiableRange, String photoList, final PiedCallback<Void> callback) {
        PostMomentRequest request = new PostMomentRequest(postContent, visiableRange, photoList, new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(request);
    }

    public void deleteMoment(final int momentId, final PiedCallback<Integer> callback) {
        DeleteMomentRequest request = new DeleteMomentRequest(momentId, new Response.Listener<Integer>() {
            @Override
            public void onResponse(Integer response) {
                callback.onSuccess(momentId);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(request);
    }

    public void getMomentDetail(final int momentId, final PiedCallback<MomentDetail> callback) {
        GetMomentDetailRequest request = new GetMomentDetailRequest(momentId, new Response.Listener<MomentDetail>() {
            @Override
            public void onResponse(MomentDetail response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(request);
    }

    public void addComment(int momentId, String toId, String content, final PiedCallback<Comment> callback) {
        AddCommentRequest request = new AddCommentRequest(momentId, toId, content, new Response.Listener<Comment>() {
            @Override
            public void onResponse(Comment response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(request);
    }

    public void likeMoment(int momentId, final PiedCallback<Void> callback) {
        LikeRequest request = new LikeRequest(momentId, new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(request);
    }

    public void dislikeMoment(int momentId, final PiedCallback<Void> callback) {
        DislikeRequest request = new DislikeRequest(momentId, new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail(error.getMessage());
            }
        });
        addRequest(request);
    }

    public void addRequest(Request request) {
        queue.add(request);
    }

    public static RequestManager getInstance() {
        return Holder.instance;
    }

    private static class Holder {
        public static RequestManager instance = new RequestManager();
    }

}
