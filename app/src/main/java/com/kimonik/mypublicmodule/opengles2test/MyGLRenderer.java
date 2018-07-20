package com.kimonik.mypublicmodule.opengles2test;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.kimonik.utilsmodule.utils.LUtils;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * * ===============================================================
 * name:             MyGLRenderer
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2018/7/19
 * method:
 * <p>
 * <p>
 * description：
 * history：
 * https://blog.csdn.net/junzia/article/details/52820177
 * *==================================================================
 */

public class MyGLRenderer implements GLSurfaceView.Renderer {
    //三角形图形类
    private Triangle triangle;
    private Square square;
    private Cube cube;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        LUtils.e(MyGLRenderer.class,"logflag---001");
//        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        //绘制背景色
        /**
         * glClearColor
         含义：设置颜色缓冲区的清理值
         glClearColor(float red, float green, float blue, float alpha) 指明红、绿、蓝、
         alpha
         的值并通过glClear 来清理颜色缓冲区，被glClearColor 指明的值属于区间[0, 1]。
         */
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        //实例化图形类
        triangle = new Triangle();
        square=new Square();
        cube=new Cube();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        LUtils.e(MyGLRenderer.class,"logflag---002");

        GLES20.glViewport(0, 0, width, height);
    }

    /**
     * 绘制每一帧
     * 该方法会被系统不停的调用
     * @param gl
     */
    @Override
    public void onDrawFrame(GL10 gl) {
        LUtils.e(MyGLRenderer.class,"logflag---003");
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
//        triangle.draw();
//        square.draw();
        cube.draw();
    }

    /**
     * 加载并编译着色器代码
     *
     * @param type       渲染器类型 {GLES20.GL_VERTEX_SHADER, GLES20.GL_FRAGMENT_SHADER}
     * @param shaderCode GLSL渲染器代码
     * @return  返回int
     */
    public static int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        //编译shader代码
        GLES20.glCompileShader(shader);
        return shader;
    }


}
