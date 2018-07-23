package com.kimonik.utilsmodule.base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.kimonik.utilsmodule.R;
import com.kimonik.utilsmodule.ui.MTopBarView;
import com.kimonik.utilsmodule.utils.DialogUtils;
import com.kimonik.utilsmodule.utils.ToastUtils;
import com.lzy.imagepicker.view.SystemBarTintManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * * ================================================
 * name:            BaseActivity
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/7
 * description：     activity基类
 * history：
 * ===================================================
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseMethod, View.OnClickListener {

    /**
     * app进入前台后为false,返回后为true
     */
    private boolean isActive = true;

    /**
     * 本activity内的okgo的请求标识
     */
    private String okgoCancelTag;
    /**
     * 下拉刷新,上拉加载布局
     */
    private SmartRefreshLayout srl;

    /**
     * 设置加载刷新布局
     */
    @SuppressWarnings("unused")
    public void setSrl(SmartRefreshLayout srl) {
        this.srl = srl;
    }

    /**
     * 结束SmartRefreshLayout加载刷新
     */
    public void finishRL() {
        if (srl != null) {
            if (srl.isRefreshing()) {
                srl.finishRefresh();
            } else if (srl.isLoading()) {
                srl.finishLoadmore();
            }
        }
    }

    /**
     * 获得本类内okgo的请求tag
     */
    @SuppressWarnings("unused")
    public String getOkgoCancelTag() {
        return okgoCancelTag;
    }

    /**
     * 设置本类内okgo的请求tag
     */
    @SuppressWarnings("unused")
    public void setOkgoCancelTag(String okgoCancelTag) {
        this.okgoCancelTag = okgoCancelTag;
    }

    //-----------------------------------------联网请求计时---------------------------------------------------------
//    /**
//     * dialog是否已显示
//     */
//    private boolean isProgressing = false;
//    /**
//     * 弱引用handler,防止内存泄露
//     */
//    private MyHandler handler = new MyHandler(this);
//    /**
//     * dialog最长的显示时间,超时将自动消失
//     */
//    private long sleepTime = 15000;

//    @SuppressLint("HandlerLeak")
//    private class MyHandler extends Handler {
//        // 弱引用 ，防止内存泄露
//        private WeakReference<AppCompatActivity> weakReference;
//
//        private MyHandler(AppCompatActivity handlerMemoryActivity) {
//            weakReference = new WeakReference<>(handlerMemoryActivity);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//
//            AppCompatActivity handlerMemoryActivity = weakReference.get();
//            if (handlerMemoryActivity != null && isProgressing && msg.what == 1) {
//                OkGo.getInstance().cancelAll();//取消所有的网络请求
//                dismissPDialog();
//            } else {
//                dismissPDialog();
//            }
//        }
//    }
    //------------------------------------------联网请求计时--------------------------------------------------------

    /**
     * okgo stringcallback()
     */
    private StringCallback stringCallback;

    /**
     * 返回okgo的StringCallback实例
     */
    @SuppressWarnings("unused")
    public StringCallback getStringCallback() {
        if (stringCallback == null) {
            stringCallback = new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
//                    dismissPDialog();
//                    finishRL();
                    loadInternetDataToUi(response);
                }

                /**网络请求出错,某些情况下由于缓存原因,可能不会被回调,例如304等*/
                @Override
                public void onError(Response<String> response) {
                    super.onError(response);
                    ToastUtils.showToast(BaseActivity.this, R.string.wangluobutaigeiliyou);
                }

                /**每次网络请求结束后都会被回调,无论网络请求时成功还是失败*/
                @Override
                public void onFinish() {
                    dismissPDialog();
                    finishRL();
                    super.onFinish();
                }
            };

        }
        return stringCallback;
    }

    /**
     * 加载进度显示dialog
     */
    private AlertDialog bDialog;

    /**
     * 显示加载进度dialog
     */
    @SuppressWarnings("unused")
    public void showPDialog() {
//        timeThread();//开启线程控制加载进度显示,okgo的onfinish()方法代替
//        isProgressing = true;
        if (bDialog == null && !isFinishing()) {
            bDialog = DialogUtils.showProgreessDialog(this, getResources().getString(R.string.zaicidianjijinagtuichugaiyemian));
        } else if (!isFinishing()) {
            bDialog.show();
        }
    }

    /**
     * 取消加载进度dialog
     */
    public void dismissPDialog() {
//        isProgressing = false;
        if (!(isFinishing() || isDestroyed())) {
            if (bDialog != null && bDialog.isShowing()) {
                bDialog.dismiss();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initSystemBarTint();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏显示
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);//禁止截屏
//        getSupportActionBar().hide();//代码隐藏actionbar

        if (isHideStatusBar()) {
            Window window = getWindow();
            //隐藏状态栏
            //定义全屏参数
            int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
            //设置当前窗体为全屏显示
            window.setFlags(flag, flag);
        }
        //设置透明图片防止背景过度绘制
//        getWindow().getDecorView().setBackground(new ColorDrawable(Color.parseColor("#FCFCFC")));

        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        initDataFromIntent();
        initView();
        initListener();
        initDataFromInternet();
    }

    /**
     * 设置是否隐藏状态栏
     */
    public boolean isHideStatusBar() {
        return false;
    }

    /**
     * 设置加载的布局
     */
    public abstract int getLayoutResId();


    /**
     * 设置状态栏颜色改变状态栏颜色
     */
    protected abstract int setStatusBarColor();

    /**
     * 子类可以重写决定是否使用透明状态栏
     */
    protected boolean translucentStatusBar() {
        return true;
    }

    /**
     * 设置顶部margin,
     *
     * @param view            最顶部的view
     * @param statusBarHeight 状态栏高度
     */
    public void setTopMargin(View view, int statusBarHeight) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.setMargins(0, statusBarHeight, 0, 0);
        view.setLayoutParams(params);
    }

    /**
     * 设置状态栏颜色
     */
    protected void initSystemBarTint() {
        if (setStatusBarColor() != 0) {
            Window window = getWindow();
            if (translucentStatusBar()) {
                // 设置状态栏全透明
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//21
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(Color.TRANSPARENT);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//19
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                }
            }
            // 沉浸式状态栏
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//21
                //5.0以上使用原生方法
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(setStatusBarColor());//直接设置状态栏的颜色
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//19
                SystemBarTintManager tintManager = new SystemBarTintManager(this);
                tintManager.setStatusBarTintEnabled(true);
                tintManager.setStatusBarTintColor(setStatusBarColor());//直接设置状态栏的颜色
            }
        }


    }

    /**
     * 无参启动下一个activity
     */
    @SuppressWarnings("unused")
    protected void openActivity(Class<? extends BaseActivity> toActivity) {
        openActivity(toActivity, null);
    }

    /**
     * Map<String, String>启动下一个activity
     */
    @SuppressWarnings("unused")
    protected void openActivityParams(Class<? extends BaseActivity> toActivity, Map<String, String> map) {
        Intent intent = new Intent(this, toActivity);
        if (map.size() > 0) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                intent.putExtra(entry.getKey(), entry.getValue());
            }
        }
        startActivity(intent);
    }

    /**
     * Map<String, String>启动下一个activity
     */
    @SuppressWarnings("unused")
    protected void openActivityParams(Class<? extends BaseActivity> toActivity, String key, String value) {
        Intent intent = new Intent(this, toActivity);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    /**
     * String key, int value启动下一个activity
     */
    @SuppressWarnings("unused")
    protected void openActivityParams(Class<? extends BaseActivity> toActivity, String key, int value) {
        Intent intent = new Intent(this, toActivity);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    /**
     * Map<String, String>启动下一个activity
     */
    @SuppressWarnings("unused")
    protected void openActivityParams(Class<? extends BaseActivity> toActivity, String key1, String value1, String key2, String value2) {
        Intent intent = new Intent(this, toActivity);
        intent.putExtra(key1, value1);
        intent.putExtra(key2, value2);
        startActivity(intent);
    }

    /**
     * Bundle参数启动下一个activity
     */
    protected void openActivity(Class<? extends BaseActivity> toActivity, Bundle parameter) {
        Intent intent = new Intent(this, toActivity);
        if (parameter != null) {
            intent.putExtras(parameter);
        }
        startActivity(intent);
    }

    /**
     * 带返回结果,无参启动下一个activity
     */
    @SuppressWarnings("unused")
    protected void openActivityForResult(Class<? extends BaseActivity> toActivity, int requestCode) {
        openActivityForResult(toActivity, null, requestCode);
    }


    /**
     * 带返回结果,启动下一个activity
     */
    @SuppressWarnings("unused")
    protected void openActivityForResult(Class<? extends BaseActivity> toActivity, String key1, String value1, int requestCode) {
        Intent intent = new Intent(this, toActivity);
        intent.putExtra(key1, value1);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 带返回结果,启动下一个activity
     */
    @SuppressWarnings("unused")
    protected void openActivityForResult(Class<? extends BaseActivity> toActivity, Bundle parameter, int requestCode) {
        Intent intent = new Intent(this, toActivity);
        if (parameter != null) {
            intent.putExtras(parameter);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 关闭activity
     */
    @SuppressWarnings("unused")
    protected void closeActivity() {
        this.finish();
    }


    /**
     * 程序是否在前台运行
     *
     * @return 返回程序是否在前台运行
     */
    public boolean isAppOnForeground() {
        //返回所有正在手机上运行的app的进程列表
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        if (activityManager != null) {
            List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
            if (appProcesses == null) {
                return false;
            }
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                // The name of the process that this object is associated with.
                if (appProcess.processName.equals(packageName)
                        && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 顶部topbarview左侧按钮处理
     */

    public void setCloseLisenter(MTopBarView mtb) {
        mtb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBackup();
                closeActivity();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isActive) {
            //app 从后台唤醒，进入前台
            isActive = true;
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        if (!isAppOnForeground()) {
            //app 进入后台
            //全局变量 记录当前应用已经进入后台
            isActive = false;
        }
    }


    @Override
    protected void onDestroy() {
        dismissPDialog();
        bDialog = null;
        if (okgoCancelTag != null) {//根据标识取消掉本页正在进行的网络请求的
            OkGo.cancelTag(OkGo.getInstance().getOkHttpClient(), okgoCancelTag);
        }
        super.onDestroy();

    }

    /**
     * 将资源颜色值转化为颜色值,等同于方法
     *
     * @see Context getColor(@ColorRes int id)
     */
    @SuppressWarnings("unused")
    public int getColorRes(@ColorRes int colorResId) {
        if (Build.VERSION.SDK_INT < 23) {
            return getResources().getColor(colorResId);
        }
        return getResources().getColor(colorResId, null);
    }

    /**统一处理返回事件*/
    public void handleBackup(){

    }

    /**显示提示信息*/
    public void showToast(@StringRes int strRes){
        ToastUtils.showToast(BaseActivity.this, strRes);
    }
    /**显示提示信息*/
    public void showToast(String strRes){
        ToastUtils.showToast(BaseActivity.this, strRes);
    }


//    /**
//     * 进度加载显示控制线程
//     */
//    private void timeThread() {
////        ScheduledThreadPoolExecutor executor=new ScheduledThreadPoolExecutor(6);
////        executor.execute(new Runnable() {
////            @Override
////            public void run() {
////                try {
////                    Thread.sleep(sleepTime);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
////                Message msg = Message.obtain();
////                msg.what = 1;
////                if (handler != null) {
////                    handler.sendMessage(msg);
////                }
////            }
////        });
//
//        ThreadUtils.getCashThreadPoolInstance().execute(new Runnable() {
//            @Override
//            public void run() {
////                try {
////                    Thread.sleep(sleepTime);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
//                SystemClock.sleep(sleepTime);
//                Message msg = Message.obtain();
//                msg.what = 1;
//                if (handler != null) {
//                    handler.sendMessage(msg);
//                }
//            }
//        });
//
////        new Thread() {
////            @Override
////            public void run() {
////                try {
////                    Thread.sleep(sleepTime);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
////                Message msg = Message.obtain();
////                msg.what = 1;
////                if (handler != null) {
////                    handler.sendMessage(msg);
////                }
////            }
////        }.start();
//    }


}
