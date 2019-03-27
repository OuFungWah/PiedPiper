package com.crazywah.piedpiper.database.service;

import android.content.Context;
import android.util.Log;

import com.crazywah.piedpiper.application.PiedPiperApplication;
import com.crazywah.piedpiper.bean.User;
import com.crazywah.piedpiper.database.helper.PiedDBHelper;
import com.google.gson.Gson;

import java.util.List;

public class UserDBService extends DBBaseService<User> {

    private static final String TAG = "UserDBService";

    public UserDBService(Context context) {
        super(context);
    }

    @Override
    protected String getTable() {
        return PiedDBHelper.TABLE_NAME_USER;
    }

    public void addUsers(List<User> users){
        for (User user : users) {
            addOrUpdateUser(user);
        }
    }

    public boolean addOrUpdateUser(User user) {
        boolean flag = false;
        List<User> selectResults = select(user, "accountId = ?", null, null, null);
//        Log.d(TAG, "addOrUpdateUser: user = "+new Gson().toJson(selectResults));
        if (selectResults == null || selectResults.isEmpty()) {
            flag = add(user);
//            Log.d(TAG, "addOrUpdateUser: 插入数据 " + user.getAccountId() + " " + flag);
        } else {
//            Log.d(TAG, "addOrUpdateUser: 查询到了 accountId = " + user.getAccountId());
            flag = update(user, "accountId = ?");
//            Log.d(TAG, "addOrUpdateUser: 修改数据 " + user.getAccountId() + " " + flag);
        }
        return flag;
    }

    public boolean addUser(User user) {
        return add(user);
    }

    public boolean updateUser(User user) {
        return update(user, "accountId = ?");
    }

    public boolean delectUser(User user) {
        return delete(user, "accountId = ?");
    }

    public User selectUser(String accountId) {
        User params = new User();
        params.setAccountId(accountId);
        List<User> selectResult = select(params, "accountId = ?", null, null, null);
        Log.d(TAG, "selectUser: user " + accountId + " = " + new Gson().toJson(selectResult));
        if (selectResult != null && !selectResult.isEmpty()) {
            Log.d(TAG, "selectUser: 查询 accountId = " + accountId + "成功");
            return selectResult.get(0);
        } else {
            Log.d(TAG, "selectUser: 查询 accountId = " + accountId + "失败");
            return null;
        }
    }

    public static UserDBService newInstance() {
        return new UserDBService(PiedPiperApplication.getInstance());
    }

}
