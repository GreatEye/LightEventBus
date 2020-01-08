package com.appleye.eventbus;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
 * 遍历方法，查找注册器，生成方法订阅，不对包外开放
 */
class SubscriberMethodFinder {
    //以下是编译器编译产生的方法
    private static final int BRIDGE = 0x40;
    private static final int SYNTHETIC = 0x1000;

    /**
     * 需要忽略的方法
     */
    private static final int MODIFIERS_IGNORE = Modifier.ABSTRACT | Modifier.STATIC | BRIDGE | SYNTHETIC;

    /**
     * 获取当前类的注册过触发器的方法信息
     *
     * @param subscriber 订阅者
     * @param subscriberMethodsMap 订阅方法Map
     */
    static void findSubscriberMethod(Object subscriber, Map<String, List<SubscriberMethod>> subscriberMethodsMap) {
        if (subscriber != null && subscriberMethodsMap != null) {
            Class<?> subscriberClz = subscriber.getClass();
            Method[] methods = subscriberClz.getDeclaredMethods();//包含继承类和接口类的方法
            for (Method method : methods) {
                int modifiers = method.getModifiers();
                /*过滤掉不是public和需要忽略的方法*/
                if ((modifiers & Modifier.PUBLIC) != 0 && (modifiers & MODIFIERS_IGNORE) == 0) {
                    Class<?>[] parameterTypes = method.getParameterTypes();//方法参数类型
                    if (parameterTypes.length == 1) {//允许只有一个参数的方法
                        Subscriber subscriberAnnotation = method.getAnnotation(Subscriber.class);
                        if (subscriberAnnotation != null) {//方法包含有定义的注解类
                            String[] types = subscriberAnnotation.eventType();
                            if(types != null && types.length > 0) {
                                for(String type : types) {
                                    SubscriberMethod subscriberMthod = new SubscriberMethod(subscriber, method, type);
                                    List<SubscriberMethod> subscriberMethodList = subscriberMethodsMap.get(type);
                                    if(subscriberMethodList == null) {
                                        subscriberMethodList = new ArrayList<>();
                                        subscriberMethodsMap.put(type, subscriberMethodList);
                                    }
                                    subscriberMethodList.add(subscriberMthod);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
