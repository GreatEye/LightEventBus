package com.appleye.eventbus;

import com.appleye.eventbus.utils.TextUtils;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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
 * date 2019-12-07
 * 轻量级EventBus工厂方法
 * des:采用懒调用模式，即第一次发送发送消息或请求的时候，才去遍历方法(不含基类)，并保存起来，
 * 下次直接取，这样不会影响初始化性能，另外该架构是通过一个GroupName来获取EventBus，分组
 * 通信可以保证不会出现几千上万个通信消息，导致性能急剧下降的问题。
 */
@NoProguard
public class LightEventBusFac {
    private static final String TAG = "LightEventBusFac";

    private static ConcurrentHashMap<String, WeakReference<ILightEventBus>> sInstanceMap;

    /**
     * 不采用分组方式，直接返回新对象
     * @return EventBus
     * */
    public static ILightEventBus newInstance() {
        return new LightEventBus("");
    }

    /**
     * 通过默认分组获取分组EventBus
     * @return EventBus
     * */
    public static ILightEventBus getInstance() {
        return getInstance("default");
    }

    /**
     * 通过groupName获取分组EventBus
     * @param groupName 分组名
     * @return EventBus
     * */
    public static ILightEventBus getInstance(String groupName) {
        if(TextUtils.isEmpty(groupName)) {
            throw new IllegalArgumentException("invalid groupName");
        }

        if(sInstanceMap == null) {
            synchronized (LightEventBus.class){
                if(sInstanceMap == null) {
                    sInstanceMap = new ConcurrentHashMap<>();
                }
            }
        }

        WeakReference<ILightEventBus> instanceRef = sInstanceMap.get(groupName);
        ILightEventBus instance = null;
        if(instanceRef != null) {
            instance = instanceRef.get();
        }
        if(instance == null) {
            instance = new LightEventBus(groupName);
            instanceRef = new WeakReference<>(instance);
            sInstanceMap.put(groupName, instanceRef);
        }

        return instance;
    }

    /**
     * 释放资源
     * */
    public static void release() {
        if(sInstanceMap != null) {
            sInstanceMap.clear();
        }
    }
    
    private static class LightEventBus implements IEventBus{
        private String mGroupName;
        private Set<Object> mSubscriberSet;
        //记录已经注册过的对象
        private Set<Object> mFoundSubscriberSet;
        private ConcurrentHashMap<String, List<SubscriberMethod>> mSubscriberMethodsMap;
        private final Object mLock = new Object();

        public LightEventBus(String groupName) {
            mGroupName = groupName;
            mSubscriberSet = new HashSet<>();
            mFoundSubscriberSet = new HashSet<>();
        }

        public String getGroupName() {
            return mGroupName;
        }

        @Override
        public ILightEventBus register(Object subscriber) {
            if(subscriber != null) {
                mSubscriberSet.add(subscriber);
            }
            return this;
        }

        @Override
        public void unRegister(Object subscriber) {
            if(subscriber != null) {
                mSubscriberSet.remove(subscriber);
            }
            if(mFoundSubscriberSet != null) {
                mFoundSubscriberSet.remove(subscriber);
            }
            //移除监听者对应的方法
            if(mSubscriberMethodsMap != null) {
                Iterator<Map.Entry<String, List<SubscriberMethod>>> it = mSubscriberMethodsMap.entrySet().iterator();
                while(it.hasNext()) {
                    Map.Entry<String, List<SubscriberMethod>> entry = it.next();
                    List<SubscriberMethod> subscriberMethodList = entry.getValue();
                    if(subscriberMethodList != null) {
                        int size = subscriberMethodList.size();
                        for(int i=size-1; i>=0; i--) {
                            if(subscriberMethodList.get(i).subscriber == subscriber) {
                                subscriberMethodList.remove(i);
                            }
                        }
                    }
                    if(subscriberMethodList == null || subscriberMethodList.size() == 0) {
                        it.remove();
                    }
                }
            }
        }

        @Override
        public ILightEventBus hook(Object subscriber){
            if(subscriber != null) {
                Field hookField = HookFieldFinder.getHookField(subscriber);
                if(hookField != null) {
                    boolean access = hookField.isAccessible();
                    hookField.setAccessible(true);
                    try {
                        hookField.set(subscriber, this);
                    }catch (Exception e){
                        e.printStackTrace();
                    } finally {
                        //还原访问属性
                        hookField.setAccessible(access);
                    }
                }
            }
            return this;
        }

        @Override
        public void postEvent(Event event) {
            if(event == null) {
                return;
            }
            event.eventBus = this;
            post(event);
        }

        private void post(Event event) {
            findSubscriberMethod();
            String type = event.type;
            if(mSubscriberMethodsMap != null) {
                List<SubscriberMethod> subscriberMethodList = mSubscriberMethodsMap.get(type);
                if(subscriberMethodList != null) {
                    try {
                        for(SubscriberMethod subscriberMethod : subscriberMethodList) {
                            subscriberMethod.method.invoke(subscriberMethod.subscriber, event);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }

        private void findSubscriberMethod() {
            if(mSubscriberMethodsMap == null) {
                synchronized (mLock){
                    if(mSubscriberMethodsMap == null) {
                        mSubscriberMethodsMap = new ConcurrentHashMap<>();
                    }
                    if(mSubscriberSet != null && mSubscriberSet.size() > 0) {
                        Iterator<Object> it = mSubscriberSet.iterator();
                        while(it.hasNext()) {
                            Object subscriber = it.next();
                            mFoundSubscriberSet.add(subscriber);
                            SubscriberMethodFinder.findSubscriberMethod(subscriber, mSubscriberMethodsMap);
                        }
                    }
                }
            } else {
                //检查是否有新增加的对象，则注册新对象
                synchronized (mLock) {
                    for(Object subscriber : mSubscriberSet) {
                        if(!mFoundSubscriberSet.contains(subscriber)) {
                            mFoundSubscriberSet.add(subscriber);
                            SubscriberMethodFinder.findSubscriberMethod(subscriber, mSubscriberMethodsMap);
                        }
                    }
                }
            }
        }

        @Override
        public Response request(Event event) {
            if(event == null) {
                return null;
            }
            event.eventBus = this;
            Response response = new Response();
            //先置为失败，有结果之后再置回来
            response.resultCode = Response.FAILED;
            OnceDataSource.putResponse(event.id, response);
            post(event);
            response = OnceDataSource.getResponse(event.id);
            return response;
        }

        @Override
        public void response(Event event, Object data) {
            Response response = OnceDataSource.getResponse(event.id);
            if(response != null) {
                response.resultCode = Response.OK;
                response.body = data;
                //重新放到池子里面
                OnceDataSource.putResponse(event.id, response);
            }
        }

        @Override
        public void release() {
            if(mSubscriberSet != null) {
                mSubscriberSet.clear();
            }
            if(mFoundSubscriberSet != null) {
                mFoundSubscriberSet.clear();
            }
            if(mSubscriberMethodsMap != null) {
                mSubscriberMethodsMap.clear();
            }
            if(sInstanceMap != null) {
                WeakReference<ILightEventBus> eventBusRef = sInstanceMap.get(mGroupName);
                if(eventBusRef != null) {
                    eventBusRef.clear();
                }
                sInstanceMap.remove(mGroupName);
            }
        }
    }
}
