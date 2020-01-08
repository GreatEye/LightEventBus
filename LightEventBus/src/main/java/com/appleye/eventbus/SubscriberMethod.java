package com.appleye.eventbus;

import com.appleye.eventbus.utils.TextUtils;

import java.lang.reflect.Method;

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
 * date 2019-12-13
 * 只允许包内访问
 */
@NoProguard
class SubscriberMethod {
    /**
     * 订阅者
     * */
    final Object subscriber;
    /**
     * 方法
     * */
    final Method method;
    /**
     * 消息类型
     * */
    final String eventType;

    SubscriberMethod(Object subscriber, Method method, String eventType) {
        this.subscriber = subscriber;
        this.method = method;
        this.eventType = eventType;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        if(method != null) {
            hashCode = method.hashCode();
        }
        if(!TextUtils.isEmpty(eventType)) {
            hashCode = hashCode + eventType.hashCode()*31;
        }

        return hashCode;
    }
}
