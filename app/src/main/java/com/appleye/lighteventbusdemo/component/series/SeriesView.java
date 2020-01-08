package com.appleye.lighteventbusdemo.component.series;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appleye.eventbus.Event;
import com.appleye.eventbus.EventBusFac;
import com.appleye.lighteventbusdemo.R;

import com.appleye.lighteventbusdemo.GroupConstants;
import com.appleye.lighteventbusdemo.mvp.BaseView;
import com.appleye.lighteventbusdemo.mvp.IPresenter;

import java.util.HashMap;
import java.util.List;
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
 * date 2019-12-30
 */
public class SeriesView extends BaseView {
    private Context mContext;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private List<String> mValueList;

    public SeriesView(IPresenter presenter, Context context) {
        super(presenter);
        mContext = context;

        mValueList = (List<String>) presenter.doRequest();
    }

    @Override
    public View getView(){
        if(mRecyclerView == null) {
            View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_common_item_recycler_view, null);
            mRecyclerView = contentView.findViewById(R.id.common_recycler_view);

            initRecyclerView();
        }
        return mRecyclerView;
    }

    private void initRecyclerView(){
        if(mRecyclerView != null) {
            LinearLayoutManager lm = new LinearLayoutManager(mContext);
            lm.setOrientation(RecyclerView.HORIZONTAL);
            mRecyclerView.setLayoutManager(lm);
            mAdapter = new RecyclerViewAdapter();
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void show() {
        View view = getView();
        if(view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void update(Object data) {

    }

    @Override
    public void hide() {
        View view = getView();
        if(view != null) {
            view.setVisibility(View.GONE);
        }
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<SeriesViewHolder>{

        @NonNull
        @Override
        public SeriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_component_series_item, null);
            return new SeriesViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SeriesViewHolder holder, int position) {
            String value = mValueList.get(position);
            holder.textView.setText(value);
            holder.textView.setTag(position);
            holder.textView.setOnClickListener(mOnItemClickListener);
        }

        @Override
        public int getItemCount() {
            return mValueList == null ? 0 : mValueList.size();
        }
    }

    private class SeriesViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public SeriesViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.series_text);
        }
    }

    private View.OnClickListener mOnItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Integer position = (Integer)v.getTag();
            if(position != null) {
                Map<String, Object> data = new HashMap<>();
                data.put("position", position);
                Event event = new Event(GroupConstants.EVENT_SERIES_SELECTED);
                event.data = data;
                EventBusFac.getInstance(GroupConstants.SERIES_PHOTO_UNION).postEvent(event);
            }
        }
    };
}
