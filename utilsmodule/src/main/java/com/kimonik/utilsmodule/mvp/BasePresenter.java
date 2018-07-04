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

/**
 * Presenter顶级接口
 *
 * Presenter持有activity中视图(一般是activity自身或者fragment)和Repository(数据源)
 * 从而达到解耦的目的
 */
public interface BasePresenter {

    /**开始加载数据到页面,启动的时机一般在onResume()中*/
    void start();

}
