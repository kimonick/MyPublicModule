package com.kimonik.utilsmodule.utils;

import android.util.Log;

import com.kimonik.utilsmodule.adapter.listview.MultiItemTypeAdapter;

/**
 * * ==================================================
 * name:            LUtils
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/8/8
 * description：   打印log自定义信息
 * history：
 * * ==================================================
 */

public class LUtils {

    private static final boolean DEBUG = true;



    /**
     * 显示--类名,方法名,自定义信息
     *
     * @param clz 打印log所在的类
     * @param str 自定义打印的字符串
     *            mark:Thread.currentThread().getStackTrace()可以获得方法符调用顺序
     */
    public static void e(Class clz, String str) {
        if (DEBUG) {
            Log.e("------" + clz.getSimpleName(), Thread.currentThread().getStackTrace()[3].getMethodName() + "------" + str);
        }
    }
    /**从应用程序开始启动,到调用该方法的方法调用顺序栈,调用逆序*/
    public static void getMethodTrace(Class clz){
        StackTraceElement[] element=Thread.currentThread().getStackTrace();
        for (int i = 0; i < element.length; i++) {
            Log.e("方法逆序追踪","---"+i+"---"+
                    element[i].getClassName()+"---"+element[i].getMethodName()
            );
        }

    }
}
