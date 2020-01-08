package com.appleye.lighteventbusdemo;

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
public class GroupConstants {
    //功能联动分组
    public static final String SERIES_PHOTO_UNION = "series_photo_union";
    public static final String EVENT_SERIES_SELECTED = "lightbus://event/series/selected";

    //订阅分发组件分组
    public static final String GROUP_SUBSCRIBER = "group_subscriber";
    public static final String EVENT_UPDATE_SUBSCRIBER = "lightbus://event/subsciber/update";

    //请求响应分组
    public static final String GROUP_REQ_RSP = "group_req_rsp";
    public static final String EVENT_REQUEST_CARD = "lightbus://event/request/card";
}
