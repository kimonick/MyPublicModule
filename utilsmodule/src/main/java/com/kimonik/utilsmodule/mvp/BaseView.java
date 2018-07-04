/*
 * Copyright 2016, The Android Open Source Project
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

package com.kimonik.utilsmodule.mvp;

import android.support.annotation.StringRes;

/**
 * 用户界面接口
 * 与用户相互交互的界面,该界面持有一个Presenter的引用,从而达到与数据源的隔离
 * 所有的数据更新操作全部在Presenter中实现
 */

public interface BaseView<T> {

    /**为视图设置Presenter,activity自身实现BaseView时须在自身内实例化Presenter
     * 如果是activity中的fragment实现BaseView的话则需要在实例化Presenter时为BaseView的实现者
     * 显式设置Presenter
     * @param presenter   主持人
     */
    void setPresenter(T presenter);
    /**显示提示信息*/
    void showToast(@StringRes int strRes);

}
