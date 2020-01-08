package com.appleye.eventbus;

import java.lang.reflect.Field;

/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author Liaopu
 * date 2019-12-14
 * 属性查找，不对包外开放
 */
class HookFieldFinder {
    static Field getHookField(Object subscriber) {
        if(subscriber != null) {
            Class clz = subscriber.getClass();
            Field[] fields = clz.getDeclaredFields();
            for(Field field : fields) {
                Hook hook = field.getAnnotation(Hook.class);
                if(hook != null) {
                    return field;
                }
            }
        }

        return null;
    }
}
