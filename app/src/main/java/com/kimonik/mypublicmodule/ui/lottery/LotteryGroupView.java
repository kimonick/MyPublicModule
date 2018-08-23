package com.kimonik.mypublicmodule.ui.lottery;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.kimonik.mypublicmodule.R;
import com.kimonik.mypublicmodule.app.MApp;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * * ===============================================================
 * name:             LotteryGroupView
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2018/8/23
 * method:
 * <p>
 * <p>
 * description：抽奖view静态视图展示view
 * history：
 * *==================================================================
 */

public class LotteryGroupView extends FrameLayout {
    /**
     * 背景图
     */
    private Bitmap bitmapBack, bitmapStart;
    /**
     * 设备屏幕宽度
     */
    private int width = MApp.DEVICE_WIDTH;
    /**
     * 背景缩放绘制区域
     */
    private Rect rectFBack;
    /**
     * 抽奖区域中心半径
     */
    private float radius, centerX, centerY;
    /**
     * 线程池
     */
    private Executor executor;
    private Paint paintBack;
    private String colorBack = "#417CF4", deng1 = "#77C85D", deng2 = "#DBCD72", rectColor =
            "#368DEA",
            textColor = "#6BA6B1", arcColor1 = "#FFFFFF", arcColor2 = "#DBFDFF";
    private RectF rectFStart;
    private PrizeView prizeView;

    public LotteryGroupView(@NonNull Context context) {
        this(context, null, 0);
    }

    public LotteryGroupView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LotteryGroupView(@NonNull Context context, @Nullable AttributeSet attrs, int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LotteryGroupView(@NonNull Context context, @Nullable AttributeSet attrs, int
            defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        executor = Executors.newFixedThreadPool(2);
        //设置执行onDraw()方法
        setWillNotDraw(false);
        bitmapBack = BitmapFactory.decodeResource(getResources(), R.drawable.beijing);
        bitmapStart = BitmapFactory.decodeResource(getResources(), R.drawable.kaishichoujiang);
        radius = width * 0.31f;
        centerX = width * 0.525f;
        centerY = width * 0.55f;

        rectFBack = new Rect(0, 0, width, width);
        paintBack = initPaint(colorBack, Paint.Style.FILL, 6);
        rectFStart = new RectF(width * 0.445f, width * 0.449f, width * 0.605f, width * 0.651f);

        FlickerView flickerView = new FlickerView(getContext());
        prizeView = new PrizeView(getContext());
        flickerView.setExecutor(executor);
        prizeView.setExecutor(executor);
        addView(flickerView);
        addView(prizeView);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmapBack, null, rectFBack, null);
        canvas.drawCircle(centerX, centerY, radius, paintBack);

    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        //在子view绘制之前绘制
        super.dispatchDraw(canvas);
        drawStart(canvas);
        //在子view绘制之后绘制

    }

    private void drawStart(Canvas canvas) {
        canvas.drawBitmap(bitmapStart, null, rectFStart, null);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(width, width);
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            //这个很重要，没有就不显示子view
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public static Paint initPaint(String color, Paint.Style style, int width) {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor(color));
        paint.setAntiAlias(true);
        paint.setStyle(style);
        paint.setStrokeWidth(width);
        return paint;
    }


    /**指定区域点击执行动画*/
    private boolean reset=false;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (rectFStart.contains(event.getX(), event.getY())) {
                    reset = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (reset&&prizeView.getRotateTime()==0){//一次动画未执行完毕前不允许执行下一次
                    int rotateTime = (int) ((3 + Math.random()) * 1000);
                    prizeView.setRotateTime(rotateTime);
                    prizeView.startRotate();
                    reset=false;
                }
                break;
        }
        //消费掉点击事件,可按照需要设置
        return true;
    }
}
