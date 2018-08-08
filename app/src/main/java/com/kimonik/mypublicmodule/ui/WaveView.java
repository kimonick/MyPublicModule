package com.kimonik.mypublicmodule.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * * ===============================================================
 * name:             WaveView
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2018/7/24
 * method:
 * <p>
 * <p>
 * description：
 * history：
 * *==================================================================
 */

public class WaveView extends View {
    /**
     * 波浪画笔
     */
    private Paint paint, paint1;
    /**
     * 波浪路径
     */
    private Path path;
    private Path path1;
    /**
     * 线程是否启动
     */
    private boolean isRun = true;
    /**
     * 波浪在屏幕中显示的长度
     */
    private float waveLength;
    /**
     * 波浪偏移量,波浪移动速度
     */
    private float offset = 0,offset1=100;
    /**
     * 波浪中线,波浪水平线
     */
    private float centerY;
    /**
     * 波浪的数量,屏幕中显示的波浪数量加1,至少为2
     */
    private int waveNum =3;
    /**
     * 正弦曲线波浪,一个完整的正弦曲线为一个波浪,该值为一个完整正弦曲线的长度
     * 其值为waveLength/(waveNum-1)
     */
    private float waveLengthNum;
    /**
     * 正弦曲线的振幅占正弦曲线的长度的百分比
     */
    private float amplitude = 0.1f;
    /**
     * 波浪的移动速度
     */
    private int velocity = 35;
    private int velocity1 = 25;
    /**
     * 绘制的频率,毫秒/帧
     */
    private long sleepTime = 50;
    /**
     * 波浪的颜色
     */
    private String color = "#5192E2";
    /**
     * 控制线程
     */
    private ThreadPoolExecutor executor;


    public WaveView(Context context) {
        this(context, null, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        paint = initPaint(color, 5f, Paint.Style.FILL);
        paint1 = initPaint("#8B81E2", 5f, Paint.Style.FILL);
        path = new Path();
        path1 = new Path();

    }

    private void initPath() {
        path.reset();
        path1.reset();
        //初始点在中线上
        path.moveTo(-waveLengthNum + offset, centerY);
        path1.moveTo(-waveLengthNum + offset1, centerY);
//        模拟单一正弦曲线
        for (int i = 0; i < waveNum + 1; i++) {
            //正弦曲线路径
            path.quadTo((-waveLengthNum * 3 / 4) + (i * waveLengthNum) + offset
                    , centerY + waveLengthNum * amplitude
                    , -0.5f * waveLengthNum + (i * waveLengthNum) + offset
                    , centerY);
            path.quadTo((-waveLengthNum / 4) + (i * waveLengthNum) + offset
                    , centerY - waveLengthNum * amplitude
                    , i * waveLengthNum + offset
                    , centerY);
            path1.quadTo((-waveLengthNum * 3 / 4) + (i * waveLengthNum) + offset1
                    , centerY - waveLengthNum * amplitude
                    , -0.5f * waveLengthNum + (i * waveLengthNum) + offset1
                    , centerY);
            path1.quadTo((-waveLengthNum / 4) + (i * waveLengthNum) + offset1
                    , centerY + waveLengthNum * amplitude
                    , i * waveLengthNum + offset1
                    , centerY);
        }
        path.lineTo(1080, 1920);
        path.lineTo(0, 1920);
        path.close();
        path1.lineTo(1080, 1920);
        path1.lineTo(0, 1920);
        path1.close();

    }

    private void initThread() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (isRun) {
                    try {
                        Thread.sleep(sleepTime);
//                      避免出现边缘空白
                        if (offset > waveLengthNum - velocity) {
                            offset =0;
                        }
                        if (offset1> waveLengthNum - velocity1){
                            offset1 =0;
                        }
                        offset += velocity;
                        offset1 += velocity1;
                        initPath();
                        postInvalidate();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private Paint initPaint(String color, float width, Paint.Style style) {
        Paint mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setStyle(style);
        mpaint.setStrokeWidth(width);
        mpaint.setColor(Color.parseColor(color));
        LinearGradient linearGradient = new LinearGradient(540, 900, 540, 1920,
                Color.parseColor(color), Color.CYAN, Shader.TileMode.REPEAT);
        mpaint.setShader(linearGradient);
        return mpaint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path1, paint1);
        canvas.drawPath(path, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        waveLength = MeasureSpec.getSize(widthMeasureSpec);
        centerY = MeasureSpec.getSize(heightMeasureSpec) / 2;
        waveLengthNum = waveLength / (waveNum - 1);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //根据View是否显示来开启关闭动画
    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == View.VISIBLE) {
            isRun = true;
            initThread();
        } else {
            isRun = false;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        isRun = false;
        super.onDetachedFromWindow();
    }
    /**
     * 1.
     */
}
