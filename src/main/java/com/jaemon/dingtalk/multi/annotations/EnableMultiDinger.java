/*
 * Copyright ©2015-2020 Jaemon. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jaemon.dingtalk.multi.annotations;

import com.jaemon.dingtalk.multi.DingerConfigHandler;
import com.jaemon.dingtalk.multi.MultiDingerScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * EnableMultiDinger
 *
 * @author Jaemon
 * @since 3.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(MultiDingerScannerRegistrar.class)
public @interface EnableMultiDinger {

    /**
     * global dingerHandler
     *
     * @return {@link DingerConfigHandler}
     */
    Class<? extends DingerConfigHandler> value() default DingerConfigHandler.class;
}