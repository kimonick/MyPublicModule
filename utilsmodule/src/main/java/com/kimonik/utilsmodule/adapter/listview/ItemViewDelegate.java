package com.kimonik.utilsmodule.adapter.listview;

/**
 * * ===============================================================
 * name:             ItemViewDelegate
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2018/7/4
 * method:
 * <p>
 * <p>
 * description：
 * history：
 * *==================================================================
 */

public interface ItemViewDelegate<T> {

    /**
     * 获取item布局id R.layout.***
     *
     * @return ""
     */
    public abstract int getItemViewLayoutId();

    /**指定位置是否使用该类型*/
    public abstract boolean isForViewType(T item, int position);

    /**数据源与视图进行绑定*/
    public abstract void convert(ViewHolder holder, T t, int position);


}
