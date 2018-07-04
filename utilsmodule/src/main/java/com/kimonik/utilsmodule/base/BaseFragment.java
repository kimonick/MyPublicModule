package com.kimonik.utilsmodule.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kimonik.utilsmodule.R;
import com.kimonik.utilsmodule.utils.DialogUtils;
import com.kimonik.utilsmodule.utils.ToastUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * * ================================================
 * name:            BaseActivity
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/10
 * description：     fragment基类
 * history：
 * ===================================================
 */


public abstract class BaseFragment extends Fragment implements BaseMethod, View.OnClickListener {
    Unbinder unbinder;

    private AlertDialog bDialog;

    /**
     * 本activity内的okgo的请求标识
     */
    private String okgoCancelTag;

    /**
     * dialog最长的显示时间,超时将自动消失
     */
    private long sleepTime = 15000;
    //-----------------------------------------联网请求计时---------------------------------------------------------

//    private boolean isProgressing = false;

//    private MyHandler handler;

    /**
     * 下拉刷新,上拉加载布局
     */
    private SmartRefreshLayout srl;

    @SuppressWarnings("unused")
    public void setSrl(SmartRefreshLayout srl) {
        this.srl = srl;
    }

    /**
     * 结束加载刷新
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
                    dismissPDialog();
                    finishRL();
                    loadInternetDataToUi(response);
                }

                @Override
                public void onError(Response<String> response) {
                    super.onError(response);
                    dismissPDialog();
                    finishRL();
                    ToastUtils.showToast(getActivity(), R.string.wangluobutaigeiliyou);
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


    @SuppressWarnings("unused")
    public String getOkgoCancelTag() {
        return okgoCancelTag;
    }

    @SuppressWarnings("unused")
    public void setOkgoCancelTag(String okgoCancelTag) {
        this.okgoCancelTag = okgoCancelTag;
    }

//    private class MyHandler extends Handler {
//        // 弱引用 ，防止内存泄露
//        private WeakReference<FragmentActivity> weakReference;
//
//        public MyHandler(FragmentActivity handlerMemoryActivity) {
//            weakReference = new WeakReference<FragmentActivity>(handlerMemoryActivity);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            FragmentActivity handlerMemoryActivity = weakReference.get();
//            if (handlerMemoryActivity != null && isProgressing && msg.what == 1) {
//                OkGo.getInstance().cancelAll();
////                ToastUtils.showToast(getActivity(), R.string.shujujiazaichaoshi);
//                dismissPDialog();
//            } else {
//                dismissPDialog();
//            }
//        }
//    }


    //------------------------------------------联网请求计时--------------------------------------------------------


    @SuppressWarnings("unused")
    public void showPDialog() {
//        isProgressing = true;
//        timeThread();
        try {
            if (bDialog == null) {
                bDialog = DialogUtils.showProgreessDialog(getActivity(), getResources().getString(R.string.zaicidianjijinagtuichugaiyemian));
            } else {
                bDialog.show();
            }
        } catch (Exception e) {
            Log.e("TAG", "showPDialog: -----窗体泄露");
        }

    }

    public void dismissPDialog() {
//        isProgressing = false;
        if (getActivity()!=null&&!(getActivity().isFinishing()||getActivity().isDestroyed())){
            if (bDialog != null && bDialog.isShowing()) {
                bDialog.dismiss();
            }
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(layoutRes(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initDataFromIntent();
        initView();
        initListener();
        initDataFromInternet();
//        handler = new MyHandler(getActivity());
        return view;
    }

    /**
     * 设置fragment的布局资源id
     */
    public abstract int layoutRes();


    /**
     * 启动下一个activity
     */
    @SuppressWarnings("unused")
    protected void openActivity(Class<? extends BaseActivity> toActivity) {
        openActivity(toActivity, null);
    }

    /**
     * 启动下一个activity
     */
    protected void openActivity(Class<? extends BaseActivity> toActivity, Bundle parameter) {
        Intent intent = new Intent(getActivity(), toActivity);
        if (parameter != null) {
            intent.putExtras(parameter);
        }
        startActivity(intent);
    }

    /**
     * Map<String, String>启动下一个activity
     */
    @SuppressWarnings("unused")
    protected void openActivityParams(Class<? extends BaseActivity> toActivity, Map<String, String> map) {
        Intent intent = new Intent(getActivity(), toActivity);
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
        Intent intent = new Intent(getActivity(), toActivity);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    /**
     * Map<String, String>启动下一个activity
     */
    @SuppressWarnings("unused")
    protected void openActivityParams(Class<? extends BaseActivity> toActivity, String key1, String value1, String key2, String value2) {
        Intent intent = new Intent(getActivity(), toActivity);
        intent.putExtra(key1, value1);
        intent.putExtra(key2, value2);
        startActivity(intent);
    }

    /**
     * 启动下一个activity
     */
    @SuppressWarnings("unused")
    protected void openActivityForResult(Class<? extends BaseActivity> toActivity, int requestCode) {
        Intent intent = new Intent(getActivity(), toActivity);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 带返回结果,启动下一个activity
     */
    @SuppressWarnings("unused")
    protected void openActivityForResult(Class<? extends BaseActivity> toActivity, Bundle parameter, int requestCode) {
        Intent intent = new Intent(getActivity(), toActivity);
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
        if (getActivity() != null) {
            getActivity().finish();
        }
    }


    @Override
    public void onDestroyView() {
        dismissPDialog();
        bDialog = null;

        if (okgoCancelTag != null) {//根据标识取消掉本页正在进行的网络请求的
            OkGo.cancelTag(OkGo.getInstance().getOkHttpClient(), okgoCancelTag);
        }

//        OkGo.cancelAll(OkGo.getInstance().getOkHttpClient());
        unbinder.unbind();
        super.onDestroyView();
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

//    /**
//     * 进度加载显示控制线程
//     */
//    private void timeThread() {
//        ThreadUtils.getCashThreadPoolInstance().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(sleepTime);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                Message msg = Message.obtain();
//                msg.what = 1;
//                if (handler != null) {
//                    handler.sendMessage(msg);
//                }
//            }
//        });
////        new Thread() {
////            @Override
////            public void run() {
////                try {
////                    Thread.sleep(sleepTime);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
////
////                Message msg = Message.obtain();
////                msg.what = 1;
////                if (handler != null) {
////                    handler.sendMessage(msg);
////                }
////            }
////        }.start();
//    }
}
