package com.kimonik.utilsmodule.ui.recylerview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * * ===============================================================
 * name:             GridSpacingItemDecoration
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2017/12/18
 * description：  recyclerview自定义分割线
 * 通过设置recyclerview的纯色背景跟设置偏移量来实现分割线
 * 另一种方式通过,parent.getChildAt(i).getHeight();
 * parent.getChildAt(i).getTop();
 * parent.getChildAt(i).getLeft();
 * parent.getChildAt(i).getWidth();
 * 获取item相对recyclerview的上和左边距,和实际宽高来绘制分割线
 * 第三种,直接在item中设置好分割线,不需要操作recyclerview
 * history：
 * *==================================================================
 */


public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    public GridSpacingItemDecoration() {
    }

    /**
     * 设置item的偏移量,空出父控件的背景
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        if (position == 0) {
            outRect.left = 0;
            outRect.right = 0;
            outRect.bottom = 0;
            outRect.top = 0;
        } else if (position % 2 == 1) {
            outRect.left = 0;
            outRect.right = 3;
            outRect.bottom = 3;
            outRect.top = 0;
        } else {
            outRect.left = 0;
            outRect.right = 0;
            outRect.bottom = 3;
            outRect.top = 0;
        }
    }

    /**
     * 具体的分割线在这里设置
     *
     * @param c      RecyclerView的画布--绘制内容可能被item覆盖
     * @param parent RecyclerView
     * @param state  状态
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        c.drawColor(Color.parseColor("#dddddd"));

        super.onDraw(c, parent, state);
    }


}
