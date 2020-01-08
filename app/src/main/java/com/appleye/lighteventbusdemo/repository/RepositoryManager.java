package com.appleye.lighteventbusdemo.repository;

import com.appleye.lighteventbusdemo.mock.MockData;


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
 * date 2019-12-18
 */
public class RepositoryManager implements IRepositoryManager{
    private static IRepositoryManager sInstance;

    public static IRepositoryManager getInstance() {
        if(sInstance == null) {
            synchronized (RepositoryManager.class){
                if(sInstance == null) {
                    sInstance = new RepositoryManager();
                }
            }
        }

        return sInstance;
    }

    @Override
    public Object getData(String type) {
        switch (type){
            case Types.TYPE_SERIES:{
                return MockData.getSeriesList();
            }
            case Types.TYPE_PHOTOS:{
                return MockData.getPhotoList();
            }
        }
        return null;
    }
}
