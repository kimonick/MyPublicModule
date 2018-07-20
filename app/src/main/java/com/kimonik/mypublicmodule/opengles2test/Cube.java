package com.kimonik.mypublicmodule.opengles2test;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * * ===============================================================
 * name:             Cube
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

public class Cube {


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

    private int mProgram;


    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static float squareCoords[] = {
            -0.5f, 0.5f, 0.5f,    //正面左上0
            -0.5f, -0.5f, 0.5f,   //正面左下1
            0.5f, -0.5f, 0.5f,    //正面右下2
            0.5f, 0.5f, 0.5f,     //正面右上3
            -0.5f, 0.5f, -0.5f,    //反面左上4
            -0.5f, -0.5f, -0.5f,   //反面左下5
            0.5f, -0.5f, -0.5f,    //反面右下6
            0.5f, 0.5f, -0.5f    //反面右上7
    }; // top right

    private short drawOrder[] = {
            0, 3, 2, 0, 2, 1,    //正面
            0, 1, 5, 0, 5, 4,    //左面
            0, 7, 3, 0, 4, 7,    //上面
            6, 7, 4, 6, 4, 5,    //后面
            6, 3, 7, 6, 2, 3,    //右面
            6, 5, 1, 6, 1, 2     //下面
    }; // order to draw vertices

    //八个顶点的颜色，与顶点坐标一一对应
    float color[] = {
            0f, 1f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 1f, 0f, 1f,
            1f, 0f, 0f, 1f,
            1f, 0f, 0f, 1f,
            1f, 0f, 0f, 1f,
            1f, 0f, 0f, 1f,
    };

    public Cube() {
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
        int vertexShader = MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        // 创建一个OpenGL ES Program
        mProgram = GLES20.glCreateProgram();
        // 添加顶点着色器到Program
        GLES20.glAttachShader(mProgram, vertexShader);
        // 添加片元着色器到Program
        GLES20.glAttachShader(mProgram, fragmentShader);
        // 链接创建OpenGL ES可执行文件
        GLES20.glLinkProgram(mProgram);
    }

    //    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };
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
//        mColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");
        // 设置绘制颜色
        GLES20.glUniform4fv(mColorHandle, 8, color, 0);
        // 绘制形状
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
        //type必须使用GLES20.GL_UNSIGNED_SHORT类型否则无法显示
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        // 禁用顶点属性数组
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

}
/**
 * public class Cube extends Shape{
 * <p>
 * private FloatBuffer vertexBuffer,colorBuffer;
 * private ShortBuffer indexBuffer;
 * private final String vertexShaderCode =
 * "attribute vec4 vPosition;" +
 * "uniform mat4 vMatrix;"+
 * "varying  vec4 vColor;"+
 * "attribute vec4 aColor;"+
 * "void main() {" +
 * "  gl_Position = vMatrix*vPosition;" +
 * "  vColor=aColor;"+
 * "}";
 * <p>
 * private final String fragmentShaderCode =
 * "precision mediump float;" +
 * "varying vec4 vColor;" +
 * "void main() {" +
 * "  gl_FragColor = vColor;" +
 * "}";
 * <p>
 * private int mProgram;
 * <p>
 * final int COORDS_PER_VERTEX = 3;
 * final float cubePositions[] = {
 * -1.0f,1.0f,1.0f,    //正面左上0
 * -1.0f,-1.0f,1.0f,   //正面左下1
 * 1.0f,-1.0f,1.0f,    //正面右下2
 * 1.0f,1.0f,1.0f,     //正面右上3
 * -1.0f,1.0f,-1.0f,    //反面左上4
 * -1.0f,-1.0f,-1.0f,   //反面左下5
 * 1.0f,-1.0f,-1.0f,    //反面右下6
 * 1.0f,1.0f,-1.0f,     //反面右上7
 * };
 * final short index[]={
 * 6,7,4,6,4,5,    //后面
 * 6,3,7,6,2,3,    //右面
 * 6,5,1,6,1,2,    //下面
 * 0,3,2,0,2,1,    //正面
 * 0,1,5,0,5,4,    //左面
 * 0,7,3,0,4,7,    //上面
 * };
 * <p>
 * float color[] = {
 * 0f,1f,0f,1f,
 * 0f,1f,0f,1f,
 * 0f,1f,0f,1f,
 * 0f,1f,0f,1f,
 * 1f,0f,0f,1f,
 * 1f,0f,0f,1f,
 * 1f,0f,0f,1f,
 * 1f,0f,0f,1f,
 * };
 * <p>
 * private int mPositionHandle;
 * private int mColorHandle;
 * <p>
 * private float[] mViewMatrix=new float[16];
 * private float[] mProjectMatrix=new float[16];
 * private float[] mMVPMatrix=new float[16];
 * <p>
 * private int mMatrixHandler;
 * <p>
 * //顶点个数
 * private final int vertexCount = cubePositions.length / COORDS_PER_VERTEX;
 * //顶点之间的偏移量
 * private final int vertexStride = COORDS_PER_VERTEX * 4; // 每个顶点四个字节
 * <p>
 * <p>
 * public Cube(View mView) {
 * super(mView);
 * ByteBuffer bb = ByteBuffer.allocateDirect(
 * cubePositions.length * 4);
 * bb.order(ByteOrder.nativeOrder());
 * vertexBuffer = bb.asFloatBuffer();
 * vertexBuffer.put(cubePositions);
 * vertexBuffer.position(0);
 * <p>
 * ByteBuffer dd = ByteBuffer.allocateDirect(
 * color.length * 4);
 * dd.order(ByteOrder.nativeOrder());
 * colorBuffer = dd.asFloatBuffer();
 * colorBuffer.put(color);
 * colorBuffer.position(0);
 * <p>
 * ByteBuffer cc= ByteBuffer.allocateDirect(index.length*2);
 * cc.order(ByteOrder.nativeOrder());
 * indexBuffer=cc.asShortBuffer();
 * indexBuffer.put(index);
 * indexBuffer.position(0);
 * int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER,
 * vertexShaderCode);
 * int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER,
 * fragmentShaderCode);
 * //创建一个空的OpenGLES程序
 * mProgram = GLES20.glCreateProgram();
 * //将顶点着色器加入到程序
 * GLES20.glAttachShader(mProgram, vertexShader);
 * //将片元着色器加入到程序中
 * GLES20.glAttachShader(mProgram, fragmentShader);
 * //连接到着色器程序
 * GLES20.glLinkProgram(mProgram);
 * }
 *
 * @Override public void onSurfaceCreated(GL10 gl, EGLConfig config) {
 * //开启深度测试
 * GLES20.glEnable(GLES20.GL_DEPTH_TEST);
 * }
 * @Override public void onSurfaceChanged(GL10 gl, int width, int height) {
 * //计算宽高比
 * float ratio=(float)width/height;
 * //设置透视投影
 * Matrix.frustumM(mProjectMatrix, 0, -ratio, ratio, -1, 1, 3, 20);
 * //设置相机位置
 * Matrix.setLookAtM(mViewMatrix, 0, 5.0f, 5.0f, 10.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
 * //计算变换矩阵
 * Matrix.multiplyMM(mMVPMatrix,0,mProjectMatrix,0,mViewMatrix,0);
 * }
 * @Override public void onDrawFrame(GL10 gl) {
 * GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT| GLES20.GL_DEPTH_BUFFER_BIT);
 * //将程序加入到OpenGLES2.0环境
 * GLES20.glUseProgram(mProgram);
 * //获取变换矩阵vMatrix成员句柄
 * mMatrixHandler= GLES20.glGetUniformLocation(mProgram,"vMatrix");
 * //指定vMatrix的值
 * GLES20.glUniformMatrix4fv(mMatrixHandler,1,false,mMVPMatrix,0);
 * //获取顶点着色器的vPosition成员句柄
 * mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
 * //启用三角形顶点的句柄
 * GLES20.glEnableVertexAttribArray(mPositionHandle);
 * //准备三角形的坐标数据
 * GLES20.glVertexAttribPointer(mPositionHandle, 3,
 * GLES20.GL_FLOAT, false,
 * 0, vertexBuffer);
 * //获取片元着色器的vColor成员的句柄
 * mColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");
 * //设置绘制三角形的颜色
 * //        GLES20.glUniform4fv(mColorHandle, 2, color, 0);
 * GLES20.glEnableVertexAttribArray(mColorHandle);
 * GLES20.glVertexAttribPointer(mColorHandle,4,
 * GLES20.GL_FLOAT,false,
 * 0,colorBuffer);
 * //索引法绘制正方体
 * GLES20.glDrawElements(GLES20.GL_TRIANGLES,index.length, GLES20.GL_UNSIGNED_SHORT,indexBuffer);
 * //禁止顶点数组的句柄
 * GLES20.glDisableVertexAttribArray(mPositionHandle);
 * }
 * }
 */
