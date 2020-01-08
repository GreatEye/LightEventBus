package com.appleye.lighteventbusdemo.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.appleye.eventbus.Event;
import com.appleye.eventbus.EventBusFac;
import com.appleye.eventbus.Response;
import com.appleye.lighteventbusdemo.GroupConstants;
import com.appleye.lighteventbusdemo.R;

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
public class RequestResponseActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_req_rsp);
        findViewById(R.id.start_view).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                doRequest();
            }
        });
    }

    private void doRequest() {
        Response response = EventBusFac
                .getInstance(GroupConstants.GROUP_REQ_RSP)
                .request(new Event(GroupConstants.EVENT_REQUEST_CARD));
        if(response != null && Response.OK == response.resultCode) {
            List<String> valueList = (List<String>)response.body;
            if(valueList != null) {
                StringBuilder sb = new StringBuilder();
                for(String value : valueList) {
                    sb.append(value);
                }

                TextView valueView = (TextView)findViewById(R.id.value_view);
                valueView.setText(sb.toString());
                valueView.setVisibility(View.VISIBLE);

                Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
