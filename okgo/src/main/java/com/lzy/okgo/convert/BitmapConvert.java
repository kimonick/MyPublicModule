/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lzy.okgo.convert;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：16/9/11
 * 描    述：字符串的转换器
 * 修订历史：
 * ================================================
 */
public class BitmapConvert implements Converter<Bitmap> {

    private int maxWidth;
    private int maxHeight;
    /**默认配置为argb8888*/
    private Bitmap.Config decodeConfig;
    /**默认配置等比缩放,保持宽高比*/
    private ImageView.ScaleType scaleType;

    public BitmapConvert() {
        this(1000, 1000, Bitmap.Config.ARGB_8888, ImageView.ScaleType.CENTER_INSIDE);
    }

    public BitmapConvert(int maxWidth, int maxHeight) {
        this(maxWidth, maxHeight, Bitmap.Config.ARGB_8888, ImageView.ScaleType.CENTER_INSIDE);
    }

    public BitmapConvert(int maxWidth, int maxHeight, Bitmap.Config decodeConfig, ImageView.ScaleType scaleType) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.decodeConfig = decodeConfig;
        this.scaleType = scaleType;
    }

    /**将okhttp3的响应体转化为bitmap*/
    @Override
    public Bitmap convertResponse(Response response) throws Throwable {
        ResponseBody body = response.body();
        if (body == null) return null;
        return parse(body.bytes());
    }

    /**
     * 将响应体转化为bitmap图片
     * @param byteArray   body的数组
     * @return  bitmap
     * @throws OutOfMemoryError  内存溢出
     */
    private Bitmap parse(byte[] byteArray) throws OutOfMemoryError {
        BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
        Bitmap bitmap;
        if (maxWidth == 0 && maxHeight == 0) {
            decodeOptions.inPreferredConfig = decodeConfig;
            bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, decodeOptions);
        } else {
            decodeOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, decodeOptions);
            int actualWidth = decodeOptions.outWidth;//图片实际宽度
            int actualHeight = decodeOptions.outHeight;//图片实际高度

            int desiredWidth = getResizedDimension(maxWidth, maxHeight, actualWidth, actualHeight, scaleType);
            int desiredHeight = getResizedDimension(maxHeight, maxWidth, actualHeight, actualWidth, scaleType);

            decodeOptions.inJustDecodeBounds = false;
            //<=1按照1,处理,返回原图片的1/decodeOptions.inSampleSize的宽度和高度
            decodeOptions.inSampleSize = findBestSampleSize(actualWidth, actualHeight, desiredWidth, desiredHeight);
            Bitmap tempBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, decodeOptions);

            if (tempBitmap != null && (tempBitmap.getWidth() > desiredWidth || tempBitmap.getHeight() > desiredHeight)) {
                //尺寸大于想要的尺寸时进行缩放
                bitmap = Bitmap.createScaledBitmap(tempBitmap, desiredWidth, desiredHeight, true);
                tempBitmap.recycle();
            } else {
                bitmap = tempBitmap;
            }
        }
        return bitmap;
    }

    /**
     * 获取重新调整后尺寸
     * @param maxPrimary "最大宽度
     * @param maxSecondary "最大高度
     * @param actualPrimary "实际宽度
     * @param actualSecondary "实际高度
     * @param scaleType "缩放模式
     * @return "调整后的尺寸
     */
    private static int getResizedDimension(int maxPrimary, int maxSecondary, int actualPrimary,
                                           int actualSecondary, ImageView.ScaleType scaleType) {

        // If no dominant value at all, just return the actual.
        if ((maxPrimary == 0) && (maxSecondary == 0)) {//最大宽度高度都为0
            return actualPrimary;//返回实际宽度
        }

        // If ScaleType.FIT_XY fill the whole rectangle, ignore ratio.
        if (scaleType == ImageView.ScaleType.FIT_XY) {
            if (maxPrimary == 0) {
                return actualPrimary;//返回使劲宽度
            } else {
                return maxPrimary;//返回最大宽度
            }
        }

        // If primary is unspecified, scale primary to match secondary's scaling ratio.
        if (maxPrimary == 0) {//最大宽度为0
            double ratio = (double) maxSecondary / (double) actualSecondary;//最大高度与实际高度的比值
            return (int) (actualPrimary * ratio);//实际宽度乘以比例
        }

        if (maxSecondary == 0) {//最大高度为0
            return maxPrimary;
        }

        double ratio = (double) actualSecondary / (double) actualPrimary;
        int resized = maxPrimary;

        // If ScaleType.CENTER_CROP fill the whole rectangle, preserve aspect ratio.
        if (scaleType == ImageView.ScaleType.CENTER_CROP) {
            if ((resized * ratio) < maxSecondary) {
                resized = (int) (maxSecondary / ratio);
            }
            return resized;
        }

        if ((resized * ratio) > maxSecondary) {
            resized = (int) (maxSecondary / ratio);
        }
        return resized;
    }

    /**
     * 找到最好的样本大小
     * @param actualWidth 实际宽度
     * @param actualHeight 实际高度
     * @param desiredWidth 想要的宽度
     * @param desiredHeight 想要的高度
     * @return "
     */
    private static int findBestSampleSize(int actualWidth, int actualHeight, int desiredWidth, int desiredHeight) {
        double wr = (double) actualWidth / desiredWidth;
        double hr = (double) actualHeight / desiredHeight;
        double ratio = Math.min(wr, hr);//比例
        float n = 1.0f;
        while ((n * 2) <= ratio) {
            n *= 2;
        }
        return (int) n;
    }
}
