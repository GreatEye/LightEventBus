package com.appleye.lighteventbusdemo.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.appleye.eventbus.Event;
import com.appleye.eventbus.EventBusFac;
import com.appleye.lighteventbusdemo.GroupConstants;
import com.appleye.lighteventbusdemo.R;

import java.util.HashMap;
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
 * date 2020-01-03
 */
public class SubscriberActivity extends AppCompatActivity {
    private Handler mHandler;

    private Runnable mTickRunnable = new Runnable() {
        @Override
        public void run() {
            mCount ++ ;
            Map<String, Object> data = new HashMap<>();
            data.put("value", mCount);
            Event event = new Event(GroupConstants.EVENT_UPDATE_SUBSCRIBER);
            event.data = data;
            EventBusFac.getInstance(GroupConstants.GROUP_SUBSCRIBER).postEvent(event);
            if(mHandler != null) {
                mHandler.postDelayed(mTickRunnable, 1000);
            }
        }
    };

    private int mCount = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_subsciber);
        findViewById(R.id.start_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTicker();
            }
        });
    }

    private void startTicker() {
        if(mHandler == null) {
            mHandler = new Handler();
            mHandler.post(mTickRunnable);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}
