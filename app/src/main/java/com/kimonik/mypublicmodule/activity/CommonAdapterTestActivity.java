package com.kimonik.mypublicmodule.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.kimonik.mypublicmodule.HomeActivity;
import com.kimonik.mypublicmodule.R;
import com.kimonik.mypublicmodule.app.MApp;
import com.kimonik.utilsmodule.adapter.listview.CommonAdapter;
import com.kimonik.utilsmodule.adapter.listview.ViewHolder;
import com.kimonik.utilsmodule.base.BaseActivity;
import com.kimonik.utilsmodule.ui.MTopBarView;
import com.kimonik.utilsmodule.utils.LUtils;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * * ===============================================================
 * name:             CommonAdapterTestActivity
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

public class CommonAdapterTestActivity extends BaseActivity {
    @BindView(R.id.mtb_act_commonadapter)
    MTopBarView mtbAct;
    @BindView(R.id.lv_act_commonadapter)
    ListView lvActCommonadapter;
    private List<String> list;

    @Override
    public int getLayoutResId() {
        return R.layout.act_commonadaptertest;
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
        list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("这是第" + i + "个!");
        }

    }

    @Override
    public void initView() {
        lvActCommonadapter.setAdapter(getAdapter());
//        setTopMargin(mtbAct, MApp.STATUS_BAE_HEIGHT);
        setCloseLisenter(mtbAct);
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

    private CommonAdapter<String> getAdapter() {
        return new CommonAdapter<String>(this, R.layout.lv_item_act_home, list) {

            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                viewHolder.setText(R.id.tv_lv_item_act_home, item);
            }
        };
    }

}
