package com.kimonik.mypublicmodule.ui.lottery;

import android.annotation.TargetApi;
import android.arch.lifecycle.MethodCallsLogger;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.kimonik.mypublicmodule.R;
import com.kimonik.mypublicmodule.app.MApp;
import com.kimonik.utilsmodule.utils.LUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * * ===============================================================
 * name:             FlickerView
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2018/8/23
 * method:
 * <p>
 * <p>
 * description：抽奖view闪烁小灯view
 * history：
 * *==================================================================
 */

public class FlickerView extends View {
    /**指定闪烁小灯的颜色值*/
    private String deng1 = "#77C85D", deng2 = "#DBCD72";
    /**绘制闪烁小灯的画笔*/
    private Paint paintDeng1, paintDeng2;
    /**设备屏幕宽度*/
    private int width = MApp.DEVICE_WIDTH;
    /**小圆的半径*/
    private float smallRadious;
    /**
     * 闪烁小灯位置集合
     */
    private List<PointF> listFlicker;
    /**
     * 线程池
     */
    private Executor executor;
    /**控制线程开启关闭,控制小灯颜色变换*/
    private boolean flag = true, glittering = true;
    /**小灯的总数量*/
    private int flickerCount = 24;

    public FlickerView(Context context) {
        this(context, null, 0);
    }

    public FlickerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FlickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    private void init() {
        float centerX = width * 0.525f;
        float centerY = width * 0.55f;
        /*
      闪烁小灯圆心所在大圆半径,闪烁小灯圆的半径
     */
        float flickerRadious = width * 0.295f;
        smallRadious = width * 0.01f;


        listFlicker = new ArrayList<>();
        for (int i = 0; i < flickerCount; i++) {
            PointF pointF = new PointF();
            pointF.x = (float) (centerX + flickerRadious * Math.cos(2 * Math.PI / flickerCount *
                    i));
            pointF.y = (float) (centerY - flickerRadious * Math.sin(2 * Math.PI / flickerCount *
                    i));
            listFlicker.add(pointF);
        }


        paintDeng1 = LotteryGroupView.initPaint(deng1, Paint.Style.FILL, 5);
        paintDeng2 = LotteryGroupView.initPaint(deng2, Paint.Style.FILL, 5);


    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startFlicker();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        flag = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < flickerCount; i++) {
            if (glittering) {
                if (i % 2 == 0) {
                    canvas.drawCircle(listFlicker.get(i).x, listFlicker.get(i).y, smallRadious,
                            paintDeng1);
                } else {
                    canvas.drawCircle(listFlicker.get(i).x, listFlicker.get(i).y,
                            smallRadious, paintDeng2);
                }
            } else {
                if (i % 2 == 0) {
                    canvas.drawCircle(listFlicker.get(i).x, listFlicker.get(i).y, smallRadious,
                            paintDeng2);
                } else {
                    canvas.drawCircle(listFlicker.get(i).x, listFlicker.get(i).y,
                            smallRadious, paintDeng1);
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //固定view尺寸为屏幕宽度,可根据需要修改为父视图的宽度
        setMeasuredDimension(width, width);
    }



    /**刷新时间间隔*/
    private int frequency=500;
    private void startFlicker() {
        if (executor != null) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    while (flag) {
                        try {
                            Thread.sleep(frequency);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        glittering = !glittering;
                        postInvalidate();
                    }
                }
            });
        }
    }
}
