package com.kimonik.mypublicmodule.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.LruCache;
import android.view.View;

import com.kimonik.utilsmodule.utils.LUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * * ===============================================================
 * name:             PathTestView
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2018/8/8
 * method:
 * <p>
 * <p>
 * description：https://github.com/GcsSloop/AndroidNote/blob/master/CustomView/Advance/%5B08
 * %5DPath_Play.md
 * history：
 * *==================================================================
 */

public class PathTestView extends View {
    private PathMeasure pathMeasure;
    private Path path, segmentPath, trianglePath;
    private Paint paint, paintGreen;
    private float pos[], tan[];
    private Executor executor;
    private boolean isRun;
    private float totalength, currentLength = 0;
    private int step = 10;
    private float radius1 = 100f, radius2 = 200f;
    private float tanx;
    private List<PointF> list;
    private Matrix matrix;
    private float[] matrixValues;
    private float rorateDegress;
    private float  temp=0f;

    public PathTestView(Context context) {
        this(context, null, 0);
    }

    public PathTestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PathTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        matrixValues = new float[9];
        matrix = new Matrix();
        isRun = true;
        executor = Executors.newSingleThreadExecutor();
        pos = new float[2];
        tan = new float[2];
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);

        paintGreen = new Paint();
        paintGreen.setColor(Color.GREEN);
        paintGreen.setAntiAlias(true);
        paintGreen.setStyle(Paint.Style.FILL);


        trianglePath = new Path();
        path = new Path();
        path.moveTo(540f, 960f);
        path.addCircle(540f, 960f, 100f, Path.Direction.CCW);
        path.addCircle(540f, 960f, 200f, Path.Direction.CCW);

        segmentPath = new Path();
        segmentPath.moveTo(100, 500);
        segmentPath.lineTo(100, 200);

        pathMeasure = new PathMeasure(path, false);
        totalength = pathMeasure.getLength();
        LUtils.e(PathTestView.class, "logflag--PathTestView11-" + totalength);
//        pathMeasure.nextContour();
        LUtils.e(PathTestView.class, "logflag--PathTestView22-" + pathMeasure.getLength());
        list = new ArrayList<>();
        pathMeasure.getPosTan(temp, pos, tan);
//        for (int i = 1; i < 5; i++) {
//            //作用于path的当前线段
//            pathMeasure.getPosTan(totalength * i / 4, pos, tan);
//            LUtils.e(PathTestView.class, "logflag--PathTestViewpos-" + pos[0] + "---" + pos[1]);
//            LUtils.e(PathTestView.class, "logflag--PathTestViewtan-" + tan[0] + "---" + tan[1]);
//            LUtils.e(PathTestView.class, "logflag--PathTestViewtan???-" + Math.atan2(tan[1],
//                    tan[0]) * 180 / Math.PI);
//            PointF point = new PointF(pos[0], pos[1]);
//            list.add(point);
//        }
//1.0,0.0,0.0,
// 0.0,1.0,0.0,
// 0.0,0.0,1.0,
//        Matrix matrix=new Matrix();
//        float[] values=new float[9];
//        matrix.getValues(values);
//        StringBuilder builder=new StringBuilder();
//        for (int i = 0; i < 9; i++) {
//            builder.append(values[i]).append(",");
//        }
//        LUtils.e(PathTestView.class,"logflag-ssssss--"+builder.toString());


        //获取path的一个片段
        //返回值(boolean)	判断截取是否成功	true 表示截取成功，结果存入dst中，false 截取失败，不会改变dst中内容
//        startD	开始截取位置距离 Path 起点的长度	取值范围: 0 <= startD < stopD <= Path总长度
//        stopD	结束截取位置距离 Path 起点的长度	取值范围: 0 <= startD < stopD <= Path总长度
//        dst	截取的 Path 将会添加到 dst 中	注意: 是添加，而不是替换
//        startWithMoveTo	起始点是否使用 moveTo	用于保证截取的 Path 第一个点位置不变
        //当startwithmoveto为false时,新截取的路径起点会与原路径中点相连接
        pathMeasure.getSegment(0f, temp, segmentPath, true);


        initThread();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
//        canvas.drawCircle(pos[0], pos[1], 30f, paint);
//        canvas.drawLine(tanx, 960f, pos[0], pos[1], paint);
        canvas.drawPath(segmentPath,paint);
//        for (int i = 0; i < list.size(); i++) {
//            canvas.drawCircle(list.get(i).x, list.get(i).y, 5 * (i + 1), paintGreen);
//        }
        canvas.save();
//        canvas.setMatrix(matrix);
        canvas.rotate(rorateDegress,pos[0],pos[1]);
        //绘制时平移至绘制图形的中心,则会沿着路径均匀绘制
        canvas.translate(0,-20);
        canvas.drawPath(trianglePath, paint);
        canvas.restore();
//        canvas.setMatrix(null);
    }


    private void initThread() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (isRun) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (currentLength + step > totalength) {
                        currentLength = 0;
                    } else {
                        currentLength += step;
                    }
                    pathMeasure.getPosTan(currentLength, pos, tan);
                    float s = tan[1] / tan[0] * radius1;
                    tanx = 540f - (float) Math.sqrt(s * s + radius1 * radius1);
//                    pathMeasure.getMatrix(currentLength, matrix, PathMeasure.TANGENT_MATRIX_FLAG
//                            | PathMeasure.POSITION_MATRIX_FLAG);
//                    matrix.getValues(matrixValues);
//                    matrixValues[2] = 0f;
//                    matrixValues[5] = 0f;
//                    matrix.setValues(matrixValues);
                    rorateDegress= (float) (Math.atan2(tan[1],tan[0])*180/Math.PI)+90f;

                    trianglePath.reset();
                    trianglePath.moveTo(pos[0], pos[1]);
                    trianglePath.lineTo(pos[0]-20, pos[1] +40);
                    trianglePath.lineTo(pos[0] + 20, pos[1]+40);
                    trianglePath.close();
                    postInvalidate();

                }
            }
        });
    }


//    @Override
//    protected void onWindowVisibilityChanged(int visibility) {
//        if (visibility == VISIBLE) {
//            isRun = true;
//            initThread();
//        } else {
//            isRun = false;
//        }
//        super.onWindowVisibilityChanged(visibility);
//    }
//    (x-a)^2+(y-b)^2=9
//    (x-c)^2+(y-d)^2=9
    //-2ax+a^2-2by+b^2=-2cx+c^2-2dy+d^2
    //(2c-2a)x+(2d-2b)y=c^2+d^2-a^2-b^2
//
//    @Override
//    protected void onDetachedFromWindow() {
//        isRun = false;
//        super.onDetachedFromWindow();
//    }
}
