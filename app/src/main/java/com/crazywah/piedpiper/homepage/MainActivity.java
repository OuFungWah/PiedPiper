package com.crazywah.piedpiper.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.base.BaseActivity;
import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.homepage.adapter.HomePagerAdapter;
import com.crazywah.piedpiper.widget.MainBottomView;
import com.crazywah.piedpiper.widget.TitleBarView;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, MainBottomView.OnItemCheckListener {

    private static final String[] TITLES = new String[]{
            "对话",
            "用户",
            "发现"
    };

    private TitleBarView titleBarView;
    private MainBottomView bottomView;
    private ViewPager mainViewPager;
    private HomePagerAdapter homePagerAdapter;
    private int currPageIndex = 0;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setView();
    }

    private void initView() {
        titleBarView = findViewById(R.id.main_titlebar);
        mainViewPager = findViewById(R.id.main_pager);
        bottomView = findViewById(R.id.main_bottom_view);
    }

    private void setView() {
        homePagerAdapter = new HomePagerAdapter(getSupportFragmentManager());
        mainViewPager.setAdapter(homePagerAdapter);
        mainViewPager.addOnPageChangeListener(this);
        titleBarView.setTitle(TITLES[currPageIndex]);
        bottomView.setItemCheckListener(this);
//        bottomView.showRedUnRead(0, "5");
//        bottomView.showGreenUnRead(1, "1");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean onHandle(Message msg) {
        return false;
    }

    @Override
    protected void prepareLogic() {

    }

    private long lastTime = 0;


    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastTime > 2000) {
            PiedToast.showShort("再按一次退出应用");
            lastTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectPage(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 底部按钮点击事件
     *
     * @param index
     */
    @Override
    public void onItemCheck(int index) {
        selectPage(index);
    }

    private void selectPage(int index) {
        currPageIndex = index;
        mainViewPager.setCurrentItem(index);
        titleBarView.setTitle(TITLES[index]);
        bottomView.check(index);
    }
}
