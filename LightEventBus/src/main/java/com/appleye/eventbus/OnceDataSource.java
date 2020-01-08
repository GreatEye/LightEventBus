package com.appleye.eventbus;

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
 * 临时数据源，只支持一次存储，不对包外开放
 */
class OnceDataSource {
    private static ConcurrentHashMap<Long, Response> sDataSource;

    private OnceDataSource() {}

    private static void init() {
        if(sDataSource == null) {
            synchronized (OnceDataSource.class){
                if(sDataSource == null) {
                    sDataSource = new ConcurrentHashMap<>(5);
                }
            }
        }
    }

    /**
     * 存储操作
     * @param id 唯一标识
     * @param response 结果
     * */
    static void putResponse(long id, Response response) {
        if(response == null) {
            return;
        }
        init();
        if(sDataSource != null) {
            sDataSource.put(id, response);
        }
    }

    /**
     * 获取操作
     * @param id 唯一标识
     * @return 结果
     * */
    static Response getResponse(long id) {
        if(sDataSource != null) {
            return sDataSource.remove(id);
        }
        return null;
    }
}
