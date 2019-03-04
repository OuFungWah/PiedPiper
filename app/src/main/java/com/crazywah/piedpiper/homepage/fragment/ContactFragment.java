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

public class ContactFragment extends BaseFragment {

    public static ContactFragment newInstance(){
        return new ContactFragment();
    }

    @Override
    protected boolean onHandle(Message msg) {
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact,container,false);
        return view;
    }

}
