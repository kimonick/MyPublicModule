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
package com.lzy.okgo.adapter;

import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.policy.CachePolicy;
import com.lzy.okgo.cache.policy.DefaultCachePolicy;
import com.lzy.okgo.cache.policy.FirstCacheRequestPolicy;
import com.lzy.okgo.cache.policy.NoCachePolicy;
import com.lzy.okgo.cache.policy.NoneCacheRequestPolicy;
import com.lzy.okgo.cache.policy.RequestFailedCachePolicy;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.lzy.okgo.utils.HttpUtils;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：2016/9/11
 * 描    述：带缓存的缓存策略回调请求
 * 修订历史：
 * ================================================
 */
public class CacheCall<T> implements Call<T> {

    /**
     * 接口,缓存策略
     */
    private CachePolicy<T> policy = null;
    /**
     * okgo的请求
     */
    private Request<T, ? extends Request> request;

    /**
     * 带缓存的请求构造函数
     *
     * @param request okgo的请求
     */
    public CacheCall(Request<T, ? extends Request> request) {
        this.request = request;
        this.policy = preparePolicy();
    }

    /**
     * 执行网络请求,首先获取本地缓存,传递缓存策略中的缓存实体进行网络请求
     */
    @Override
    public Response<T> execute() {
        CacheEntity<T> cacheEntity = policy.prepareCache();
        return policy.requestSync(cacheEntity);
    }

    /**
     * 执行网络请求,首先获取本地缓存,传递缓存策略中的缓存实体进行网络请求
     * 包含有回调
     */
    @Override
    public void execute(Callback<T> callback) {
        HttpUtils.checkNotNull(callback, "callback == null");
        CacheEntity<T> cacheEntity = policy.prepareCache();
        policy.requestAsync(cacheEntity, callback);
    }

    /**
     * 初始化缓存策略
     */
    private CachePolicy<T> preparePolicy() {
        switch (request.getCacheMode()) {
            case DEFAULT:
                policy = new DefaultCachePolicy<>(request);
                break;
            case NO_CACHE:
                policy = new NoCachePolicy<>(request);
                break;
            case IF_NONE_CACHE_REQUEST:
                policy = new NoneCacheRequestPolicy<>(request);
                break;
            case FIRST_CACHE_THEN_REQUEST:
                policy = new FirstCacheRequestPolicy<>(request);
                break;
            case REQUEST_FAILED_READ_CACHE:
                policy = new RequestFailedCachePolicy<>(request);
                break;
        }
        if (request.getCachePolicy() != null) {
            policy = request.getCachePolicy();
        }
        HttpUtils.checkNotNull(policy, "policy == null");
        return policy;
    }

    /**当前网络请求是否已执行*/
    @Override
    public boolean isExecuted() {
        return policy.isExecuted();
    }

    /**取消当前网络请求*/
    @Override
    public void cancel() {
        policy.cancel();
    }

    /**当前网络请求是否已取消*/
    @Override
    public boolean isCanceled() {
        return policy.isCanceled();
    }

    /**返回当前请求的新的CashCall*/
    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public Call<T> clone() {
        return new CacheCall<>(request);
    }

    /**获取当前okgo的Request*/
    public Request getRequest() {
        return request;
    }
}
