package com.crazywah.piedpiper.module.homepage.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.crazywah.piedpiper.module.chat.fragment.ChatFragment;
import com.crazywah.piedpiper.module.contact.fragment.ContactFragment;
import com.crazywah.piedpiper.module.homepage.fragment.DiscoveryFragment;

public class HomePagerAdapter extends FragmentPagerAdapter {

    public static final int INDEX_CHAT = 0;
    public static final int INDEX_CONTACT = 1;
    public static final int INDEX_DISCOVERY = 2;

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case INDEX_CHAT:
                return ChatFragment.newInstance();
            case INDEX_CONTACT:
                return ContactFragment.newInstance();
            case INDEX_DISCOVERY:
                return DiscoveryFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

}
