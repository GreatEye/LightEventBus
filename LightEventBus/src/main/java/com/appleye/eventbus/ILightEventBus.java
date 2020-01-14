package com.appleye.eventbus;

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
 * 定义轻量级EventBus接口
 * <p>1. 支持 订阅-分发 模型</p>
 * <p>2. 支持 请求-响应 模型</p>
 * <p>3. 只支持同步操作，不支持异步<pp>
 * <p>4. 支持分组模式，也就是一个业务单元一个轻量级EventBus，这也就是轻量级的含义</p>
 */
@NoProguard
public interface ILightEventBus {
    /**
     * 获取分组名
     * @return group名称
     * */
    String getGroupName();
    /**
     * 注册订阅者
     * @param subscriber 订阅者
     * @return 引用
     * */
    ILightEventBus register(Object subscriber);
    /**
     * 注销订阅者
     * @param subscriber 订阅者
     * */
    void unRegister(Object subscriber);
    /**
     * 增加钩子，避免被gc，用一个观察者来勾住，生命周期等同观察者
     * @param subscriber 要勾住的对象
     * @return 引用
     * */
    ILightEventBus hook(Object subscriber);
    /**
     * 发送消息
     * @param event 事件
     * */
    void postEvent(Event event);

    /**
     * 请求操作
     * @param event 事件
     * @return 结果
     * */
    Response request(Event event);

    /**
     * 响应操作
     * @param event 源事件
     * @param data 数据
     * */
    void response(Event event, Object data);

    /**
     * 释放资源
     * */
    void release();
}
