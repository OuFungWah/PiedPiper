package com.crazywah.piedpiper.base;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.common.InnerSchemmer;

public class SchemerActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InnerSchemmer.getInstance().launch(this);
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_empty;
    }

    @Override
    protected boolean onHandle(Message msg) {
        return false;
    }

    @Override
    protected void prepareLogic() {

    }
}
