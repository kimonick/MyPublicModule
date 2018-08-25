package com.kimonik.mypublicmodule.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
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
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomViewPager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
//            int width = view.getMeasuredWidth();
//            int padding=view.getPaddingTop();
//            LUtils.e(CustomViewPager.class,"logflag--padding内边距-"+padding);
//
//            switch (i) {
//                case 0:
//                    view.layout(l, 0, r, b-t);
//                    break;
//                case 1:
//                    view.layout(l - width, 0, l, b-t);
//                    break;
//                case 2:
//                    view.layout(r, 0, r + width, b-t);
//                    break;
//                case 3:
//                    break;
//                case 4:
//                    break;
//                case 5:
//                    break;
//                case 6:
//                    break;
//                case 7:
//                    break;
//            }
            view.layout(l, t, r, b);
        }

    }

    private float startX,startY;
    private Scroller scroller=new Scroller(getContext());
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                startX=event.getRawX();
                startY=event.getRawY();
                LUtils.e(CustomViewPager.class,"logflag-按下x偏移量--1?"+startX);
                LUtils.e(CustomViewPager.class,"logflag-按下y偏移量--1?"+startY);

                break;
            case MotionEvent.ACTION_MOVE:
//                float dx=startX-event.getX();
//                float dy=startY-event.getY();
//                startX=event.getX();
//                startY=event.getY();
//
//                if (!(getScrollX()<-1080&&dx<0)&&!(getScrollX()>1080&&dx>0)){
//                    scrollBy((int) dx, (int) dy);
//                }
                break;
            case MotionEvent.ACTION_UP:
                float dx1=startX-event.getRawX();
                float dy1=startY-event.getRawY();
                LUtils.e(CustomViewPager.class,"logflag-偏移量--1?"+dx1);
                LUtils.e(CustomViewPager.class,"logflag-偏移量--2?"+dy1);
                LUtils.e(CustomViewPager.class,"logflag-开始x偏移量--2?"+startX);
                LUtils.e(CustomViewPager.class,"logflag-开始y偏移量--2?"+startY);

//                scroller.startScroll((int) startX,(int)startY,(int)dx1,(int)dy1,2000);
//                invalidate();
                break;
        }

        return true;
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {//是否已经滚动完成
            LUtils.e(CustomViewPager.class,"logflag-getScrollX()--"+scroller.getCurrX());
            LUtils.e(CustomViewPager.class,"logflag-getScrollY()--"+scroller.getCurrY());
            scrollTo(scroller.getCurrX(), scroller.getCurrY());//获取当前值，startScroll（）初始化后，调用就能获取区间值
            postInvalidate();
        }
    }


}
