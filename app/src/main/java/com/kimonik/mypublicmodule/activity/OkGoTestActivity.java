package com.kimonik.mypublicmodule.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kimonik.mypublicmodule.R;
import com.kimonik.utilsmodule.base.BaseActivity;
import com.kimonik.utilsmodule.ui.MTopBarView;
import com.kimonik.utilsmodule.utils.HttpUtils;
import com.kimonik.utilsmodule.utils.LUtils;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import butterknife.BindView;

/**
 * * ===============================================================
 * name:             OkGoTestActivity
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

public class OkGoTestActivity extends BaseActivity {
    @BindView(R.id.mtb_act_okgo)
    MTopBarView mtb;
    @BindView(R.id.tv_act_okgotest_content)
    TextView tvtestContent;
    @BindView(R.id.et_act_okgotest)
    EditText ettest;
    @BindView(R.id.et_act_okgotest_params)
    EditText ettestParams;
    @BindView(R.id.tv_act_okgotest_get)
    TextView tvtestGet;
    @BindView(R.id.tv_act_okgotest_post)
    TextView tvtestPost;
    @BindView(R.id.tv_act_okgotest_file)
    TextView tvtestFile;
    @BindView(R.id.tv_act_okgotest_bitmap)
    TextView tvtestBitmap;
    @BindView(R.id.tv_act_okgotest_sendrequest)
    TextView tvtestSendrequest;
    /**
     * 请求类型
     */
    private int requestType = 1;
    /**
     * 请求参数
     */
    private TreeMap<String, String> map;
    /**
     * 请求的url
     */
    private String url;
    /**请求类型view集合*/
    private  TextView[] textViews;

    @Override
    public int getLayoutResId() {
        return R.layout.act_okgotest;
    }

    @Override
    protected int setStatusBarColor() {
        return 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_act_okgotest_get:
                requestType = 1;
                setTvBackground(0);
                break;
            case R.id.tv_act_okgotest_post:
                requestType = 2;
                setTvBackground(1);
                break;
            case R.id.tv_act_okgotest_file:
                requestType = 3;
                setTvBackground(2);
                break;
            case R.id.tv_act_okgotest_bitmap:
                requestType = 4;
                setTvBackground(3);
                break;
            case R.id.tv_act_okgotest_sendrequest:
                if (check()) {
                    tvtestContent.setText("");
                    request();
                }
                break;
//            case R.id.: break;
        }
    }

    private void setTvBackground(int i) {
        for (int j = 0; j < textViews.length; j++) {
            if (j==i){
                textViews[j].setBackgroundColor(getColorRes(R.color.colorQianLan));
            }else {
                textViews[j].setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    private boolean check() {
        url = ettest.getText().toString();
        if (url.equals("")) {
            showToast(R.string.qingshuruurl);
            return false;
        }
        if (!ettestParams.getText().toString().equals("")) {
            String[] params = ettestParams.getText().toString().trim().split(",");
            if (params.length % 2 == 0) {
                if (map == null) {
                    map = new TreeMap<>();
                } else {
                    map.clear();
                }
                for (int i = 0; i < params.length; i++) {
                    map.put(params[i], params[i + 1]);
                    i++;
                }
            } else {
                showToast(R.string.qingzhengqueshurucanshu);
                return false;
            }

        }

        return true;
    }

    @Override
    public void initDataFromIntent() {
        textViews=new TextView[4];
        textViews[0]=tvtestGet;
        textViews[1]=tvtestPost;
        textViews[2]=tvtestFile;
        textViews[3]=tvtestBitmap;
    }

    @Override
    public void initView() {
//        setTopMargin(mtb, MApp.STATUS_BAE_HEIGHT);
        setCloseLisenter(mtb);
    }

    @Override
    public void initListener() {
        tvtestFile.setOnClickListener(this);
        tvtestGet.setOnClickListener(this);
        tvtestPost.setOnClickListener(this);
        tvtestBitmap.setOnClickListener(this);
        tvtestSendrequest.setOnClickListener(this);

    }

    @Override
    public void initDataFromInternet() {



    }

    private void request(){
        switch (requestType) {
            case 1:
                get();
                break;
            case 2:
                post();
                break;
            case 3:
                break;
            case 4:
                break;
        }
    }

    private void post() {
        //http://mob.tudoujf.com/phone/member/memberCenter
        //xmdy,e24d09a7debb63713b774acc13140b24&,diyou,8uSm4azIYbh77RR7v949%2Bd4%2FNZoYlvT7pRUKes6Kxv8GeYneecqWJCCZG%2F2OuOHM%20
        //https://mob.tudoujf.com/phone/loan/list
        //xmdy,ea9fa4302a497a4f6c437ca7397f8f4a&,diyou,6PULTv3AsrmIBWaqLvjsoLxVgjQoywH0TeVrXXt5ImLXCUAhLVA1zkglw2pRbHYxYa1qme7gXQTm%20AlmibWsqdX0fRQrG5V4eXkyBaPxaP84GeYneecqWJCCZG%2F2OuOHM%20
        HttpUtils.getInstance(this.getApplicationContext()).commonPOST(url, map, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                String result=response.body();
                tvtestContent.setText(result);
                LUtils.e(OkGoTestActivity.class,"logflag-???-1-"+result);
            }

            @Override
            public void onError(Response<String> response) {
                String result=response.getException().getMessage();
                showToast(result);
                LUtils.e(OkGoTestActivity.class,"logflag-???-2-"+result);
                super.onError(response);

            }

            @Override
            public void onFinish() {
                showToast(R.string.qingqiujieshu);
                super.onFinish();
            }
        });
    }

    private Set<String> set;
    private void initSet(){
        set=new HashSet<>();
        for (int i = 0; i < 10; i++) {
            set.add(""+i);
        }
    }
    private void get() {
        HttpUtils.getInstance(this.getApplicationContext()).GET(url,map,
                new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        tvtestContent.setText(response.body());

                    }

                    @Override
                    public void onError(Response<String> response) {
                        String result=response.getException().getMessage();
                        showToast(result);
                        super.onError(response);
                    }

                    @Override
                    public void onFinish() {
                        showToast(R.string.qingqiujieshu);
                        super.onFinish();
                    }
                });
    }

    @Override
    public void loadInternetDataToUi(Response<String> response) {

    }

    @Override
    public void loadInternetDataToUi() {

    }


}
