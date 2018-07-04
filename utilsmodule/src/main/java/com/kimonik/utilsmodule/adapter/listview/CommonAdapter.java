package com.kimonik.utilsmodule.adapter.listview;

/**
 * * ===============================================================
 * name:             CommonAdapter
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2018/7/4
 * method:
 * <p>
 * <p>
 * description：适用于listview只有一种item类型时使用,是MultiItemTypeAdapter的一种特殊类型
 * history：
 * *==================================================================
 */


import android.content.Context;

import java.util.List;

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {

    /**
     * @param context  上下文
     * @param layoutId item的布局id，R.layout.***
     * @param datas    listview的数据源
     */
    public CommonAdapter(Context context, final int layoutId, List<T> datas) {
        super(context, datas);

//        添加item代理,即为当类型只有一种时的特殊情况
        addItemViewDelegate(new ItemViewDelegate<T>() {
            /**返回该类型item的布局id*/
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            /**指定位置的item是否应用该类型*/
            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            /**数据源与视图进行绑定*/
            @Override
            public void convert(ViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }
    /**对数据源与视图进行绑定*/
    protected abstract void convert(ViewHolder viewHolder, T item, int position);

}
