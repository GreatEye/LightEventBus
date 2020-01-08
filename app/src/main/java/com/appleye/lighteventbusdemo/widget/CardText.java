package com.appleye.lighteventbusdemo.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.appleye.eventbus.Event;
import com.appleye.eventbus.EventBusFac;
import com.appleye.eventbus.Subscriber;
import com.appleye.lighteventbusdemo.GroupConstants;

import java.util.ArrayList;
import java.util.List;

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
public class CardText extends AppCompatTextView {

    public CardText(Context context) {
        this(context, null);
    }

    public CardText(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CardText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        EventBusFac.getInstance(GroupConstants.GROUP_REQ_RSP).register(this);
    }

    @Subscriber(eventType = GroupConstants.EVENT_REQUEST_CARD)
    public void handRequest(Event event) {
        String text = getText().toString();
        if(event.data == null) {
            event.data = new ArrayList<>();
        }
        List<String> data = (List<String>) event.data;
        data.add(text);
        EventBusFac.getInstance(GroupConstants.GROUP_REQ_RSP).response(event, data);
    }
}
