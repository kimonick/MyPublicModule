package com.kimonik.utilsmodule.utils;

import android.support.annotation.Nullable;

/**
 * * ===============================================================
 * name:             CheckUtils
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2018/3/22
 * method:
 * <p>
 * <p>
 * description：  检测规则工具类
 * history：
 * *==================================================================
 */

public class CheckUtils {
    private CheckUtils() {
        throw new UnsupportedOperationException("u can't initilize me!");
    }

    /**
     * 判断对象是不是为null
     */
    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        } else {
            return reference;
        }
    }

    /**
     * 判断字符串是否为null或者""
     */
    public static boolean checkString(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 检查日期格式是否为yyyy-mm-dd格式
     *
     * @param date 日期字符串
     * @return true--格式错误,false--格式正确
     */
    public static boolean checkDate(String date) {
        if (date == null) {
            return true;
        }
        if (date.length() != 10) {
            return true;
        }
        String[] split = date.split("-");
        if (split.length != 3) {
            return true;
        } else if (StringUtils.string2Integer(split[1]) < 1 || StringUtils.string2Integer(split[1]) > 12) {
            return true;
        } else if (StringUtils.string2Integer(split[2]) > 31 || StringUtils.string2Integer(split[2]) < 1) {
            return true;
        }
        return false;
    }
}
