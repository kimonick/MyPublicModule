package com.kimonik.utilsmodule.adapter.listview;

/**
 * * ===============================================================
 * name:             ItemViewDelegateManager
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

import android.support.v4.util.SparseArrayCompat;

public class ItemViewDelegateManager<T> {
    /**类型存储系数数组*/
    SparseArrayCompat<ItemViewDelegate<T>> delegates = new SparseArrayCompat();

    /**listview 中item的type类型个数*/
    public int getItemViewDelegateCount() {
        return delegates.size();
    }

    /**按照稀疏数组中的数据个数为索引添加视图类型*/
    public ItemViewDelegateManager<T> addDelegate(ItemViewDelegate<T> delegate) {
        int viewType = delegates.size();
        //当前类型按照索引顺序进行存储
        if (delegate != null) {
            delegates.put(viewType, delegate);
//            viewType++;
        }
        return this;
    }
    /**自定义类型索引进行存储*/
    public ItemViewDelegateManager<T> addDelegate(int viewType, ItemViewDelegate<T> delegate) {
        if (delegates.get(viewType) != null) {
            throw new IllegalArgumentException(
                    "An ItemViewDelegate is already registered for the viewType = "
                            + viewType
                            + ". Already registered ItemViewDelegate is "
                            + delegates.get(viewType));
        }
        delegates.put(viewType, delegate);
        return this;
    }

    /**删除类型存储*/
    public ItemViewDelegateManager<T> removeDelegate(ItemViewDelegate<T> delegate) {
        if (delegate == null) {
            throw new NullPointerException("ItemViewDelegate is null");
        }
        int indexToRemove = delegates.indexOfValue(delegate);

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    /**按照item的存储类型进行删除*/
    public ItemViewDelegateManager<T> removeDelegate(int itemType) {
        int indexToRemove = delegates.indexOfKey(itemType);

        if (indexToRemove >= 0) {
            delegates.removeAt(indexToRemove);
        }
        return this;
    }

    /**获取item的类型*/
    public int getItemViewType(T item, int position) {
        int delegatesCount = delegates.size();
        for (int i = delegatesCount - 1; i >= 0; i--) {
            ItemViewDelegate<T> delegate = delegates.valueAt(i);
            //指定位置使用指定类型
            if (delegate.isForViewType(item, position)) {
                return delegates.keyAt(i);
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + " in data source");
    }

    /**对数据源与视图进行绑定*/
    public void convert(ViewHolder holder, T item, int position) {
        int delegatesCount = delegates.size();
        for (int i = 0; i < delegatesCount; i++) {
            ItemViewDelegate<T> delegate = delegates.valueAt(i);
            //该位置使用该类型的ItemViewDelegate
            if (delegate.isForViewType(item, position)) {
                delegate.convert(holder, item, position);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegateManager added that matches position=" + position + " in data source");
    }

    /**获取指定类型的布局id*/
    public int getItemViewLayoutId(int viewType) {
        return delegates.get(viewType).getItemViewLayoutId();
    }

    /**获取ItemViewDelegate的序号索引*/
    public int getItemViewType(ItemViewDelegate itemViewDelegate) {
        return delegates.indexOfValue(itemViewDelegate);
    }

    /**获取指定位置的数据源的视图类型*/
    public ItemViewDelegate getItemViewDelegate(T item, int position) {
        int delegatesCount = delegates.size();
        for (int i = delegatesCount - 1; i >= 0; i--) {
            ItemViewDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(item, position)) {
                return delegate;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + " in data source");
    }

    /**获取指定位置的布局id*/
    public int getItemViewLayoutId(T item, int position) {
        return getItemViewDelegate(item, position).getItemViewLayoutId();
    }
}
