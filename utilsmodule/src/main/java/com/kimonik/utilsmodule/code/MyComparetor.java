package com.kimonik.utilsmodule.code;

/**
 * * ===============================================================
 * name:             MyComparetor
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2018/1/23
 * description：
 * history：
 * *==================================================================
 */

import java.util.Comparator;

public class MyComparetor implements Comparator<Object> {
    public MyComparetor() {
    }

    public int compare(Object o1, Object o2) {
        String s1 = (String)o1;
        String s2 = (String)o2;
        return s1.compareTo(s2);
    }
}

