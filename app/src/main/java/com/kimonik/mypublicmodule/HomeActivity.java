package com.kimonik.mypublicmodule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.kimonik.utilsmodule.adapter.listview.CommonAdapter;
import com.kimonik.utilsmodule.adapter.listview.ViewHolder;
import com.kimonik.utilsmodule.base.BaseActivity;
import com.kimonik.utilsmodule.utils.LUtils;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.listView_act_home)
    ListView listViewActHome;
    private List<String> list;

    @Override
    public int getLayoutResId() {
        return R.layout.act_home;
    }

    @Override
    public void initDataFromIntent() {
        list=new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("这是第"+i+"个!");
        }

    }

    private CommonAdapter<String> getAdapter() {
        return new CommonAdapter<String>(this, R.layout.lv_item_act_home, list) {

            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                viewHolder.setText(R.id.tv_lv_item_act_home,item);
            }
        };
    }

    @Override
    public void onClick(View v) {

    }



    @Override
    public void initView() {
        LUtils.e(HomeActivity.class,"logflag-????--");

        listViewActHome.setAdapter(getAdapter());
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
    protected int setStatusBarColor() {
        return 0;
    }
}
