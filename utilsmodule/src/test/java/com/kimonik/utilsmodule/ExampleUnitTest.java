package com.kimonik.utilsmodule;

import android.util.Log;

import com.kimonik.utilsmodule.utils.LUtils;
import com.kimonik.utilsmodule.utils.TimeUtils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        System.out.print(TimeUtils.getCurentTimeTotal());
        assertEquals(4, 2 + 2);

    }
}