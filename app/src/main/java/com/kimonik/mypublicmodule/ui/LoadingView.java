package com.kimonik.mypublicmodule.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.kimonik.mypublicmodule.R;
import com.kimonik.utilsmodule.utils.LUtils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * * ===============================================================
 * name:             LoadingView
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2018/7/30
 * method:
 * <p>
 * <p>
 * description：
 * history：
 * *==================================================================
 */

public class LoadingView extends View {
    private Paint arrowPaint;
    private RectF rectF;
    //圆弧起点,当前扫描角度,最大扫描角度
    private float startAngle = 90f, sweepAngle = 0f, maxSweepAngle = 240;
    //控制线程
    private Executor executor;
    private boolean isRuning = true;
    //左上角坐标点与直径
    private float leftPoint = 100f, topPoint = 100f, radius = 80f;
    /**
     * 当前圆弧的尾点位置坐标
     */
    private PointF pointF;
    /**
     * 箭头三角形的路径
     */
    private Path path;
    /**
     * 三角形画笔
     */
    private Paint trianglePaint;
    /**
     * 画布旋转角度
     */
    private int canvasSweep = 0;

    public LoadingView(Context context) {
        this(context, null, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        pointF = new PointF();
        path = new Path();
        arrowPaint = initPaint("#8B81E2", Paint.Style.STROKE, 10f);
        trianglePaint = initPaint("#8B81E2", Paint.Style.FILL, 10f);
        rectF = new RectF(leftPoint, topPoint, leftPoint + radius, topPoint + radius);
        executor = Executors.newFixedThreadPool(1);
        initThread();

    }

    private void initThread() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (isRuning) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (sweepAngle < maxSweepAngle) {
                        sweepAngle += 10;
                        pointF.x = leftPoint + radius / 2 - (float) (radius / 2 * Math.sin(degressToRadian(sweepAngle)));
                        pointF.y = topPoint + radius / 2 + (float) (radius / 2 * Math.cos(degressToRadian(sweepAngle)));
                        path.reset();
                        path.moveTo(pointF.x, pointF.y);
                        path.lineTo(pointF.x, pointF.y - 20);
                        path.lineTo(pointF.x - 25, pointF.y);
                        path.lineTo(pointF.x, pointF.y + 20);
                        path.close();
                    } else {
                        canvasSweep = (canvasSweep + 20) % 360;
                    }
                    postInvalidate();
                }
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //逆时针绘制,起点0在右侧
        canvas.save();
        canvas.rotate(canvasSweep, leftPoint + radius / 2, topPoint + radius / 2);
        canvas.drawArc(rectF, startAngle, sweepAngle, false, arrowPaint);
        canvas.save();
        canvas.rotate(sweepAngle, pointF.x, pointF.y);
        canvas.drawPath(path, trianglePaint);
        canvas.restore();
        canvas.restore();

    }

    private Paint initPaint(String color, Paint.Style style, float width) {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor(color));
        paint.setAntiAlias(true);
        paint.setStyle(style);
        paint.setStrokeWidth(width);
        return paint;
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == View.VISIBLE) {
            isRuning = true;
            initThread();
        } else {
            isRuning = false;
        }
    }

    /**
     * 角度转换为弧度
     */
    private double degressToRadian(float degress) {
        return (Math.PI / 180) * degress;
    }

    /**
     * 点击后重置
     *
     * @param event 点击事件
     * @return ""
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LUtils.getMethodTrace(this.getClass());


        MotionEvent motionEvent = MotionEvent.obtain(event);

        LUtils.e(LoadingView.class, "logflag--点击事件-1");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //返回true才能接收到up事件
                LUtils.e(LoadingView.class, "logflag--点击事件-2"+(event==motionEvent));
                break;
            case MotionEvent.ACTION_UP:
                sweepAngle = 0;
                canvasSweep = 0;
                LUtils.e(LoadingView.class, "logflag--点击事件-3");
                break;
        }
        LUtils.e(LoadingView.class, "logflag--点击事件-4");
        return super.onTouchEvent(motionEvent);
    }


}
