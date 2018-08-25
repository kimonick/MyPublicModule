package com.kimonik.mypublicmodule.activity;

import android.os.Bundle;
import android.view.View;

import com.kimonik.mypublicmodule.R;
import com.kimonik.mypublicmodule.ui.lottery.LotteryGroupView;
import com.kimonik.utilsmodule.base.BaseActivity;
import com.kimonik.utilsmodule.ui.MTopBarView;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * * ===============================================================
 * name:             LotteryViewTestActivity
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

public class LotteryViewTestActivity extends BaseActivity {
    @BindView(R.id.mtb_act_lottery)
    MTopBarView mtb;
    @BindView(R.id.lgv_act_lottery)
    LotteryGroupView lgvActLottery;

    @Override
    public int getLayoutResId() {
        return R.layout.act_lotteryview;
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


}
