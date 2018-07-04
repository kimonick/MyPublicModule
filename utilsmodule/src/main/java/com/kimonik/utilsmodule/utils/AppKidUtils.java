package com.kimonik.utilsmodule.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;

/**
 * * ===============================================================
 * name:             AppKidUtils
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2018/2/26
 * description：  APP工具辅助类
 * history：
 * *==================================================================
 */

public class AppKidUtils {

    /**
     * 通过apk的存储目录安装apk，返回安装apk的intent
     */
    public static Intent installApk(Context context, String apkPath) {
        if (context == null || TextUtils.isEmpty(apkPath)) {
            return null;
        }


        File file = new File(apkPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);

        //判读版本是否在7.0以上
        if (Build.VERSION.SDK_INT >= 24) {
            //provider authorities
            Uri apkUri = FileProvider.getUriForFile(context, "com.mydomain.fileprovider", file);
            //Granting Temporary Permissions to a URI
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        return intent;
        /**
         * *****************apk的下载及安装**************************************
         *
         *
         *
         *
         *  private NotificationManager manager;
         *  private int Notification_ID = 0;
         *  private Notification.Builder builder;
         *   private Notification notification;
         *   private String filePath;


         *   String url = "http://app.mi.com/download/26";//apk下载地址
         *  final String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/.apkdownload";//apk存储目录,隐藏文件夹,不存在时okgo会自动创建
         *  final String fileName = "mydownload.apk";//下载的apk保存文件名,固定名字,重复下载时会被后下载的版本覆盖
         *   filePath = dir + "/" + fileName;//apk文件的全路径
         *    File file = new File(filePath);//根据路径创建apk的File文件
         *   //      NotificationManager导入的包名  import android.app.NotificationManager;
         *    manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);//通过服务获取通知管理者
         *   showNotification();
         *   if (file.exists()) {//检测文件是否存在,存在则直接安装
         *    //此处进行文件及版本检查,如果当前存在的是最新版本的apk,则直接安装无须下载
         *    AppKidUtils.installApk(HomeActivity.this, filePath);
         *    } else {//不存在进行下载
         *   HttpUtils.getInstance().downloadFile(url, new FileCallback(dir, fileName) {//使用okgo的文件下载下载apk
         *      @Override
         *    public void onSuccess(Response<File> response) {//下载成功后打开安装界面
         *   startActivity(AppKidUtils.installApk(HomeActivity.this,filePath));
         *    }

         *   @Override
         *    public void downloadProgress(final Progress progress) {//下载进度更新
         *    Log.e("HttpUtils", "downloadProgress: -下载进度----" + progress.fraction);
         *    int progressInt=(int) (progress.fraction * 100);//下载进度转换为整数
         *    if (progressInt<100){
         *    manager.notify(Notification_ID, builder.setProgress(100, progressInt, false).build());//更新通知
         *    }else {
         *    builder.setProgress(100, 100, false);
         *    builder.setContentTitle("下载已完成");//设置标题
         *    builder.setContentText("点击安装");//设置通知内容
         *     manager.notify(Notification_ID, builder.build());//更新通知,当通知的id相同时即为更新通知
         *    }
         *    super.downloadProgress(progress);
         *    }

         *   @Override
         *    public void onError(Response<File> response) {//下载出错回调
         *    super.onError(response);
         *    Log.e("HttpUtils", "downloadProgress: -未知错误----" + response.toString());
         *    }
         *     });

         *    }



         *    private void showNotification() {
         *  //   Notification导入的包名     import android.app.Notification;
         *  builder = new Notification.Builder(this);
         *  builder.setSmallIcon(R.mipmap.ic_launcher);//设置图标
         *  builder.setTicker("正在下载");//手机状态栏的提示
         *  builder.setContentTitle("正在下载土豆金服app");//设置标题
         *  builder.setContentText("正在下载");//设置通知内容
         *  builder.setWhen(System.currentTimeMillis());//设置通知时间
         *  Intent intent = AppKidUtils.installApk(this, filePath);//获取安装apk的intent
         *  PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);//点击通知打开的activity
         *  builder.setContentIntent(pendingIntent);//点击后的意图
         *   builder.setDefaults(Notification.DEFAULT_LIGHTS);//设置指示灯
         *  builder.setDefaults(Notification.DEFAULT_SOUND);//设置提示声音
         *  builder.setDefaults(Notification.DEFAULT_VIBRATE);//设置震动
         *  builder.setProgress(100, 0, false);
         *   notification = builder.build();//4.1以上，以下要用getNotification()
         *   manager.notify(Notification_ID, notification);//显示通知
         *    }
         *
         */
    }

    /**
     * 卸载应用程序,无法卸载系统级apk
     */
    public void uninstallApp(Context context, String packageName) {
        Intent uninstall_intent = new Intent();
        uninstall_intent.setAction(Intent.ACTION_DELETE);
        uninstall_intent.setData(Uri.parse("package:" + packageName));
        context.startActivity(uninstall_intent);
    }

    /**
     *
     */
    private void temp() {

    }

}