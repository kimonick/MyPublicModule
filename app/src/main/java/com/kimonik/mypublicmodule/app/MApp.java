package com.kimonik.mypublicmodule.app;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.kimonik.utilsmodule.app.BaseApplication;

/**
 * * ===============================================================
 * name:             MApp
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

public class MApp extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        // 将“12345678”替换成您申请的APPID，申请地址：http://www.xfyun.cn
// 请勿在“=”与appid之间添加任何空字符或者转义符
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5b69312e");
    }
}
