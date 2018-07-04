package com.kimonik.utilsmodule.app;

import android.app.Application;


import com.kimonik.utilsmodule.utils.ScreenSizeUtils;

import org.litepal.LitePal;

/**
 * * ===============================================================
 * name:             BaseApplication
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2018/3/19
 * method:
 * <p>
 * <p>
 * description：
 * history：
 * *==================================================================
 */

public class BaseApplication extends Application {

    /**设备屏幕宽度*/
    public static int DEVICE_WIDTH;
    /**设备屏幕高度*/
    public static int DEVICE_HEIGHT;
    /**设备屏幕密度*/
    public static int DEVICE_DENSITY;
    /**设备状态栏高度*/
    public static int STATUS_BAE_HEIGHT;


    @Override
    public void onCreate() {
        super.onCreate();

        DEVICE_WIDTH = ScreenSizeUtils.getScreenWidth(this);
        DEVICE_HEIGHT = ScreenSizeUtils.getScreenHeight(this);
        DEVICE_DENSITY = ScreenSizeUtils.getDensity(this);
        STATUS_BAE_HEIGHT=ScreenSizeUtils.getStatusHeight(this);

        //初始化litepal
        LitePal.initialize(this);

//        CreateCode.initCreatCode(this);
//        initOkGo();
    }

}
