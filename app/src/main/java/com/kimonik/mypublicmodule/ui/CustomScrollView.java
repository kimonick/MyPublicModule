package com.kimonik.mypublicmodule.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.Scroller;

import com.kimonik.mypublicmodule.app.MApp;
import com.kimonik.utilsmodule.utils.LUtils;

/**
 * * ===============================================================
 * name:             CustomScrollView
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


/**
 * author : zhongwr on 2016/7/20
 */
public class CustomScrollView extends ViewGroup {
    private static final String TAG = "CustomScrollView";
    private Context mContext;
    private int mScreenHeight;
    private int totalHeight;
    private Scroller mScroller;

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomScrollView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mScreenHeight = MApp.DEVICE_HEIGHT;
        mScroller = new Scroller(mContext);
    }

    /***
     * 获取真实的宽高 比如200px
     *
     * @param widthMeasureSpec "
     * @return "
     */
    public int measureRealWidth(int widthMeasureSpec) {
        int result = 200;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int realWidth = MeasureSpec.getSize(widthMeasureSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
                //MeasureSpec.EXACTLY：精确值模式： 控件的layout_width或layout_heiht指定为具体值，比如200dp，或者指定为match_parent（占据父view的大小），系统返回的是这个模式
                result = realWidth;
                Log.d(TAG, "EXACTLY result " + result);
                break;
            case MeasureSpec.AT_MOST:
                // MeasureSpec.AT_MOST: 最大值模式，控件的layout_width或layout_heiht指定为wrap_content时，控件大小一般随着控件的子控件或内容的变化而变化，此时控件的尺寸不能超过父控件
                result = Math.min(result, realWidth);
                Log.d(TAG, "AT_MOST result " + result);
                break;
            case MeasureSpec.UNSPECIFIED:
                // MeasureSpec.UNSPECIFIED:不指定其大小测量模式，通常在绘制定义view的时候才会使用，
                // 即多大由开发者在onDraw()的时候指定大小
                result = realWidth;
                Log.d(TAG, "UNSPECIFIED result " + result);
                break;
        }
        return result;
    }

    /***
     * @param widthMeasureSpec  系统测量的宽 一共是32位的 高2位代表模式 低30位表示大小
     * @param heightMeasureSpec 系统测量的高 一共是32位的 高2位代表模式 低30位表示大小
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        totalHeight=0;
        measureChildren(widthMeasureSpec,heightMeasureSpec);
////        /***自身宽*/
        int measureSelfWidth = measureRealWidth(widthMeasureSpec);
        int childCount = getChildCount();
        LUtils.e(CustomScrollView.class,"logflag-子view参数--个数"+childCount);
        for (int i = 0; i < childCount; i++) {
            totalHeight +=getChildAt(i).getMeasuredHeight();
        }
        LUtils.e(CustomScrollView.class,"logflag-子view参数--"+
        getChildAt(0));

        //设置viewGroup的宽高，也可以在onlayout中通过layoutParams设置
        setMeasuredDimension(measureSelfWidth, totalHeight);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
//        LayoutParams lp = getLayoutParams();
//        totalHeight = getScreenSize(mContext).heightPixels * childCount;
//        lp.height = totalHeight;//设置viewgroup总高度
//        setLayoutParams(lp);
        int total=0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int height=childView.getMeasuredHeight();
            LUtils.e(CustomScrollView.class,"logflag-实际高度--"+height);
            childView.layout(l, total, r, total+height);
            total+=height;
        }
    }

    private float lastDownY;
    private float mScrollStart;
    private float mScrollEnd;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastDownY = event.getY();
                mScrollStart = getScrollX();
                LUtils.e(CustomScrollView.class,"logflag-自定义scroll--001"+event.getX());
                LUtils.e(CustomScrollView.class,"logflag-自定义scroll--002"+event.getY());

                break;
            case MotionEvent.ACTION_MOVE:
//                float currentY = event.getY();
//                float dy;
//                dy = lastDownY - currentY;
//                if (getScrollY() < 0) {
//                    dy = 0;
//                    //最顶端，超过0时，不再下拉，要是不设置这个，getScrollY一直是负数
////                    setScrollY(0);
//                } else if (getScrollY() > getHeight() - mScreenHeight) {
//                    dy = 0;
//                    //滑到最底端时，不再滑动，要是不设置这个，getScrollY一直是大于getHeight() - mScreenHeight的数，无法再滑动
////                    setScrollY(getHeight() - mScreenHeight);
//                }
//                scrollBy(0, (int) dy);
//                //不断的设置Y，在滑动的时候子view就会比较顺畅
//                lastDownY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
//                mScrollEnd = getScrollY();
//                int dScrollY = (int) (mScrollEnd - mScrollStart);
//                if (mScrollEnd < 0) {// 最顶端：手指向下滑动，回到初始位置
//                    Log.d(TAG, "mScrollEnd < 0" + dScrollY);
//                    mScroller.startScroll(0, getScrollY(), 0, -getScrollY());
//                } else if (mScrollEnd > getHeight() - mScreenHeight) {//已经到最底端，手指向上滑动回到底部位置
//                    Log.d(TAG, "getHeight() - mScreenHeight - (int) mScrollEnd " + (getHeight() - mScreenHeight - (int) mScrollEnd));
//                    mScroller.startScroll(0, getScrollY(), 0, getHeight() - mScreenHeight - (int) mScrollEnd);
//                }
//                postInvalidate();// 重绘执行computeScroll()
                LUtils.e(CustomScrollView.class,"logflag-自定义scroll--003"+event.getX());
                LUtils.e(CustomScrollView.class,"logflag-自定义scroll--004"+event.getY());
                break;
        }
        return true;//需要返回true否则down后无法执行move和up操作
    }

    /**
     * Scroller只是个计算器，提供插值计算，让滚动过程具有动画属性，但它并不是UI，也不是滑动辅助UI运动，反而是单纯地为滑动提供计算
     * 需要invalidate()之后才会调用,这个方法在onDraw()中调用
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        Log.d(TAG, "mScroller.getCurrY() " + mScroller.getCurrY());
        if (mScroller.computeScrollOffset()) {//是否已经滚动完成
            scrollTo(0, mScroller.getCurrY());//获取当前值，startScroll（）初始化后，调用就能获取区间值
            postInvalidate();
        }
    }



}
