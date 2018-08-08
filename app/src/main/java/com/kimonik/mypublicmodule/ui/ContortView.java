package com.kimonik.mypublicmodule.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.kimonik.mypublicmodule.R;
import com.kimonik.utilsmodule.utils.LUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * * ===============================================================
 * name:             ContortView
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2018/8/2
 * method:
 * <p>
 * <p>
 * description：bitmap扭曲绘制
 * history：
 * *==================================================================
 */

public class ContortView extends View {

    private Bitmap bitmap;
    private Executor executor;
    private float[] newPos, origPos;
    private Paint paint;
    private int widthPart = 3, heightPart = 3;
    private int count = 0;

    public ContortView(Context context) {
        this(context, null, 0);
    }

    public ContortView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContortView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ContortView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        LUtils.e(ContortView.class,"logflag-可用的cpu数目--"+Runtime.getRuntime().availableProcessors());


        executor = Executors.newSingleThreadExecutor();
        bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.xiaoche);
        newPos = new float[(widthPart + 1) * (heightPart + 1) * 2];
        origPos = new float[(widthPart + 1) * (heightPart + 1) * 2];
        getBitmapMeshPoints(bitmap, heightPart, widthPart, newPos, origPos);
        initThread();
    }

    private void initThread() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                    postInvalidate();

                }
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.WHITE);
//        canvas.setDrawFilter(paintFlagsDrawFilter);
        //原始点
        canvas.save();
        canvas.scale(0.2f, 0.2f);
        canvas.translate(300, 500);
        //按照给出的数组坐标点将bitmap绘制到点所组成的范围内
        if (count % 2 == 0) {
            canvas.drawBitmapMesh(bitmap, widthPart, heightPart, origPos, 0, null, 0, null);
            for (int i = 0; i < origPos.length / 2; i++) {
                canvas.drawCircle(origPos[i * 2], origPos[i * 2 + 1], 50, paint);
            }
        } else {
            canvas.drawBitmapMesh(bitmap, widthPart, heightPart, newPos, 0, null, 0, null);
            for (int i = 0; i < newPos.length / 2; i++) {
                canvas.drawCircle(newPos[i * 2], newPos[i * 2 + 1], 50, paint);
            }
        }

        canvas.restore();
        //变化点
//        canvas.save();
//        canvas.translate(1000,50);
//        canvas.drawBitmapMesh(bitmapCarBody, carBodyWidthPart, carBodyHeightPart,
// carBodyNewsPos, 0, null, 0, paint);
//        paint.setColor(Color.RED);
//        paint.setStrokeWidth(5);
//        drawPoint(canvas, carBodyNewsPos, paint);
//        canvas.restore();

    }

    /**
     * 得到网格点集合
     *
     * @param bitmap     bitmap
     * @param heightPart 纵向分割的份数
     * @param widthPart  横向分割的份数
     * @param newPos     新点集合
     * @param origPos    原始点集合
     */
    protected void getBitmapMeshPoints(Bitmap bitmap, int heightPart, int widthPart, float[]
            newPos, float[] origPos) {
        //初始化网格点
        int index = 0;
        float bmWidth = bitmap.getWidth();
        float bmHeight = bitmap.getHeight();
        for (int i = 0; i < heightPart + 1; i++) {
            float fy = bmHeight * i / heightPart;
            for (int j = 0; j < widthPart + 1; j++) {
                float fx = bmWidth * j / widthPart;
                //X轴坐标 放在偶数位
                newPos[index * 2] = fx;
                origPos[index * 2] = newPos[index * 2];
                //Y轴坐标 放在奇数位
                newPos[index * 2 + 1] = fy;
                origPos[index * 2 + 1] = newPos[index * 2 + 1];
                index += 1;
            }
        }
        handleData(origPos, widthPart + 1);//307.2
    }

    /**
     * 将长方形矩阵转化为梯形矩阵
     * step=2
     * 0   1   2    3
     * 4   5   6    7
     * 8   9   10  11
     */
    private void handleData(float[] data, int step) {
        if (data == null || data.length < step * 2) return;
        //横向宽度
        float width = data[step * 2 - 2] - data[0];
        float middle = data[0] + width / 2;
        int s = 0;
        for (int i = 0; i < data.length / 2; i++) {
            data[i * 2] = (middle - (middle - data[i * 2]) * (1 - s * 0.05f));
            if ((i + 1) % step == 0) {
                s++;
            }
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            if (i % 2 == 0) {
                builder.append(data[i]).append(",");
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }
}
