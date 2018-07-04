package com.kimonik.utilsmodule.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.kimonik.utilsmodule.R;

/**
 * * ==================================================
 * name:            ImageGlideUtils
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/7
 * description：   glide加载图片工具类
 * history：
 * * ==================================================
 */

public class ImageGlideUtils {
    /**
     * 加载圆形网络图片
     */
    public static void loadCircularImage(ImageView view, String url) {
        //new RoundedCorners(2000)//圆角举行显示
        //CenterInside()//居中内部显示,原图显示,原图过大则会缩小??,如果图像的尺寸匹配或比目标的尺寸小，
        // 则返回图像的原始尺寸，再加上{@link android.widget。 图像视图。
        // 比例类型＃CENTER INSIDE}以便将其居中在Target中。
        // 如果不是，则缩放它以使图像的一个维度等于给定维度，另一维度小于给定维度（保持图像的宽高比）
        //FiterCenter()//宽度充满等比缩放居中显示,均匀地缩放图像（保持图像的宽高比），
        // 以便图像的其中一个尺寸等于给定尺寸，另一个尺寸小于给定尺寸
        //CenterCrop()//缩放图像，使图像的宽度与给定宽度相匹配，并且图像的高度大于给定高度，
        // 反之亦然，然后裁剪较大的尺寸以匹配给定的尺寸。 不保持图像的宽高比
        //CircleCrop()//显示圆形图片
//        RequestOptions options = RequestOptions.bitmapTransform(new RoundedCorners(2000));
//        .circleCropTransform()//加载圆形图片
        RequestOptions options = new RequestOptions()
//                .centerCrop()//显示不全,显示原图??
                .placeholder(R.drawable.img_default)//占位图片
//                .error(R.drawable.img_default)//架子啊错误时显示的图片
//                .priority(Priority.HIGH)//图片优先级
//                .diskCacheStrategy(DiskCacheStrategy.NONE)//磁盘缓存策略,none不适用磁盘缓存
//                .fitCenter()//居中缩略显示
//                .centerInside()//居中缩略显示,比例不变
//                .circleCrop()//显示圆形图片,居中裁剪
//                .optionalCenterCrop()
        //                .override(500,500)//设置加载图片的大小

//                .skipMemoryCache(true)//不使用内存缓存
                ;

        Glide.with(view.getContext())
                .load(url)
                .apply(options)
                .transition(new DrawableTransitionOptions().crossFade(500))//淡入淡出效果
                .into(view);
    }


}
