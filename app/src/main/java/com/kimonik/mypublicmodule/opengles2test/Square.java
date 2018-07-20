package com.kimonik.mypublicmodule.opengles2test;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * * ===============================================================
 * name:             Square
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

public class Square {


    //顶点着色器

    private final String vertexShaderCode =

            "attribute vec4 vPosition;" +

                    "void main() {" +

                    "  gl_Position = vPosition;" +

                    "}";

    //片元着色器

    private final String fragmentShaderCode =

            "precision mediump float;" +

                    "uniform vec4 vColor;" +

                    "void main() {" +

                    "  gl_FragColor = vColor;" +

                    "}";

    private  int mProgram;


    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static float squareCoords[] = {
            -0.5f,  0.5f, 0.0f,   // top left
            -0.5f, -0.5f, 0.0f,   // bottom left
            0.5f, -0.5f, 0.0f,   // bottom right
//            -0.5f,  0.5f, 0.0f,   // top left
//            0.5f, -0.5f, 0.0f,   // bottom right
            0.5f,  0.5f, 0.0f }; // top right

    private short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices

    public Square() {
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
                drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);


        // 编译着色器代码
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentShaderCode);
        // 创建一个OpenGL ES Program
        mProgram = GLES20.glCreateProgram();
        // 添加顶点着色器到Program
        GLES20.glAttachShader(mProgram, vertexShader);
        // 添加片元着色器到Program
        GLES20.glAttachShader(mProgram, fragmentShader);
        // 链接创建OpenGL ES可执行文件
        GLES20.glLinkProgram(mProgram);
    }

    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };
    //顶点着色器的vPosition成员
    private int mPositionHandle;
    //    片元着色器的vColor成员
    private int mColorHandle;
    //顶点的个数
    private final int vertexCount = drawOrder.length / COORDS_PER_VERTEX;
    //        每个顶点占有的字节数
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    public void draw() {
        // 将Program添加至OpenGL ES环境
        GLES20.glUseProgram(mProgram);
        // 获取顶点着色器的vPosition成员
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        // 启用顶点属性数组。如果启用，那么当glDrawArrays或者glDrawElements被调用时，
        // 顶点属性数组会被使用。
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        // 准备形状坐标数据
//        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
//                GLES20.GL_SHORT, false,
//                vertexStride, drawListBuffer);
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);
        // 获取片元着色器的vColor成员
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        // 设置绘制颜色
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);
        // 绘制形状
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
        //type必须使用GLES20.GL_UNSIGNED_SHORT类型否则无法显示
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, 6, GLES20.GL_UNSIGNED_SHORT,drawListBuffer);
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        // 禁用顶点属性数组
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

}
