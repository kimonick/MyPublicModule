package com.kimonik.utilsmodule.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;


/**
 * * ==================================================
 * name:            ToastUtils
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/7
 * description：   toast显示工具类
 * method:
 * <p>
 * showToast(Context context, @StringRes int resId)------使用字符串资源显示短toast
 * showToast(Context context, String content)-----------使用字符串显示短toast
 * showToast(Context context, String content, int duration )----使用字符串显示自定义时间的toast
 * showToast(Context context, @StringRes int resId, int duration )-----使用字符串资源显示自定义时间的toast
 * <p>
 * history：
 * <p>
 * Toast使用自定义背景色以及使用自定view的方法
 * Toast toast=Toast.makeText(context,content,duration);
 * View  view=toast.getView();
 * view.setBackgroundColor(int color);
 * toast.show();
 * 自定义view
 * TextView textview=new TextView(context);
 * textview.setText(content);
 * textview.setBackgroundColor(int color);
 * ........
 * toast.setView(textview);
 * toast.show();
 * * ==================================================
 */


public class ToastUtils {

    /**
     * 使用资源id展示
     */
    public static void showToast(final Context context, @StringRes final int resId) {
//        Toast toast=Toast.makeText(context, resId, Toast.LENGTH_SHORT);
//        TextView textview=new TextView(context);
//        textview.setText(resId);
//        textview.setBackgroundResource(R.drawable.xshape_roundrect_toastbac);
//        textview.setGravity(Gravity.CENTER);
//        toast.setView(textview);
//        toast.show();

        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }


    /**
     * 使用字符串展示
     */
    public static void showToast(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    /**
     * 使用字符串展示
     */
    public static void showToast(Context context, String content, int duration) {
        Toast.makeText(context, content, duration).show();
    }

    /**
     * 使用资源id展示
     */
    public static void showToast(Context context, @StringRes int resId, int duration) {
        Toast.makeText(context, resId, duration).show();
    }
}
