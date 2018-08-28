package com.kimonik.mypublicmodule.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v13.view.DragStartHelper;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.iflytek.msc.MSCSessionInfo;
import com.kimonik.utilsmodule.mvp.Contract;
import com.kimonik.utilsmodule.utils.LUtils;

/**
 * * ===============================================================
 * name:             CustomViewPager
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2018/8/24
 * method:
 * <p>
 * <p>
 * description：
 * history：
 * *==================================================================
 */

public class CustomViewPager extends ViewGroup {
    public CustomViewPager(Context context) {
        this(context, null, 0);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomViewPager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private float maxVolecity, minVolecity;

    private void init() {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        maxVolecity = viewConfiguration.getScaledMaximumFlingVelocity();
        minVolecity = viewConfiguration.getScaledMinimumFlingVelocity();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
//        LUtils.e(CustomViewPager.class,"logflag--padding内边距-"+l);
//        LUtils.e(CustomViewPager.class,"logflag--padding内边距-"+t);
//        LUtils.e(CustomViewPager.class,"logflag--padding内边距-"+r);
//        LUtils.e(CustomViewPager.class,"logflag--padding内边距-"+b);
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            int width = view.getMeasuredWidth();
            int padding = view.getPaddingTop();
            LUtils.e(CustomViewPager.class, "logflag--padding内边距-" + padding);

            switch (i) {
                case 0:
                    view.layout(l, 0, r, b - t);
                    break;
                case 1:
                    view.layout(l - width, 0, l, b - t);
                    break;
                case 2:
                    view.layout(r, 0, r + width, b - t);
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
            }
//            view.layout(l, t, r, b);
        }

    }

    //  https://www.jb51.net/article/100638.htm
    private float startX, startY;
    private Scroller scroller = new Scroller(getContext());

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initOrResetVelocityTracker();
                velocityTracker.addMovement(event);
                //按下就停止动画
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                startX = event.getRawX();
                startY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:

                if (velocityTracker != null) {
                    velocityTracker.addMovement(event);
                }
                float dx = startX - event.getRawX();
                float dy = startY - event.getRawY();
                startX = event.getRawX();
                startY = event.getRawY();
                LUtils.e(CustomViewPager.class, "logflag-move事件--" + dx);

                if (!(getScrollX() < -1080 && dx < 0) && !(getScrollX() > 1080 && dx > 0)) {
                    if (getScrollX() + dx < -1080) {
                        scrollTo(-1080, 0);
                    } else if (getScrollX() + dx > 1080) {
                        scrollTo(1080, 0);
                    } else {
//                        if (Math.abs(dx)>100){
//                            scroller.startScroll(getScrollX(),0,(int)dx,0);
//                            invalidate();
//                        }else {
                        scrollBy((int) dx, 0);
//                        }
                    }
                } else if (dx < 0) {
                    scrollTo(-1080, 0);
                } else if (dx > 0) {
                    scrollTo(1080, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                //添加事件
                velocityTracker.addMovement(event);
                //计算1000毫秒内的加速度
                velocityTracker.computeCurrentVelocity(1000, maxVolecity);
                //获取x方向上的加速度
                float xVelocity = velocityTracker.getXVelocity();
                float yVelocity = velocityTracker.getYVelocity();
                if (Math.abs(xVelocity) > minVolecity) {
                    scroller.fling(getScrollX(), 0, (int) -xVelocity, 0, -1080, 1080, 0, 1920);
                    invalidate();
                    recycleVelocityTracker();
                } else {
                    float dx1 = startX - event.getRawX();
                    float dy1 = startY - event.getRawY();
                    if (getScrollX() < -1080) {
                        scrollTo(-1080, 0);
                    }
                    if (getScrollX() > 1080) {
                        scrollTo(1080, 0);
                    }
                    if (dx1 != 0) {
                        //计算时要根据getScrollX(),getScrollY()来确定当前初始点坐标,否则会
                        scroller.startScroll(getScrollX(), 0, (int) dx1, 0, 2000);
                        invalidate();
                    }
                }
                break;
        }

        return true;
    }

    /**
     * 回收加速度测试器，防止内存泄漏
     */
    private void recycleVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }

    /**
     * 手指加速度辅助类
     */
    private VelocityTracker velocityTracker;

    private void initOrResetVelocityTracker() {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        } else {
            velocityTracker.clear();
        }
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {//是否已经滚动完成
            LUtils.e(CustomViewPager.class, "logflag-getScrollX()--" + scroller.getCurrX());
            LUtils.e(CustomViewPager.class, "logflag-getScrollY()--" + scroller.getCurrY());
            scrollTo(scroller.getCurrX(), scroller.getCurrY());//获取当前值，startScroll（）初始化后，调用就能获取区间值
            postInvalidate();
        }
    }


}
