package com.kimonik.mypublicmodule.ui.lottery;

import android.annotation.TargetApi;
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
 * name:             PrizeView
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2018/8/23
 * method:
 * <p>
 * <p>
 * description：抽奖view中心奖品view
 * history：
 * *==================================================================
 */

public class PrizeView extends View {
    /**奖品图片*/
    private Bitmap bitmapRed, bitmapXie, bitmapFu;
    /**奖品图片绘制位置*/
    private Rect rectFRed;
    /**扇形区域所在的矩形*/
    private RectF rectFArc;
    /**颜色值*/
    private String  rectColor ="#368DEA",textColor = "#6BA6B1",
            arcColor1 = "#FFFFFF", arcColor2 = "#DBFDFF";
    /**奖品文本*/
    private String thank = "谢谢参与", eight = "8", doubleEight = "88", threeEight = "888", oneEight
            = "188", lucky = "幸运福袋";
    /**画笔*/
    private Paint  paintRect, paintText, arcPaint1, arcPaint2;
    /**屏幕宽度*/
    private int width = MApp.DEVICE_WIDTH;
    /**半径,圆心坐标*/
    private float radius, centerX, centerY;
    /**位图集合*/
    private List<Bitmap> list;
    /**字符串集合*/
    private List<String> listString;
    /**
     * 线程池
     */
    private Executor executor;
    /**线程控制标志*/
    private boolean flag = true;


    public void setRotateTime(int rotateTime) {
        this.rotateTime = rotateTime;
    }

    public int getRotateTime() {
        return rotateTime;
    }

    public PrizeView(Context context) {
        this(context, null, 0);
    }

    public PrizeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PrizeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PrizeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    private void init() {
        radius = width * 0.31f;
        centerX = width * 0.525f;
        centerY = width * 0.55f;

        bitmapFu = BitmapFactory.decodeResource(getResources(), R.drawable.xinyunfudai);
        bitmapRed = BitmapFactory.decodeResource(getResources(), R.drawable.hognbao);
        bitmapXie = BitmapFactory.decodeResource(getResources(), R.drawable.xiexiecanyu);

        list = new ArrayList<>();
        list.add(bitmapRed);
        list.add(bitmapRed);
        list.add(bitmapRed);
        list.add(bitmapRed);
        list.add(bitmapFu);

        listString = new ArrayList<>();
        listString.add(eight);
        listString.add(doubleEight);
        listString.add(oneEight);
        listString.add(threeEight);
        listString.add(lucky);


        arcPaint1 = LotteryGroupView.initPaint(arcColor1, Paint.Style.FILL, 5);
        arcPaint2 = LotteryGroupView.initPaint(arcColor2, Paint.Style.FILL, 5);
        paintRect = LotteryGroupView.initPaint(rectColor, Paint.Style.FILL, 5);
        paintText = LotteryGroupView.initPaint(textColor, Paint.Style.FILL, 5);
        paintText.setTextSize(0.03704f * width);


        rectFRed = new Rect((int) (0.47f * width), (int) (0.338f * width), (int) (0.58f * width),
                (int) (0.448f * width));
        //半径0.28f--0.295f
        rectFArc = new RectF(width * 0.245f, width * 0.27f, width * 0.805f, width * 0.83f);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        for (int i = 1; i < 7; i++) {
            if (i % 2 == 0) {
                canvas.drawArc(rectFArc, 60 * (i - 1), 60, true, arcPaint1);
            } else {
                canvas.drawArc(rectFArc, 60 * (i - 1), 60, true, arcPaint2);
            }
        }
        canvas.drawText(thank, 0.525f * width - paintText.measureText(lucky) / 2, 0.33f * width,
                paintText);
        canvas.drawBitmap(bitmapXie, null, rectFRed, null);

        for (int i = 0; i < 5; i++) {
            int s=canvas.save();
            canvas.rotate(60f, centerX, centerY);
            canvas.drawText(listString.get(i), 0.525f * width - paintText.measureText(listString
                    .get(i)) / 2, 0.33f * width, paintText);
            canvas.drawBitmap(list.get(i), null, rectFRed, null);
        }
//        canvas.restoreToCount(1);
        canvas.restore();
        canvas.restore();
        canvas.restore();
        canvas.restore();
        canvas.restore();


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(width, width);
    }



    /**实际相对起点旋转的角度*/
    private int rotate;
    /**重新绘制的休眠时间,与单次旋转角度共同作用控制旋转速度*/
    private int frequency=50;
    /**旋转次数计数*/
    private int count = 0;
    /**预定总旋转时间*/
    private int rotateTime;
    /**单次旋转角度*/
    private int degress=30;
    public void startRotate() {

        flag = true;
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
                        count++;
                        //设置旋转中心点
                        setPivotX(centerX);
                        setPivotY(centerY);
                        rotate = count * degress % 360;
                        //无论旋转多少次,旋转角度都是相对起始状态的旋转角度
                        //设置旋转不需要刷新视图,在子线程中运行也可以
                        setRotation(rotate);
                        if (count * frequency > rotateTime && rotate % 60 == 0
//                                && rotate == 120使用固定角度控制旋转至指定选项
                                ) {
                            flag = false;
                            count = 0;
                            rotateTime = 0;
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        flag=false;
    }
}
