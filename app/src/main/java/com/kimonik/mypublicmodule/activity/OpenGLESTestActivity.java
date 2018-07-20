package com.kimonik.mypublicmodule.activity;

import android.os.Bundle;
import android.view.View;

import com.kimonik.mypublicmodule.R;
import com.kimonik.mypublicmodule.app.MApp;
import com.kimonik.mypublicmodule.opengles2test.MyGLSurfaceView;
import com.kimonik.utilsmodule.base.BaseActivity;
import com.kimonik.utilsmodule.ui.MTopBarView;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * * ===============================================================
 * name:             OpenGLESTestActivity
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2018/7/20
 * method:
 * <p>
 * <p>
 * description：
 * history：
 * *==================================================================
 */

public class OpenGLESTestActivity extends BaseActivity {
    @BindView(R.id.mtb_act_opengles)
    MTopBarView mtbActOpengles;
    @BindView(R.id.gl_act_opengles)
    MyGLSurfaceView glActOpengles;

    @Override
    public int getLayoutResId() {
        return R.layout.act_opengles;
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
//        setTopMargin(mtbActOpengles, MApp.STATUS_BAE_HEIGHT);
        setCloseLisenter(mtbActOpengles);
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
