package com.kimonik.utilsmodule.utils;

import android.app.Application;
import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.DBCookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;


/**
 * * ================================================
 * name:            HttpUtils
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/9
 * description：   网络连接工具类--单例
 * history：
 * ===================================================
 */

public class HttpUtils {

    /**okgo是否已经初始化*/
    private static boolean hasInitialize=true;
    /**全局请求重试次数*/
    private static final int RETRY_COUNT=3;

    /**
     * 私有构造函数
     */
    private HttpUtils() {
//        throw new UnsupportedOperationException("u can't initialize this");//为实现单例效果,此处不能抛出异常
    }

    //创建单例--静态内部类方式
    private static class SingleHolder {
        private static final HttpUtils instance = new HttpUtils();
    }

    //获取单例
    public static HttpUtils getInstance(Context context) {
        if (hasInitialize){
            hasInitialize=false;
            initOkGo(context);
        }
        return SingleHolder.instance;
    }


    /**
     * get请求
     * 链式调用泛型约束GetRequest<T>的泛型与Callback<T>的泛型必须一致
     */
    public void GET(String url, Map<String, String> paramsMap,
                    Callback<String> callback) {
        OkGo.<String>get(url)                // 请求方式和请求url
                .tag("get")                       // 请求的 tag, 主要用于取消对应的请求
//                .cacheKey(key)                      // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
//                .cacheMode(CacheMode.DEFAULT)       // 缓存模式--初始化时配置，详细请看缓存介绍
                .params(paramsMap)
                .execute(callback);
    }

    /**
     * 无参get请求
     */
    public void GET(String url, Callback<String> callback) {
        OkGo.<String>get(url)                    // 请求方式和请求url
                .tag("get")                            // 请求的 tag, 主要用于取消对应的请求
                .execute(callback);
    }

    /**
     * 无参get请求
     */
    public void POST(String url, Callback<String> callback) {
        OkGo.<String>post(url)                    // 请求方式和请求url
                .tag("post")                            // 请求的 tag, 主要用于取消对应的请求
                .execute(callback);
    }

    /**
     * post请求
     */
    public void POST(String url, TreeMap<String, String> paramsMap,
                     Callback<String> callback) {
        OkGo.<String>post(url)     // 请求方式和请求url
                .tag("post")                       // 请求的 tag, 主要用于取消对应的请求
                .params(StringUtils.getRequestParams(paramsMap))//参数加密--与后台服务器相关
                .execute(callback);
    }


    /**
     * @param url       链接URL
     * @param paramsMap 参数map
     * @param callback  回调
     */
    public void download(String url, Map<String, String> paramsMap,
                         Callback<String> callback) {
        OkGo.<String>get(url)
                .tag("download")
                .params(paramsMap)
                .execute(callback);

    }

    /**
     * 上传文件到服务器
     *
     * @param url        上传链接
     * @param loginToken 令牌--参数--具体参数与服务器相关
     * @param filePath   文件路径--参数--具体参数与服务器相关
     * @param callback   回调
     */
    public void postFile(String url, String loginToken, String filePath,
                         Callback<String> callback) {
        OkGo.<String>post(url)
                .tag("postfile")
                .params("login_token", loginToken)
                .params("headImage", new File(filePath))
                .cacheKey("push")
                .execute(callback);

    }

    /**
     * 下载文件
     */
    public void downloadFile(String url, Callback<File> fileCallback) {
        OkGo.<File>get(url)
                .tag("downloadfile")
                .execute(fileCallback);
    }

    /**okgo初始化配置*/
    private static void initOkGo(Context context) {

        //-----------------------设置全局请求头,全局请求参数----------------------------------------


        //全局请求头,没有不设置
//        HttpHeaders headers = new HttpHeaders();
//        headers.put("commonHeaderKey1", "commonHeaderValue1");    //header不支持中文，不允许有特殊字符
//        headers.put("commonHeaderKey2", "commonHeaderValue2");
        //全局请求参数,没有不设置
//        HttpParams params = new HttpParams();
//        params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
//        params.put("commonParamsKey2", "这里支持中文参数");


        //-----------------------设置全局请求头,全局请求参数----------------------------------------



        //----------------------okhttp3  OkHttpClient相关设置---------------------------------------


        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //log拦截相关
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
//        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
//        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
//        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志


        //第三方的开源库，使用通知显示当前请求的log，不过在做文件下载的时候，这个库好像有问题，对文件判断不准确
        //builder.addInterceptor(new ChuckInterceptor(this));

//        //超时时间设置，默认60秒
//        builder.readTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);      //全局的读取超时时间
//        builder.writeTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);     //全局的写入超时时间
//        builder.connectTimeout(OkGo.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);   //全局的连接超时时间
// //超时时间设置，默认60秒
        builder.readTimeout(5000, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(5000, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(5000, TimeUnit.MILLISECONDS);   //全局的连接超时时间

//
        //自动管理cookie（或者叫session的保持），以下几种任选其一就行
        //builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));            //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new DBCookieStore(context)));              //使用数据库保持cookie，如果cookie不过期，则一直有效
        //builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));            //使用内存保持cookie，app退出后，cookie消失

        //https相关设置，以下几种方案根据需要自己设置
        //方法一：信任所有证书,不安全有风险
        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        //方法二：自定义信任规则，校验服务端证书
//        HttpsUtils.SSLParams sslParams2 = HttpsUtils.getSslSocketFactory(new SafeTrustManager());
        //方法三：使用预埋证书，校验服务端证书（自签名证书）
        //HttpsUtils.SSLParams sslParams3 = HttpsUtils.getSslSocketFactory(getAssets().open("srca.cer"));
        //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
        //HttpsUtils.SSLParams sslParams4 = HttpsUtils.getSslSocketFactory(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"));
        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
        //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
//        builder.hostnameVerifier(new SafeHostnameVerifier());

        //----------------------okhttp3  OkHttpClient相关设置---------------------------------------

        //----------------------okgo初始化----------------------------------------------------------

        // 此处的全局设置优先级最低,会被使用时的设置覆盖,其他统一的配置
        // 详细说明看GitHub文档：https://github.com/jeasonlzy/
        OkGo.getInstance().init((Application) context.getApplicationContext())                           //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置会使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(RETRY_COUNT);//全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
//                .addCommonHeaders(headers)                      //全局公共头
//                .addCommonParams(params);                       //全局公共参数
        //----------------------okgo初始化----------------------------------------------------------

    }

}
