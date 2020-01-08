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
 * 响应数据结构
 */
@NoProguard
public class Response {
    /**
     * 成功
     * */
    public static final int OK = 200;
    /**
     * 失败
     * */
    public static final int FAILED = -1;
    /**
     * key值
     * */
    public static final String KEY_RESULT = "result";
    /**
     * 状态码
     * */
    public int resultCode;
    /**
     * 返回结果
     * */
    public Object body;
}
