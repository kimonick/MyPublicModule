package com.kimonik.utilsmodule.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;


import com.kimonik.utilsmodule.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * * ==================================================
 * name:            FileUtils
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/19
 * description：   文件存储工具类
 * history：
 * <p>
 * <p>
 * -----------existSDCard()-----判断是否存在外部SD卡
 * -----------getFilepath(Context context)----获取文件的保存路径--优先外部SD卡缓存目录
 * -----------saveJsonToSDCard(Context context, String fileName, String content)---
 * 保存传递的字符串到本地--存储到外部SD卡缓存目录(优先)或内部缓存目录
 * -----------isExists(String strPath)------判断文件是否存在
 * -----------readFileContent(Context context, String fileName)---读取文件
 * -----------
 * -----------
 * * ==================================================
 */

public class FileUtils {
    /**
     * 判断是否存在外部SD卡
     *
     * @return 存在  ture ,不存在  false
     */
    private static boolean existSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取文件的保存路径--优先外部SD卡缓存目录
     */
    private static String getFilepath(Context context) {
        String filePath;
        if (existSDCard()) {
            filePath = context.getExternalCacheDir().getPath() + "/";
        } else {
            filePath = context.getCacheDir().getPath() + "/";
        }
        return filePath;
    }

    /**
     * 保存传递的字符串到本地--存储到外部SD卡缓存目录(优先)或内部缓存目录
     *
     * @param context  上下文
     * @param fileName 文件存储名称
     * @param content  存储内容
     */
    public static void saveJsonToSDCard(Context context, String directoryName,String fileName, String content) {

        File appDir = new File(Environment.getExternalStorageDirectory(), directoryName);
        if (!appDir.exists()) {
            if (!appDir.mkdir()) {
                Log.e("TAG", "saveImageToGallery: -----图片保存目录创建失败!!");
            }
        }
        File file = new File(appDir.getAbsolutePath()+"/" + fileName);
        Log.e("TAG", "saveJsonToSDCard: " + (appDir.getAbsolutePath() + fileName));

        FileOutputStream os = null;
        try {
            if (file.exists()) {
                file.delete();
            } else {
                file.createNewFile();
            }
            os = new FileOutputStream(file);
            os.write(content.getBytes(), 0, content.getBytes().length);
            os.flush();
            ToastUtils.showToast(context, R.string.wenjianbaocunchenggong);

        } catch (IOException e) {
            e.printStackTrace();
            ToastUtils.showToast(context, R.string.wenjianbaocunshibai);
        } finally {
            if (os != null)
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }


    }

    /**
     * 判断文件是否存在
     *
     * @param strPath 文件路径
     * @return 存在  ture,不存在  false
     */
    public static boolean isExists(String strPath) {
        if (strPath == null) {
            return false;
        }

        final File strFile = new File(strPath);

        return strFile.exists();

    }

    /**
     * 读取文件,通过底层目录,文件名,sdk的以及目录
     *
     * @param context  上下文
     * @param fileName 文件名称
     * @return 内容字符串
     */
    public static String readFileContent(Context context, String directoryName, String fileName) {
//        File file = new File(getFilepath(context) + fileName);
        File appDir = new File(Environment.getExternalStorageDirectory(), directoryName);
        if (!appDir.exists()) {
            if (!appDir.mkdir()) {
                Log.e("TAG", "saveImageToGallery: -----图片保存目录创建失败!!");
            }
        }
        File file = new File(appDir.getAbsolutePath()+"/" + fileName);

        if (file.exists()) {
            FileInputStream fis = null;
            InputStreamReader isr = null;
            try {
                fis = new FileInputStream(file);
                isr = new InputStreamReader(fis, "UTF-8");

                char[] input = new char[fis.available()];
                isr.read(input);
                String in = new String(input);
                return in.trim();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (isr != null) {
                        isr.close();
                    }
                    if (fis != null) {
                        fis.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return "";

    }

    /**
     * 通过详细路径读取文件
     * @param fileName   详细路径
     * @return  文件字符串
     */
    public static String readFileContent(String fileName) {
        File file = new File(fileName);

        if (file.exists()) {
            FileInputStream fis = null;
            InputStreamReader isr = null;
            try {
                fis = new FileInputStream(file);
                isr = new InputStreamReader(fis, "UTF-8");

                char[] input = new char[fis.available()];
                isr.read(input);
                String in = new String(input);
                return in.trim();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (isr != null) {
                        isr.close();
                    }
                    if (fis != null) {
                        fis.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return "";

    }

//    /**
//     * 返回本地头像图片的保存路径
//     */
//    public static String getIconPath(Context context) {
//        // 首先确定保存图片的目录
//        String loginToken = UserConfig.getInstance().getLoginToken(context);
//        String name = MD5Utils.md5(loginToken);
//        File appDir = new File(Environment.getExternalStorageDirectory(), "icon");
//        if (!appDir.exists()) {
//            if (!appDir.mkdir()) {
//                Log.e("TAG", "saveImageToGallery: -----图片保存目录创建失败!!");
//            }
//        }
//        String fileName = name + "icon.jpg";
//        Log.e("TAG", "iconPath: file.getAbsolutePath()-----" + appDir.getAbsolutePath() + "/" + fileName);
//        return appDir.getAbsolutePath() + "/" + fileName;
//    }


//    /**
//     * 保存图片到本地
//     */
//    public static String saveImageToGallery(Context context, String path) {
//
//        String loginToken = UserConfig.getInstance().getLoginToken(context);
//        String name = MD5Utils.md5(loginToken);
//        // 首先确定保存图片的目录
//        File appDir = new File(Environment.getExternalStorageDirectory(), "icon");
//        if (!appDir.exists()) {
//            if (!appDir.mkdir()) {
//                Log.e("TAG", "saveImageToGallery: -----图片保存目录创建失败!!");
//            }
//        }
//        String fileName = name + "icon.jpg";
//        File file = new File(appDir, fileName);
//        File oFile = new File(path);
//        int length = 2097152;
//
//        try {
//            FileInputStream in = new FileInputStream(oFile);
//            FileOutputStream out = new FileOutputStream(file);
//            FileChannel inC = in.getChannel();
//            FileChannel outC = out.getChannel();
//
//            ByteBuffer b = null;
//            while (true) {
//                if (inC.position() == inC.size()) {
//                    inC.close();
//                    outC.close();
//                    break;
//                }
//                if ((inC.size() - inC.position()) < length) {
//                    length = (int) (inC.size() - inC.position());
//                } else {
//                    length = 2097152;
//                }
//                b = ByteBuffer.allocateDirect(length);
//                inC.read(b);
//                b.flip();
//                outC.write(b);
//                outC.force(false);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        // 其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    file.getAbsolutePath(), fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        return appDir.getAbsolutePath() + "/icon.jpg";
//        // 最后通知图库更新
////        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
//    }

//
//    /**
//     * 保存方法
//     */
//    public static String saveBitmap(Bitmap bitmap) {
//
//        // 首先确定保存图片的目录
//        File appDir = new File(Environment.getExternalStorageDirectory(), "icon");
//        if (!appDir.exists()) {
//            if (!appDir.mkdir()) {
//                Log.e("TAG", "saveImageToGallery: -----图片保存目录创建失败!!");
//            }
//        }
//        String fileName = "icon.jpg";
//
//        File f = new File(appDir, fileName);
//        if (f.exists()) {
//            f.delete();
//        }
//        Bitmap newBitmap;
//
//        if (bitmap.getRowBytes() * bitmap.getHeight() > 2091752) {
//            newBitmap=BitmapUtils.getCompressBitmap(bitmap);
//        }else {
//            newBitmap=bitmap;
//        }
//
//        try {
//            FileOutputStream out = new FileOutputStream(f);
//            newBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
//            out.flush();
//            out.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Log.e("TAG", "saveBitmap: --f.getAbsolutePath()---"+f.getAbsolutePath());
//
//        return f.getAbsolutePath();
//
//    }

    public static String getIconDir(){
        // 首先确定保存图片的目录
        File appDir = new File(Environment.getExternalStorageDirectory(), "icon");
        if (!appDir.exists()) {
            if (!appDir.mkdir()) {
                Log.e("TAG", "saveImageToGallery: -----图片保存目录创建失败!!");
            }
        }
        return appDir.getAbsolutePath();
    }

    public static void fileDir(Context context) {
        Log.e("TAG", "fileDir:1---------------" + context.getExternalCacheDir());//应用内缓存目录
        Log.e("TAG", "fileDir:5---------------" + context.getExternalCacheDir().getPath());
        Log.e("TAG", "fileDir: 2---------------" + context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));//SD卡内应用缓存目录
        Log.e("TAG", "fileDir: -3--------------" + context.getFilesDir());//SD卡内应用缓存目录
        Log.e("TAG", "fileDir: ---4------------" + context.getCacheDir());//应用内缓存目录
        Log.e("TAG", "fileDir: ---6------------" + context.getCacheDir().getPath());
        //以上目录都会在卸载时清空数据
    }

}
