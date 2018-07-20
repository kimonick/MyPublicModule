package com.kimonik.mypublicmodule.opengles2test;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.kimonik.mypublicmodule.opengles2test.Cube1;

/**
 * * ===============================================================
 * name:             MyGLSurfaceView
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2018/7/19
 * method:
 * <p>
 * <p>
 * description：
 * history：
 * *==================================================================
 */

public class MyGLSurfaceView extends GLSurfaceView {

//    private final MyGLRenderer renderer=new MyGLRenderer();
    private final Cube1 renderer=new Cube1();

    public MyGLSurfaceView(Context context) {
        this(context, null);

    }

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //使用的opengl es的版本
        setEGLContextClientVersion(2);
        //设置渲染器
        setRenderer(renderer);
    }
}
