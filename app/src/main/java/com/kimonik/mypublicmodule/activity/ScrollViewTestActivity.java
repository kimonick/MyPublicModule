package com.kimonik.mypublicmodule.activity;

import android.os.Bundle;
import android.view.View;

import com.kimonik.mypublicmodule.R;
import com.kimonik.mypublicmodule.ui.CustomScrollView;
import com.kimonik.utilsmodule.base.BaseActivity;
import com.kimonik.utilsmodule.ui.MTopBarView;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * * ===============================================================
 * name:             ScrollViewTestActivity
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

public class ScrollViewTestActivity extends BaseActivity {
    @BindView(R.id.mtb_act_scrollview)
    MTopBarView mtb;
    @BindView(R.id.csv_act_scrollview)
    CustomScrollView csvActScrollview;

    @Override
    public int getLayoutResId() {
        return R.layout.act_scrollview;
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
        setCloseLisenter(mtb);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
