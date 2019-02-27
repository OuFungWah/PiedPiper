package com.crazywah.piedpiper;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.crazywah.piedpiper.base.BaseActivity;
import com.crazywah.piedpiper.common.PiedToast;

public class MainActivity extends BaseActivity {

    public static void launch(Context context){
        context.startActivity(new Intent(context,MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
