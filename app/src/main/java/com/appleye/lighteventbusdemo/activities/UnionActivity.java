package com.appleye.lighteventbusdemo.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appleye.lighteventbusdemo.R;
import com.appleye.lighteventbusdemo.component.IComponent;
import com.appleye.lighteventbusdemo.mock.MockData;
import com.appleye.lighteventbusdemo.parser.DataParser;

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
 * 测试组件间联动的(按照功能分组)
 */
public class UnionActivity extends AppCompatActivity {
    private IComponent[] mComponents;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doRequest();
    }

    private void doRequest() {
        Class[] data = MockData.getComponents();
        mComponents = DataParser.parseData(data, this);

        initViews();
    }

    private void initViews() {
        if(mRecyclerView == null) {
            mRecyclerView = findViewById(R.id.recycler_view);
            LinearLayoutManager lm= new LinearLayoutManager(this);
            lm.setOrientation(RecyclerView.VERTICAL);
            mRecyclerView.setLayoutManager(lm);

            mAdapter= new RecyclerViewAdapter();
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mComponents != null) {
            for(IComponent component : mComponents) {
                component.destroy();
            }
        }
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<ComponentViewHolder>{

        @Override
        public ComponentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(UnionActivity.this).inflate(R.layout.layout_item_container, null);
            return new ComponentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ComponentViewHolder holder, int position) {
            IComponent component = mComponents[position];
            component.attachView((ViewGroup) holder.itemView);
        }

        @Override
        public int getItemViewType(int position) {
            return mComponents[position].getType();
        }

        @Override
        public int getItemCount() {
            return mComponents == null ? 0 : mComponents.length;
        }
    }

    private static class ComponentViewHolder extends RecyclerView.ViewHolder{

        public ComponentViewHolder(View itemView) {
            super(itemView);
        }
    }
}
