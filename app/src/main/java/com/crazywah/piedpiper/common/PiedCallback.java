package com.crazywah.piedpiper.common;

public interface PiedCallback<T> {

    void onSuccess(T object);

    boolean onFail(String message);

}
