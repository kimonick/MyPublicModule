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
 * MVP模式中契约者的模板
 */
public  interface Contract {

    /**
     * 视图接口,持有Presenter引用来隔离数据源
     */
    interface View extends BaseView<Presenter> {
//        具体方法
    }

    /**
     * Presenter接口,持有视图(BaseView实现者)以及数据源(Repository)
     * 实现视图与数据源的隔离
     */
    interface Presenter extends BasePresenter {
//        具体方法
    }
}
