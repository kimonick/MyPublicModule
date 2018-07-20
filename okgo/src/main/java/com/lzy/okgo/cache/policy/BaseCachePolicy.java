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
package com.lzy.okgo.cache.policy;

import android.graphics.Bitmap;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.db.CacheManager;
import com.lzy.okgo.exception.HttpException;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.lzy.okgo.utils.HeaderParser;
import com.lzy.okgo.utils.HttpUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Headers;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：2017/5/25
 * 描    述：   okgo发起网络请求后对响应体的基本处置策略类
 * 修订历史：
 * ================================================
 */
public abstract class BaseCachePolicy<T> implements CachePolicy<T> {

    /**okgo的请求基类*/
    protected Request<T, ? extends Request> request;
    /**请求是否已取消*/
    protected volatile boolean canceled;
    /**当前请求超时的次数*/
    protected volatile int currentRetryCount = 0;
    /**网络请求是否已执行*/
    protected boolean executed;
    /**okhttp3call*/
    protected okhttp3.Call rawCall;
    /**okgo的请求回调*/
    protected Callback<T> mCallback;
    /**okgo响应体的缓存实体类*/
    protected CacheEntity<T> cacheEntity;

    public BaseCachePolicy(Request<T, ? extends Request> request) {
        this.request = request;
    }

    /**分析响应体*/
    @Override
    public boolean onAnalysisResponse(Call call, okhttp3.Response response) {
        return false;
    }

    /**为提交请求之前,如果数据库中有当前请求的缓存,则优先去除缓存???*/
    @Override
    public CacheEntity<T> prepareCache() {
        //check the config of cache 检查缓存配置
        if (request.getCacheKey() == null) {
            //以url的拼接形式作为缓存的key值
            request.cacheKey(HttpUtils.createUrlFromParams(request.getBaseUrl(), request.getParams().urlParamsMap));
        }
        if (request.getCacheMode() == null) {
            //缓存模式为空时设置为无缓存模式
            request.cacheMode(CacheMode.NO_CACHE);
        }

        CacheMode cacheMode = request.getCacheMode();
        if (cacheMode != CacheMode.NO_CACHE) {
            //从数据库中根据缓存key值查询出缓存数据
            //noinspection unchecked
            cacheEntity = (CacheEntity<T>) CacheManager.getInstance().get(request.getCacheKey());
//            对每个请求添加默认的请求头，如果有缓存，并返回缓存实体对象
            HeaderParser.addCacheHeaders(request, cacheEntity, cacheMode);
            //检查缓存是否过期
            if (cacheEntity != null && cacheEntity.checkExpire(cacheMode, request.getCacheTime(), System.currentTimeMillis())) {
                cacheEntity.setExpire(true);
            }
        }

        //缓存实体类为空或者缓存已过期或者缓存实体类数据为空或者缓存实体响应头为空
        if (cacheEntity == null || cacheEntity.isExpire() || cacheEntity.getData() == null || cacheEntity.getResponseHeaders() == null) {
            cacheEntity = null;
        }
        return cacheEntity;
    }

    /**
     * 获取okhttp的请求回调
     * @return   okhttp3.call,回调未处理
     * @throws Throwable
     */
    @Override
    public synchronized okhttp3.Call prepareRawCall() throws Throwable {
        //request已提交,抛异常
        if (executed) throw HttpException.COMMON("Already executed!");
        executed = true;
//        获取okhttp的同步call对象
        rawCall = request.getRawCall();
        //如果标记为取消,则执行取消方法,但是已经完成的请求无法取消
        if (canceled) rawCall.cancel();
        return rawCall;
    }

    /**
     * 请求网络同步
     * @return  响应
     */
    protected Response<T> requestNetworkSync() {
        try {
            //立即执行请求,阻塞直到响应可以被处理或者出错
            okhttp3.Response response = rawCall.execute();
            //返回http状态码
            int responseCode = response.code();

            //network error
            if (responseCode == 404 || responseCode >= 500) {
//                网络出错时设置okgo响应实体类的相关信息
                return Response.error(false, rawCall, response, HttpException.NET_ERROR());
            }

            //使用转换器将响应转化为需要的响应体
            T body = request.getConverter().convertResponse(response);
            //save cache when request is successful 请求成功时缓存请求
            saveCache(response.headers(), body);
//            网络请求成功时的响应处理
            return Response.success(false, body, rawCall, response);
        } catch (Throwable throwable) {
            //链接超时,重试次数未满时
            if (throwable instanceof SocketTimeoutException && currentRetryCount < request.getRetryCount()) {
                currentRetryCount++;
                rawCall = request.getRawCall();
                if (canceled) {
                    rawCall.cancel();
                } else {
                    //再次发起网络请求
                    requestNetworkSync();
                }
            }
            return Response.error(false, rawCall, null, throwable);
        }
    }

    /**发起网络请求,真正由okhttp3发起的网络请求*/
    protected void requestNetworkAsync() {
        /**将网络请求添加到请求队列中,一般会立即执行请求,除非当前队列有其他请求正在执行*/
        rawCall.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                //请求超时,重试次数未超,重新请求
                if (e instanceof SocketTimeoutException && currentRetryCount < request.getRetryCount()) {
                    //retry when timeout
                    currentRetryCount++;
                    rawCall = request.getRawCall();
                    if (canceled) {
                        rawCall.cancel();
                    } else {
                        rawCall.enqueue(this);
                    }
                } else {//重试次数已经超出
                    if (!call.isCanceled()) {
                        //设置响应出错实体
                        Response<T> error = Response.error(false, call, null, e);
                        //回调okgo的onerror方法
                        onError(error);
                    }
                }
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                int responseCode = response.code();

                //network error
                if (responseCode == 404 || responseCode >= 500) {
                    //设置响应出错实体
                    Response<T> error = Response.error(false, call, response, HttpException.NET_ERROR());
                    //回调okgo的onerror方法
                    onError(error);
                    return;
                }
//                分析响应体
                if (onAnalysisResponse(call, response)) return;

                try {
                    //获得转化后的响应体
                    T body = request.getConverter().convertResponse(response);
                    //save cache when request is successful  缓存响应体
                    saveCache(response.headers(), body);
                    Response<T> success = Response.success(false, body, call, response);
                    //回调okgo的onsucess()方法,调用具体实现策略类的onSuccess(success)方法,本抽象类无此方法实现
                    onSuccess(success);
                } catch (Throwable throwable) {
                    Response<T> error = Response.error(false, call, response, throwable);
                    //回调okgo 的onerror方法
                    onError(error);
                }
            }
        });
    }

    /**
     * 请求成功后根据缓存模式，更新缓存数据
     *
     * @param headers 响应头
     * @param data    响应数据
     */
    private void saveCache(Headers headers, T data) {
        if (request.getCacheMode() == CacheMode.NO_CACHE) return;    //不需要缓存,直接返回
        if (data instanceof Bitmap) return;             //Bitmap没有实现Serializable,不能缓存

        CacheEntity<T> cache = HeaderParser.createCacheEntity(headers, data, request.getCacheMode(), request.getCacheKey());
        if (cache == null) {
            //服务器不需要缓存，移除本地缓存--数据库操作
            CacheManager.getInstance().remove(request.getCacheKey());
        } else {
            //更新缓存
            CacheManager.getInstance().replace(request.getCacheKey(), cache);
        }
    }

    /**子线程在handler依附的线程上运行runnable*/
    protected void runOnUiThread(Runnable run) {
        OkGo.getInstance().getDelivery().post(run);
    }

    /**网络请求是否已执行*/
    @Override
    public boolean isExecuted() {
        return executed;
    }

    /**是否取消网络请求*/
    @Override
    public void cancel() {
        canceled = true;
        if (rawCall != null) {
            rawCall.cancel();
        }
    }

    /**网络请求是否已取消*/
    @Override
    public boolean isCanceled() {
        if (canceled) return true;
        synchronized (this) {
            return rawCall != null && rawCall.isCanceled();
        }
    }
}
