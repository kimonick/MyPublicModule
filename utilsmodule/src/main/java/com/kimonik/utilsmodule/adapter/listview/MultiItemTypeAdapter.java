package com.kimonik.utilsmodule.adapter.listview;

/**
 * * ===============================================================
 * name:             MultiItemTypeAdapter
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


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;


import com.kimonik.utilsmodule.utils.LUtils;

import java.util.List;

public class MultiItemTypeAdapter<T> extends BaseAdapter {
    /**上下文*/
    private Context mContext;
    /**数据源*/
    private List<T> mDatas;

    /**
     * item类型代理管理者
     */
    private ItemViewDelegateManager mItemViewDelegateManager;


    public MultiItemTypeAdapter(Context context, List<T> datas) {
        this.mContext = context;
        this.mDatas = datas;
        /**对类型管理者进行初始化*/
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    /**添加list的view的类型代理*/
    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    /**使用类型视图代理管理者*/
    private boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

    /**
     * 返回listview的item类型种类个数
     *
     * @return  默认返回1
     */
    @Override
    public int getViewTypeCount() {

//        if (useItemViewDelegateManager()){
//            return mItemViewDelegateManager.getItemViewDelegateCount();
//        }
//        return super.getViewTypeCount();
        //多类型返回多类型个数,单类型时返回1
        return useItemViewDelegateManager() ? mItemViewDelegateManager.getItemViewDelegateCount() :
                super.getViewTypeCount();
    }

    /**获取视图类型*/
    @Override
    public int getItemViewType(int position) {
//        if (useItemViewDelegateManager()) {
//            return mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
//        }
//        return super.getItemViewType(position);
        //返回当前位置item的类型
        return useItemViewDelegateManager()?mItemViewDelegateManager.getItemViewType(mDatas.get(position), position):
                super.getItemViewType(position);
    }

    /**获取视图,convertView在返回时会调用getItemViewType(int position)函数来确定是否有
     * 可复用的当前类型item*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(mDatas.get(position), position);
        //获取item布局id
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        ViewHolder viewHolder = null;
        if (convertView == null) {
            View itemView = LayoutInflater.from(mContext).inflate(layoutId, parent,
                    false);
            viewHolder = new ViewHolder(mContext, itemView, parent, position);
            viewHolder.mLayoutId = layoutId;
            onViewHolderCreated(viewHolder, viewHolder.getConvertView());
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mPosition = position;
        }


        convert(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();
    }

    /**数据源与视图进行绑定*/
    protected void convert(ViewHolder viewHolder, T item, int position) {
        mItemViewDelegateManager.convert(viewHolder, item, position);
    }

    public void onViewHolderCreated(ViewHolder holder, View itemView) {
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    /**获取数据源对象*/
    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
