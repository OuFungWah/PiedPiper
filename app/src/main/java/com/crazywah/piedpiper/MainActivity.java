package com.crazywah.piedpiper;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.crazywah.piedpiper.base.BaseActivity;
import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.widget.MainBottomView;
import com.crazywah.piedpiper.widget.UnReadView;

public class MainActivity extends BaseActivity {

    private MainBottomView bottomView;

    public static void launch(Context context){
        context.startActivity(new Intent(context,MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setView();
    }

    private void initView(){
        bottomView = findViewById(R.id.main_bottom_view);
    }

    private void setView() {
        bottomView.showRedUnRead(0,"5");
        bottomView.showGreenUnRead(1,"1");
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
        if(System.currentTimeMillis()-lastTime>2000){
            PiedToast.showShort("再按一次退出应用");
            lastTime = System.currentTimeMillis();
        }else{
            super.onBackPressed();
        }
    }
}
