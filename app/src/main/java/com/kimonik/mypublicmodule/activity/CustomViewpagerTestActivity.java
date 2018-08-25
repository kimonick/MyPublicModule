package com.kimonik.mypublicmodule.activity;

import android.view.View;

import com.kimonik.mypublicmodule.R;
import com.kimonik.utilsmodule.base.BaseActivity;
import com.lzy.okgo.model.Response;

/**
 * * ===============================================================
 * name:             CustomViewpagerTestActivity
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2018/8/24
 * method:
 * <p>
 * <p>
 * description：
 * history：
 * *==================================================================
 */

public class CustomViewpagerTestActivity extends BaseActivity {
    @Override
    public int getLayoutResId() {
        return R.layout.act_customviewpager;
    }

    @Override
    protected int setStatusBarColor() {
        return 0;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initDataFromIntent() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initDataFromInternet() {

    }

    @Override
    public void loadInternetDataToUi(Response<String> response) {

    }

    @Override
    public void loadInternetDataToUi() {

    }
}
