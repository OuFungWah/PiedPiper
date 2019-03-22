package com.crazywah.piedpiper.module.homepage.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.base.BaseFragment;

public class DiscoveryFragment extends BaseFragment {

    public static DiscoveryFragment newInstance() {
        return new DiscoveryFragment();
    }

    @Override
    protected void prepareLogic() {

    }

    @Override
    protected boolean onHandle(Message msg) {
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discovery, container, false);
        return view;
    }

}
