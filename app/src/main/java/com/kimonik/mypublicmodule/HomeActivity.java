package com.kimonik.mypublicmodule;

import android.arch.lifecycle.MethodCallsLogger;
import android.graphics.Matrix;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.kimonik.mypublicmodule.activity.CommonAdapterTestActivity;
import com.kimonik.mypublicmodule.activity.CustomViewpagerTestActivity;
import com.kimonik.mypublicmodule.activity.LotteryViewTestActivity;
import com.kimonik.mypublicmodule.activity.OkGoTestActivity;
import com.kimonik.mypublicmodule.activity.OpenGLESTestActivity;
import com.kimonik.mypublicmodule.activity.ScrollViewTestActivity;
import com.kimonik.mypublicmodule.activity.VideoViewTestActivity;
import com.kimonik.mypublicmodule.ui.LoadingView;
import com.kimonik.mypublicmodule.ui.LotteryView;
import com.kimonik.mypublicmodule.ui.PathTestView;
import com.kimonik.mypublicmodule.ui.lottery.FlickerView;
import com.kimonik.mypublicmodule.ui.lottery.LotteryGroupView;
import com.kimonik.utilsmodule.androidutil.util.RomUtils;
import com.kimonik.utilsmodule.base.BaseActivity;
import com.kimonik.utilsmodule.utils.LUtils;
import com.kimonik.utilsmodule.utils.SoundUtils;
import com.lzy.okgo.model.Response;

import butterknife.BindView;

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
    @BindView(R.id.bt_home_005)
    Button btHome005;
    @BindView(R.id.bt_home_006)
    Button btHome006;
    @BindView(R.id.bt_home_007)
    Button btHome007;
    @BindView(R.id.bt_home_008)
    Button btHome008;
    @BindView(R.id.bt_home_009)
    Button btHome009;

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
        ceshi("这是原来的字符串网贷之家");

    }

    public void ceshi(String s) {
        Log.e("TAG", "method2: " + s);
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
            case R.id.bt_home_005:
                SoundUtils.playSound(this, R.raw.after_upload_voice);
                break;
            case R.id.bt_home_006:
                openActivity(LotteryViewTestActivity.class);
                break;
            case R.id.bt_home_007:
                openActivity(ScrollViewTestActivity.class);
                break;
            case R.id.bt_home_008:
                openActivity(CustomViewpagerTestActivity.class);
                break;
            case R.id.bt_home_009:
                break;
        }
    }


    @Override
    public void initView() {

//        Log.e("TAG", "class切点插入:onCreate开始" );
//        PaoPaoView paoPaoView=new PaoPaoView(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
//        paoPaoView.setLayoutParams(params);
//        ((FrameLayout)(getWindow().getDecorView())).addView(paoPaoView);
////        Log.e("TAG", "class切点插入:onCreate结束" );
//        WaveView waveView=new WaveView(this);
////        FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(ViewGroup.LayoutParams
/// .MATCH_PARENT,
////                ViewGroup.LayoutParams.MATCH_PARENT);
//        waveView.setLayoutParams(params);
//        ((FrameLayout) (getWindow().getDecorView())).addView(waveView);
//        ContortView contortView=new ContortView(this);
//        contortView.setLayoutParams(params);
//        LoadingView loadingView = new LoadingView(this);
//        ((FrameLayout) (getWindow().getDecorView())).addView(loadingView);
//        PathTestView pathTestView = new PathTestView(this);
//        ((FrameLayout) (getWindow().getDecorView())).addView(pathTestView);
//        FlickerView lotteryView=new FlickerView(this);
//        LotteryGroupView lotteryView=new LotteryGroupView(this);
//        lotteryView.setLayoutParams(params);
//        ((FrameLayout) (getWindow().getDecorView())).addView(lotteryView);
//        ((FrameLayout) (getWindow().getDecorView())).addView(contortView);
        LUtils.e(HomeActivity.class, "logflag-系统类型--" + RomUtils.getRom().toString());
//        Matrix matrix = new Matrix();
//        float[] f = new float[9];
//        matrix.getValues(f);
//        for (int i = 0; i < 9; i++) {
//            LUtils.e(HomeActivity.class, "logflag-matrix矩阵--" + f[i]);
//
//        }
        String s = getS();
        String s1 = s.replace("\n", "").replace(" ", "");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < s1.length(); i++) {
            builder.append(s1.charAt(i));
            if (i % 48 == 0) {
                Log.d("tag12356", builder.toString());
                builder = new StringBuilder();
            } else if (i == s1.length() - 1) {
                Log.d("tag12356", builder.toString());

            }
        }


    }

    private String getS() {
        return "";
    }

    @Override
    public void initListener() {
        btHome001.setOnClickListener(this);
        btHome002.setOnClickListener(this);
        btHome003.setOnClickListener(this);
        btHome004.setOnClickListener(this);
        btHome005.setOnClickListener(this);
        btHome006.setOnClickListener(this);
        btHome007.setOnClickListener(this);
        btHome008.setOnClickListener(this);
        btHome009.setOnClickListener(this);
//        btHome010.setOnClickListener(this);
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
