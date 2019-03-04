package com.crazywah.piedpiper.homepage.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.base.BaseFragment;

public class ChatFragment extends BaseFragment {

    public static ChatFragment newInstance(){
        return new ChatFragment();
    }

    @Override
    protected boolean onHandle(Message msg) {
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat,container,false);
        return view;
    }

}
