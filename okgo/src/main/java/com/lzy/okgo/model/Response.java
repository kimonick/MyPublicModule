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
package com.lzy.okgo.model;

import okhttp3.Call;
import okhttp3.Headers;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：2016/9/11
 * 描    述：okgo响应体的包装类
 * 修订历史：
 * ================================================
 */
public final class Response<T> {

    /**网络请求的响应体,类型受泛型控制*/
    private T body;
    /**网络请求抛出的异常*/
    private Throwable throwable;
    /**该网络响应是否来自缓存*/
    private boolean isFromCache;
    /**okhttp3请求的未处理回调*/
    private okhttp3.Call rawCall;
    /**okhttp3请求的未处理响应*/
    private okhttp3.Response rawResponse;

    /**网络请求成功时的响应处理*/
    public static <T> Response<T> success(boolean isFromCache, T body, Call rawCall, okhttp3.Response rawResponse) {
        Response<T> response = new Response<>();
        response.setFromCache(isFromCache);
        response.setBody(body);
        response.setRawCall(rawCall);
        response.setRawResponse(rawResponse);
        return response;
    }

    /**网络请求出错时的响应处理*/
    public static <T> Response<T> error(boolean isFromCache, Call rawCall, okhttp3.Response rawResponse, Throwable throwable) {
        Response<T> response = new Response<>();
        response.setFromCache(isFromCache);
        response.setRawCall(rawCall);
        response.setRawResponse(rawResponse);
        response.setException(throwable);
        return response;
    }

    /**okgo的响应类*/
    public Response() {
    }

    /**okgo请求后的网络状态码*/
    public int code() {
        if (rawResponse == null) return -1;
        return rawResponse.code();
    }

    /**okgo请求后的网络状态信息*/
    public String message() {
        if (rawResponse == null) return null;
        return rawResponse.message();
    }

    /**okgo请求后的网络响应头*/
    public Headers headers() {
        if (rawResponse == null) return null;
        return rawResponse.headers();
    }

    /**
     * 网络请求成功
     */
    public boolean isSuccessful() {
        return throwable == null;
    }

    /**设置网络请求返回的响应体*/
    public void setBody(T body) {
        this.body = body;
    }

    /**获取网络请求的响应体*/
    public T body() {
        return body;
    }

    /**获取网络请求的异常*/
    public Throwable getException() {
        return throwable;
    }

    /**设置网络请求的异常*/
    public void setException(Throwable exception) {
        this.throwable = exception;
    }

    /**获取okhttp3返回的未处理回调*/
    public Call getRawCall() {
        return rawCall;
    }

    /**设置okhttp3的未处理回调*/
    public void setRawCall(Call rawCall) {
        this.rawCall = rawCall;
    }

    /**获取okhttp3的未处理此昂英*/
    public okhttp3.Response getRawResponse() {
        return rawResponse;
    }

    /**设置okhttp3的未处理响应*/
    public void setRawResponse(okhttp3.Response rawResponse) {
        this.rawResponse = rawResponse;
    }

    /**okgo的网络响应是否来自缓存*/
    public boolean isFromCache() {
        return isFromCache;
    }

    /**设置okgo的网络响应是否来自缓存*/
    public void setFromCache(boolean fromCache) {
        isFromCache = fromCache;
    }
}
