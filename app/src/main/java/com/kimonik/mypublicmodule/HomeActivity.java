package com.kimonik.mypublicmodule;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.kimonik.mypublicmodule.activity.CommonAdapterTestActivity;
import com.kimonik.mypublicmodule.activity.OkGoTestActivity;
import com.kimonik.mypublicmodule.activity.OpenGLESTestActivity;
import com.kimonik.mypublicmodule.activity.VideoViewTestActivity;
import com.kimonik.mypublicmodule.ui.PaoPaoView;
import com.kimonik.utilsmodule.base.BaseActivity;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @BindView(R.id.bt_home_001)
    Button btHome001;
    @BindView(R.id.bt_home_002)
    Button btHome002;
    @BindView(R.id.bt_home_003)
    Button btHome003;
    @BindView(R.id.bt_home_004)
    Button btHome004;

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();


    @Override
    public int getLayoutResId() {
        return R.layout.act_home;
    }

    @Override
    public void initDataFromIntent() {


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //opengles测试
            case R.id.bt_home_001:
                openActivity(OpenGLESTestActivity.class);
                break;
            //okgo测试
            case R.id.bt_home_002:
                openActivity(OkGoTestActivity.class);
                break;
            //listview万能适配器测试
            case R.id.bt_home_003:
                openActivity(CommonAdapterTestActivity.class);
                break;
            //videoview测试
            case R.id.bt_home_004:
                openActivity(VideoViewTestActivity.class);
                break;
//                 case R.id.bt_home_005:break;
//                 case R.id.bt_home_006:break;
        }
    }


    @Override
    public void initView() {
        PaoPaoView paoPaoView=new PaoPaoView(this);
        FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        paoPaoView.setLayoutParams(params);
        ((FrameLayout)(getWindow().getDecorView())).addView(paoPaoView);

    }

    @Override
    public void initListener() {
        btHome001.setOnClickListener(this);
        btHome002.setOnClickListener(this);
        btHome003.setOnClickListener(this);
        btHome004.setOnClickListener(this);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 2018！尚硅谷全套视频200G，免费下载，自学一样拿1万+月薪！
     尚硅谷Vue、SpringBoot、SpringCloud倾心之课！
     【Java基础,Java9,JavaWeb】www.atguigu.com/download.shtml
     【Linux,Oracle,JDBC,MySQL高级】http://url.cn/4ERsXa0
     【HTML5,CSS3,JS,JQuery,AJAX】http://url.cn/4EHmHKZ
     【SSH,SSM,SpringMVC,SpringBoot】http://url.cn/4Ej6ePw
     【Redis,Shiro,MyBatis】http://url.cn/4EU3FCV
     【Android核心,影音,商城项目】http://url.cn/4ENsQjH
     【Node,mongoDB,Angular,React】http://url.cn/5ndEJKm
     自学一样拿1万+月薪，技术群：715446567

     */
}
