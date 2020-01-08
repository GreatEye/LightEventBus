package com.appleye.lighteventbusdemo.parser;

import android.content.Context;

import com.appleye.lighteventbusdemo.component.IComponent;

import java.lang.reflect.Constructor;

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
 * date 2019-12-30
 */
public class DataParser {
    public static IComponent[] parseData(Class[] clzs, Context context) {
        if(clzs == null || clzs.length == 0 || context == null) {
            return null;
        }
        int size = clzs.length;
        try {
            IComponent[] components = new IComponent[size];
            for(int i=0; i<size; i++) {
                Class clz = clzs[i];
                Constructor<IComponent> constructor = clz.getConstructor(Context.class);
                IComponent component = constructor.newInstance(context);
                components[i] = component;
            }
            return components;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
