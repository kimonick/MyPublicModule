package com.kimonik.utilsmodule.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * * ===============================================================
 * name:             BitmapUtils
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2017/9/14
 * description： 图像压缩工具类
 * history：
 * *==================================================================
 */

public class BitmapUtils {


    private BitmapUtils() {
    }

    /**
     * 压缩图片-->尺寸压缩-->将图片压缩到指定宽高,同时缩小bitmap的存储大小
     *
     * 非等比压缩,图片可能存在拉伸
     * @param inBitmap  要压缩的bitmap
     * @param newWidth  新的宽度
     * @param newHeight 新的高度
     * @return 指定宽高的bitmap, 原bitmap已回收
     */
    public static Bitmap getReduceBitmap(Bitmap inBitmap, int newWidth, int newHeight) {
        Bitmap outBitmap;
        float width = inBitmap.getWidth();
        float height = inBitmap.getHeight();

        Matrix matrix = new Matrix();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        matrix.postScale(scaleWidth, scaleHeight);

        outBitmap = Bitmap.createBitmap(inBitmap, 0, 0, (int) width,
                (int) height, matrix, true);

        inBitmap.recycle();
        return outBitmap;

    }

    /**
     * 压缩图片-->质量压缩-->压缩图片的保存体积,图片本身的宽高不变
     * <p>
     * 质量压缩适合将图片保存到本地,再次读取压缩后的图片时,bitmap的大小没有变化,所占内存与原图片相同
     *
     * @param inBitmap 需要压缩的图片
     * @param outSize  压缩后的图片大小,单位bit,例:压缩后图片大小为100kb应该传入100*1024
     * @return 压缩后的图片, 原图片已回收
     */
    public static Bitmap getCompressBitmap(Bitmap inBitmap, int outSize) {
        Bitmap outBitmap;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        inBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int originalSize = baos.toByteArray().length;
        //注意百分比小于1的情况
        int options = 100 * outSize / originalSize;//将图片压缩到100kb的压缩比
        baos.reset();
        // 这里压缩options%，把压缩后的数据存放到baos中
        inBitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        outBitmap = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length);
        inBitmap.recycle();
        return outBitmap;
    }

    /**
     * 从指定的图像路径获取位图
     *
     * @param imgPath 图片路径
     * @return bitmap
     */
    public static Bitmap getBitmap(String imgPath) {
        // Get bitmap through image path
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        // Do not compress
        newOpts.inSampleSize = 1;
        newOpts.inPreferredConfig = Config.RGB_565;
        return BitmapFactory.decodeFile(imgPath, newOpts);
    }

    /**
     * 获取bitmap的大小
     *
     * @param bitmap 目标bitmap
     * @return bitmap的大小, 单位bit
     */
    @SuppressLint("ObsoleteSdkInt")
    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            return bitmap.getAllocationByteCount();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
    }

    /**
     * 压缩图像的大小，这将修改图像宽度/高度。用于获取缩略图
     * 压缩图片到指定宽高,等比压缩,不改变宽高比
     *
     * @param image  bitmap
     * @param pixelW target pixel of width
     * @param pixelH target pixel of height
     * @return bitmap
     */
    public Bitmap ratio(Bitmap image, float pixelW, float pixelH) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, os);
        // 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
        if (os.toByteArray().length / 1024 > 1024) {
            os.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, os);// 这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream is;
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        //  开始读入图片，此时把options.inJustDecodeBounds 设回true，即只读边不读内容
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Config.RGB_565;
        Bitmap bitmap;
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > pixelW) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / pixelW);
        } else if (w < h && h > pixelH) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / pixelH);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        is = new ByteArrayInputStream(os.toByteArray());
        bitmap = BitmapFactory.decodeStream(is, null, newOpts);
        // 压缩好比例大小后再进行质量压缩
        // return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return bitmap;
    }
//
//    /**
//     * 按质量压缩，并将图像生成指定的路径
//     *
//     * @param image   Bitmap
//     * @param outPath 输出路径
//     * @param maxSize 目标将被压缩到小于这个大小（KB）。
//     * @throws IOException io异常
//     */
//    public void compressAndGenImage(Bitmap image, String outPath, int maxSize) throws IOException {
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        // scale
//        int options = 100;
//        // Store the bitmap into output stream(no compress)
//        image.compress(Bitmap.CompressFormat.JPEG, options, os);
//        // Compress by loop
//        while (os.toByteArray().length / 1024 > maxSize) {
//            // Clean up os
//            os.reset();
//            // interval 10
//            options -= 10;
//            image.compress(Bitmap.CompressFormat.JPEG, options, os);
//        }
//
//        // Generate compressed image file
//        FileOutputStream fos = new FileOutputStream(outPath);
//        fos.write(os.toByteArray());
//        fos.flush();
//        fos.close();
//    }
//
//    /**
//     * 按质量压缩，并将图像生成指定的路径
//     *
//     * @param imgPath     位图路径
//     * @param outPath     输出路径
//     * @param maxSize     目标将被压缩到小于这个大小（KB）。
//     * @param needsDelete 是否压缩后删除原始文件
//     * @throws IOException io异常
//     */
//    public void compressAndGenImage(String imgPath, String outPath, int maxSize, boolean needsDelete)
//            throws IOException {
//        compressAndGenImage(getBitmap(imgPath), outPath, maxSize);
//
//        // Delete original file
//        if (needsDelete) {
//            File file = new File(imgPath);
//            if (file.exists()) {
//                file.delete();
//            }
//        }
//    }


}

