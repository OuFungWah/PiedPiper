package com.crazywah.piedpiper.module.homepage.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.crazywah.piedpiper.R;
import com.crazywah.piedpiper.base.BaseActivity;
import com.crazywah.piedpiper.common.PiedToast;
import com.crazywah.piedpiper.module.homepage.adapter.SearchResultAdapter;
import com.crazywah.piedpiper.module.homepage.logic.SearchLogic;
import com.crazywah.piedpiper.widget.TitleBarNormalView;

public class SearchActivity extends BaseActivity implements TextWatcher {

    private SearchLogic logic;

    private TitleBarNormalView titleView;
    private EditText searchInputEt;
    private View noResultView;
    private TextView noResultTv;
    private RecyclerView resultRv;
    private SearchResultAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, SearchActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setView();
    }

    private void initView() {
        titleView = findViewById(R.id.search_title_view);
        noResultView = findViewById(R.id.search_result_no_result_view);
        noResultTv = findViewById(R.id.search_result_no_result_tv);
        searchInputEt = findViewById(R.id.search_input_et);
        resultRv = findViewById(R.id.search_result_rv);
        adapter = new SearchResultAdapter(logic.getUsers());
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    private void setView() {
        titleView.setTitle("搜索");
        titleView.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        noResultView.setVisibility(View.GONE);
        searchInputEt.addTextChangedListener(this);
        searchInputEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_SEARCH && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (!TextUtils.isEmpty(searchInputEt.getText().toString())) {
                        search();
                    } else {
                        PiedToast.showErrorShort("请先输入要搜索的ID");
                    }
                }
                return false;
            }
        });
        resultRv.setVisibility(View.GONE);
        resultRv.setAdapter(adapter);
        resultRv.setLayoutManager(linearLayoutManager);
    }

    private void search() {
        String inputStr = searchInputEt.getText().toString();
        if (!TextUtils.isEmpty(inputStr)) {
            logic.searchUser(inputStr);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_search;
    }

    @Override
    protected boolean onHandle(Message msg) {
        switch (msg.what) {
            case SearchLogic.MSG_SEARCH_SUCC:
                updateView();
                break;
            case SearchLogic.MSG_SEARCH_FAIL:
                noResult("---服务器跑路了---");
                break;
            default:
                break;
        }
        return false;
    }

    private void updateView() {
        if (logic.getUsers() != null && !logic.getUsers().isEmpty()) {
            resultExsit();
        } else {
            noResult("---搜索ID号：" + searchInputEt.getText().toString() + " 无结果---");
        }
    }

    private void resultExsit() {
        adapter.setUsers(logic.getUsers());
        adapter.notifyDataSetChanged();
        resultRv.setVisibility(View.VISIBLE);
        noResultView.setVisibility(View.GONE);
    }

    private void noResult(String tips) {
        resultRv.setVisibility(View.GONE);
        noResultView.setVisibility(View.VISIBLE);
        noResultTv.setText(tips);
    }

    @Override
    protected void prepareLogic() {
        logic = new SearchLogic(this, handler);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(TextUtils.isEmpty(s)){
            noResult("输入内容开始搜索");
        }else {
            search();
        }
    }
}
