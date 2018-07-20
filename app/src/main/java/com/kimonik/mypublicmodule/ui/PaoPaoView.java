package com.kimonik.mypublicmodule.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.kimonik.utilsmodule.utils.LUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * * ===============================================================
 * name:             PaoPaoView
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2018/7/20
 * method:
 * <p>
 * <p>
 * description：
 * history：
 * *==================================================================
 */

public class PaoPaoView extends View {

    /**
     * 画笔
     */
    private Paint paint;
    /**
     * 线程控制
     */
    private boolean toggle = true;
    /**
     * 宽高
     */
    private int width, height;
    /**
     * 当前圆心坐标
     */
    private float curX, curY;
    /**
     * 移动位移
     */
    private int  offset=30;
//    private PointF pointF = new PointF(0, 5);
    /**泡泡集合*/
    private List<PointF> list,listOffset;
    /**泡泡出现的时间间隔,毫秒*/
    private int interval=200;
    /**时间片段计时*/
    private int count=0;
    /**复用记录*/
    private Set<Integer>  set;
    /**边界判断值*/
    private int left=-50,right,top=-50;
    /**随机数生成*/
    private Random random=new Random();
    private double min=Math.PI/6;
    private double max=2*Math.PI/3;
    private RadialGradient radialGradient;


    public PaoPaoView(Context context) {
        this(context, null, 0);
    }

    public PaoPaoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaoPaoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PaoPaoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        paint = initPaint("#21AC3A", 5, Paint.Style.FILL);
        list=new ArrayList<>();
        listOffset=new ArrayList<>();
        set=new HashSet<>();
        initRefresh();
        //线性渐变着色器相关讲解--https://juejin.im/post/596baf5f6fb9a06bb15a3df9
        //tile：端点范围之外的着色规则，类型是 TileMode。TileMode 一共有 3 个值可选：
        // CLAMP, MIRROR 和 REPEAT。CLAMP （夹子模式？？？算了这个词我不会翻）
        // 会在端点之外延续端点处的颜色；MIRROR 是镜像模式；REPEAT 是重复模式。

        Shader shader = new LinearGradient(100, 100, 500, 500, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.REPEAT);

        //辐射渐变
        radialGradient=new RadialGradient(540, 960, 960, new int[] {
                Color.parseColor("#368DEA"),
                Color.parseColor("#4887E5"),
                Color.parseColor("#5998E8"),
                Color.parseColor("#5192E2"),
                Color.parseColor("#6CA8E7"),
                Color.parseColor("#70A8ED"),
                Color.parseColor("#E5A8E9"),
                Color.parseColor("#F1F0FE"),
                Color.parseColor("#78E97F"),
                Color.parseColor("#C26295"),
                Color.parseColor("#8B81E2"),
//                Color.YELLOW,
//                Color.GREEN,
//                Color.parseColor("#21AC3A"),Color.CYAN,
//                Color.BLUE,Color.DKGRAY
        }, null,
                Shader.TileMode.REPEAT);
        paint.setShader(radialGradient);

    }

    private void initRefresh() {
        new Thread() {
            @Override
            public void run() {
                while (toggle) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < list.size(); i++) {
                        if (set.contains(i)) continue;
                        if (list.get(i).x<left||list.get(i).x>right||list.get(i).y<top){
                            set.add(i);
                        }
                    }

                    if (count*50%interval==0){
                        if (set.isEmpty()){
                            PointF pointF=new PointF(width/2,height);
//                            double  degree=Math.PI*Math.random();
                            double  degree=min+max*Math.random();
                            PointF pointFOffset=new PointF((float) (offset*Math.cos(degree)),(float) Math.abs(offset*Math.sin(degree)));
                            list.add(pointF);
                            listOffset.add(pointFOffset);
                        }else{//复用已经超出边界的数据
                            Iterator<Integer> iterator=set.iterator();
                            int temp;
                            if (iterator.hasNext()){
                                temp=iterator.next();
                                list.get(temp).x=width/2;
                                list.get(temp).y=height;
                                double  degree=min+max*Math.random();
                                listOffset.get(temp).x=(float) (offset*Math.cos(degree));
                                listOffset.get(temp).y=(float) Math.abs(offset*Math.sin(degree));
                                set.remove(temp);
                            }
                        }

                    }
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).x+=listOffset.get(i).x;
                        list.get(i).y-=listOffset.get(i).y;
                    }
//                    curX += pointF.x;
//                    curY -= pointF.y;
                    count++;
                    LUtils.e(PaoPaoView.class,"logflag-集合的大小--"+list.size());

                    postInvalidate();
                }
            }
        }.start();
    }

    private Paint initPaint(String color, float strokeWidth, Paint.Style style) {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor(color));
        paint.setStrokeWidth(strokeWidth);
        paint.setAntiAlias(true);
        paint.setStyle(style);
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {

//        canvas.drawCircle(540, 960, 960, paint);

        for (int i = 0; i < list.size(); i++) {
            if (set.contains(i))  continue;
            canvas.drawCircle(list.get(i).x, list.get(i).y, 50, paint);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        right=width+50;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDetachedFromWindow() {
        toggle=false;
        super.onDetachedFromWindow();
    }


}
