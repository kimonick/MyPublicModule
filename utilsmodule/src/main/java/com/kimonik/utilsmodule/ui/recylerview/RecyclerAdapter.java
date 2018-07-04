package com.kimonik.utilsmodule.ui.recylerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * * ===============================================================
 * name:             RecyclerAdapter
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2018/3/30
 * method:
 * <p>
 * <p>
 * description：适配器,多类型实现复杂布局的实现
 * history：
 * *==================================================================
 */
public class RecyclerAdapter<T> extends RecyclerView.Adapter {

//    private final static int HEAD_COUNT = 1;
//    private final static int FOOT_COUNT = 1;

    private final static int TYPE_HEAD = 0;
    private final static int TYPE_CONTENT = 1;
    private final static int TYPE_FOOTER = 2;

    private List<T> list;
    private Context context;
    private int resHeader;
    private int resFooter;
    private int resItem;

    /**13963626754
     * 构造函数
     */
    public RecyclerAdapter(List<T> list, Context context, int resHeader, int resFooter, int resItem) {
        this.list = list;
        this.context = context;
        this.resHeader = resHeader;
        this.resFooter = resFooter;
        this.resItem = resItem;
    }

    private int getContentSize() {
        return list.size();
    }

    private boolean isHead(int position) {
        return position == 0;
    }

    private boolean isFoot(int position) {
        return position == getContentSize() + 1;
    }

    @Override
    public int getItemViewType(int position) {

        if (isHead(position)) { // 头部
            return TYPE_HEAD;
        } else if (isFoot(position)) { // 尾部
            return TYPE_FOOTER;
        } else {
            return TYPE_CONTENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CONTENT) {//内容视图
            View itemView = LayoutInflater.from(context).inflate(resItem, parent, false);
            return new RecyclerAdapter.ContentHolder(itemView);
        } else if (resHeader!=-1&&viewType == TYPE_HEAD) {//头部视图
            View itemView = LayoutInflater.from(context).inflate(resHeader, parent, false);
            return new RecyclerAdapter.HeadHolder(itemView);
        } else if (resFooter!=-1&&viewType == TYPE_FOOTER){//底部视图
            View itemView = LayoutInflater.from(context).inflate(resFooter, parent, false);
            return new RecyclerAdapter.FootHolder(itemView);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecyclerAdapter.ContentHolder) { // 内容
            RecyclerAdapter.ContentHolder myHolder = (RecyclerAdapter.ContentHolder) holder;
            //内容视图相关设置
        } else if (holder instanceof RecyclerAdapter.HeadHolder) { // 头部

        } else { // 尾部

        }
    }

    @Override
    public int getItemCount() {
        int foot=0;
        int head=0;
        if (resFooter!=-1){
            foot=1;
        }
        if (resHeader!=-1){
            head=1;
        }
        return list.size() + head + foot;
    }

    // 头部
    private class HeadHolder extends RecyclerView.ViewHolder {
        public HeadHolder(View itemView) {
            super(itemView);
        }
    }

    // 内容--视图
    private class ContentHolder extends RecyclerView.ViewHolder {
        TextView itemText;

        public ContentHolder(View itemView) {
            super(itemView);
        }
    }

    // 尾部
    private class FootHolder extends RecyclerView.ViewHolder {
        public FootHolder(View itemView) {
            super(itemView);
        }
    }

}