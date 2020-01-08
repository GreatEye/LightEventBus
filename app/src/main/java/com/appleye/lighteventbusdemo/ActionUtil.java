package com.appleye.lighteventbusdemo;

import android.app.Activity;
import android.content.Intent;

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
 * date 2020-01-03
 */
public class ActionUtil {
    public static final String ACTION_JUMP_TO_EMPTY = "android.intent.action.empty";
    public static final String ACTION_JUMP_TO_SUBSCRIBER = "android.intent.action.subscriber";
    public static final String ACTION_JUMP_TO_REQ_RSP = "android.intent.action.req_rsp";
    public static final String ACTION_JUMP_TO_UNION = "android.intent.action.union";

    public static void doAction(Activity activity, String action) {
        switch (action){
            case ACTION_JUMP_TO_EMPTY:
            case ACTION_JUMP_TO_SUBSCRIBER:
            case ACTION_JUMP_TO_REQ_RSP:
            case ACTION_JUMP_TO_UNION:{
                activity.startActivity(new Intent(action));
                break;
            }
        }
    }
}
